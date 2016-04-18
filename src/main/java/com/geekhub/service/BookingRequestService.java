package com.geekhub.service;

import com.geekhub.model.BookingRequest;
import com.geekhub.model.Hotel;
import com.geekhub.model.Room;
import com.geekhub.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class BookingRequestService {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomTypeService roomTypeService;

    public List<BookingRequest> getBookingRequests(Date arrivalDate, Date departureDate) {
        return (List<BookingRequest>) sessionFactory.getCurrentSession()
                .createCriteria(BookingRequest.class)
                .add(Restrictions.gt("departureDate", arrivalDate))
                .add(Restrictions.lt("arrivalDate", departureDate))
                .add(Restrictions.eq("rejected", false))
                .list();
    }

    public List<BookingRequest> getBookingRequests(Date arrivalDate, Date departureDate, List<Room> rooms) {
        return (List<BookingRequest>) sessionFactory.getCurrentSession()
                .createCriteria(BookingRequest.class)
                .add(Restrictions.gt("departureDate", arrivalDate))
                .add(Restrictions.lt("arrivalDate", departureDate))
                .add(Restrictions.in("room", rooms))
                .add(Restrictions.eq("rejected", false))
                .list();
    }

    public List<BookingRequest> getBookingRequests(Integer bookingRequestNumber) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BookingRequest.class)
                .add(Restrictions.eq("bookingRequestNumber", bookingRequestNumber))
                .list();
    }

    public List<BookingRequest> getBookingRequests(User user) {
        return sessionFactory.getCurrentSession()
                .createCriteria(BookingRequest.class)
                .add(Restrictions.eq("guest", user))
                .list();
    }

    private List<Map<String, String>> getBookingRequests(Hotel hotel, User user) {

        List<Map<String, String>> result = new ArrayList<>();
        List<Room> hotelRooms = roomService.getHotelRooms(hotel);

        Session currentSession = sessionFactory.getCurrentSession();
        Criteria criteria = currentSession.createCriteria(BookingRequest.class)
                .add(Restrictions.in("room", hotelRooms))
                .setProjection(Projections.projectionList()
                        .add(Projections.groupProperty("bookingRequestNumber"))
                        .add(Projections.max("arrivalDate"))
                        .add(Projections.max("departureDate"))
                        .add(Projections.max("accepted"))
                        .add(Projections.max("rejected")));
        if (user != null) {
            criteria.add(Restrictions.eq("guest", user));
        }
        List<Object[]> bookingRequests = (List<Object[]>) criteria.list();


        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        for (Object[] bookingRequest : bookingRequests) {
            Map<String, String> bookingRequestInfo = new HashMap<>();
            bookingRequestInfo.put("bookingRequestNumber", bookingRequest[0].toString());
            bookingRequestInfo.put("arrivalDate", dateFormat.format(bookingRequest[1]));
            bookingRequestInfo.put("departureDate", dateFormat.format(bookingRequest[2]));
            Boolean accepted = (Boolean) bookingRequest[3];
            Boolean rejected = (Boolean) bookingRequest[4];
            if (accepted) {
                bookingRequestInfo.put("status", "подтверждена");
            } else if (rejected) {
                bookingRequestInfo.put("status", "отклонена");
            } else {
                bookingRequestInfo.put("status", "ожидает обработки");
            }
            result.add(bookingRequestInfo);
        }

        return result;
    }

    public void createBookingRequest(Integer bookingRequestNumber, Date arrivalDate, Date departureDate, User guest, String guestName,
                                     String guestEmail, String guestPhoneNumber, Room room) {

        Session currentSession = sessionFactory.getCurrentSession();
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setBookingRequestNumber(bookingRequestNumber);
        bookingRequest.setArrivalDate(arrivalDate);
        bookingRequest.setDepartureDate(departureDate);
        bookingRequest.setRoom(room);
        bookingRequest.setGuest(guest);
        bookingRequest.setGuestName(guestName);
        bookingRequest.setGuestEmail(guestEmail);
        bookingRequest.setGuestPhoneNumber(guestPhoneNumber);
        bookingRequest.setAccepted(false);
        bookingRequest.setRejected(false);
        currentSession.save(bookingRequest);
    }

    public void prepareBookingRequest(Hotel hotel, Date arrivalDate, Date departureDate, User guest, String guestName,
                                      String guestEmail, String guestPhoneNumber, Map<Integer, Integer> rooms) {

        List<BookingRequest> bookingRequests = getBookingRequests(arrivalDate, departureDate, roomService.getHotelRooms(hotel));
        Integer bookingRequestNumber = getLastNumber() + 1;
        for (Map.Entry<Integer, Integer> entry : rooms.entrySet()) {
            List<Room> freeRooms = roomService.getFreeRooms(bookingRequests, hotel, roomTypeService.getRoomTypeById(entry.getKey()));
            if (freeRooms.size() >= entry.getValue()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    createBookingRequest(bookingRequestNumber, arrivalDate, departureDate, guest, guestName, guestEmail, guestPhoneNumber, freeRooms.get(i));
                }
            } else {
                throw new RuntimeException();
            }
        }

    }

    public List<Map<String, Object>> prepareBookingRequestList(List<Hotel> hotels, User user) {
        List<Map<String, Object>> bookingRequestList = new ArrayList<>();
        for (Hotel hotel : hotels) {
            Map<String, Object> hotelRequests = new HashMap<>();
            hotelRequests.put("hotel", hotel);
            hotelRequests.put("bookingRequestsInfo", getBookingRequests(hotel, user));
            bookingRequestList.add(hotelRequests);
        }
        return bookingRequestList;
    }

    private Integer getLastNumber() {
        Integer lastNumber = (Integer) sessionFactory.getCurrentSession()
                .createCriteria(BookingRequest.class)
                .setProjection(Projections.max("bookingRequestNumber"))
                .uniqueResult();
        if (lastNumber != null) {
            return lastNumber;
        } else {
            return 0;
        }
    }

    public Map<String, Object> getBookingRequestInformation(Integer bookingRequestNumber) {

        Map<String, Object> bookingRequestInformation = new HashMap<>();

        List<BookingRequest> bookingRequests = getBookingRequests(bookingRequestNumber);

        if (bookingRequests.size() > 0) {
            bookingRequestInformation.put("bookingRequestNumber", bookingRequests.get(0).getBookingRequestNumber());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date arrivalDate = bookingRequests.get(0).getArrivalDate();
            Date departureDate = bookingRequests.get(0).getDepartureDate();
            bookingRequestInformation.put("arrivalDate", dateFormat.format(arrivalDate));
            bookingRequestInformation.put("departureDate", dateFormat.format(departureDate));
            bookingRequestInformation.put("nightsQuantity", getNightsQuantity(arrivalDate, departureDate));
            bookingRequestInformation.put("guestName", bookingRequests.get(0).getGuestName());
            bookingRequestInformation.put("guestEmail", bookingRequests.get(0).getGuestEmail());
            bookingRequestInformation.put("guestPhoneNumber", bookingRequests.get(0).getGuestPhoneNumber());
            bookingRequestInformation.put("accepted", bookingRequests.get(0).getAccepted());
            bookingRequestInformation.put("rejected", bookingRequests.get(0).getRejected());
            List<Map<String, String>> roomsInformation = getBookingRequestRoomsInformation(bookingRequests);
            bookingRequestInformation.put("roomsInformation", roomsInformation);
            bookingRequestInformation.put("requestValue", getRequestValue(roomsInformation));
        }

        return bookingRequestInformation;

    }

    private List<Map<String, String>> getBookingRequestRoomsInformation(List<BookingRequest> bookingRequests) {
        List<Map<String, String>> bookingRequestRoomsInformation = new ArrayList<>();

        for (BookingRequest bookingRequest : bookingRequests) {

            Map<String, String> roomTypeInformation = null;
            for (Map<String, String> listElement : bookingRequestRoomsInformation) {
                Integer roomTypeId = Integer.parseInt(listElement.get("roomTypeId"));
                if (roomTypeId.equals(bookingRequest.getRoom().getRoomType().getRoomTypeId())) {
                    roomTypeInformation = listElement;
                }
            }

            if (roomTypeInformation == null) {
                roomTypeInformation = new HashMap<>();
                roomTypeInformation.put("roomTypeId", bookingRequest.getRoom().getRoomType().getRoomTypeId().toString());
                roomTypeInformation.put("roomTypeDescription", bookingRequest.getRoom().getRoomType().getDescription());
                roomTypeInformation.put("roomsQuantity", "0");
                roomTypeInformation.put("value", "0");
                bookingRequestRoomsInformation.add(roomTypeInformation);
            }

            roomTypeInformation.put("roomsQuantity", String.valueOf(Integer.parseInt(roomTypeInformation.get("roomsQuantity")) + 1));

            Integer pricePerNight = bookingRequest.getRoom().getPricePerNight();
            long diff = bookingRequest.getDepartureDate().getTime() - bookingRequest.getArrivalDate().getTime();
            long nights = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            long value = Integer.parseInt(roomTypeInformation.get("roomsQuantity")) * pricePerNight * nights;
            roomTypeInformation.put("value", String.valueOf(value));

        }

        return bookingRequestRoomsInformation;
    }

    private Integer getRequestValue(List<Map<String, String>> roomsInformation) {
        Integer requestValue = 0;
        for (Map<String, String> element : roomsInformation) {
            requestValue += Integer.parseInt(element.get("value"));
        }
        return requestValue;
    }

    private Long getNightsQuantity(Date date1, Date date2) {
        long diff = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public void updateRequestStatus(Integer bookingRequestNumber, Boolean accepted) {
        List<BookingRequest> bookingRequests = getBookingRequests(bookingRequestNumber);
        Session currentSession = sessionFactory.getCurrentSession();
        bookingRequests.forEach(bookingRequest -> {
            bookingRequest.setAccepted(accepted);
            bookingRequest.setRejected(!accepted);
            currentSession.update(bookingRequest);
        });
    }

}

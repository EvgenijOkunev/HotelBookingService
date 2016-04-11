package com.geekhub.service;

import com.geekhub.model.BookingRequest;
import com.geekhub.model.Hotel;
import com.geekhub.model.Room;
import com.geekhub.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    public void createBookingRequest(Date arrivalDate, Date departureDate, User guest, String guestName,
                                     String guestEmail, String guestPhoneNumber, Room room) {

        Session currentSession = sessionFactory.getCurrentSession();
        BookingRequest bookingRequest = new BookingRequest();
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
        bookingRequest.setBookingRequestNumber(bookingRequest.getBookingRequestId());
        currentSession.update(bookingRequest);

    }

    public void prepareBookingRequest(Hotel hotel, Date arrivalDate, Date departureDate, User guest, String guestName,
                                         String guestEmail, String guestPhoneNumber, Map<Integer, Integer> rooms) {

        Session currentSession = sessionFactory.getCurrentSession();

        List<BookingRequest> bookingRequests = getBookingRequests(arrivalDate, departureDate, roomService.getHotelRooms(hotel));
        for (Map.Entry<Integer, Integer> entry : rooms.entrySet()) {
            List<Room> freeRooms = roomService.getFreeRooms(bookingRequests, hotel, roomTypeService.getRoomTypeById(entry.getKey()));
            if (freeRooms.size() >= entry.getValue()) {
                for (int i = 0; i < entry.getValue(); i++) {
                    createBookingRequest(arrivalDate, departureDate, guest, guestName, guestEmail, guestPhoneNumber, freeRooms.get(i));
                }
            } else {
               throw new RuntimeException();
            }
        }

    }

}

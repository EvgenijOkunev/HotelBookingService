package com.geekhub.service;

import com.geekhub.model.BookingRequest;
import com.geekhub.model.Hotel;
import com.geekhub.model.Room;
import com.geekhub.model.RoomType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RoomService {

    @Autowired
    private SessionFactory sessionFactory;

    public Room createRoom(RoomType roomType, Integer numberOfGuests, Integer pricePerNight) {
        Session session = sessionFactory.getCurrentSession();
        Room room = new Room();
        room.setRoomType(roomType);
        room.setNumberOfGuests(numberOfGuests);
        room.setPricePerNight(pricePerNight);
        session.save(room);
        return room;
    }

    public List<HashMap<String, String>> getInformationAboutHotelRooms(Hotel hotel, List<RoomType> roomTypes) {
        List<HashMap<String, String>> roomsList = new ArrayList<>();
        for (RoomType roomType : roomTypes) {
            List<Room> roomsByType = getHotelRoomsByType(hotel, roomType);
            int roomsListSize = roomsByType.size();
            HashMap<String, String> roomInformation = new HashMap<>();
            roomInformation.put("roomsTypeId", String.valueOf(roomType.getRoomTypeId()));
            roomInformation.put("roomsTypeName", String.valueOf(roomType.getDescription()));
            roomInformation.put("roomsQuantity", String.valueOf((roomsListSize > 0) ? roomsListSize : ""));
            roomInformation.put("numberOfGuests", String.valueOf((roomsListSize > 0) ? roomsByType.get(0).getNumberOfGuests() : ""));
            roomInformation.put("pricePerNight", String.valueOf((roomsListSize > 0) ? roomsByType.get(0).getPricePerNight() : ""));
            roomsList.add(roomInformation);
        }
        return roomsList;
    }

    public List<Room> getHotelRoomsByType(Hotel hotel, RoomType roomType) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Room.class).
                add(Restrictions.eq("hotel", hotel)).
                add(Restrictions.eq("roomType", roomType)).
                list();
    }

    public void deleteHotelRooms(Hotel hotel) {
        Session session = sessionFactory.getCurrentSession();
        session.createCriteria(Room.class)
                .add(Restrictions.eq("hotel", hotel))
                .list()
                .forEach(session::delete);
    }

    public List<Room> getFreeRooms(List<BookingRequest> bookingRequests) {
        Set<Integer> occupiedRoomsId = new HashSet<>();
        bookingRequests.forEach(bookingRequest -> occupiedRoomsId.add(bookingRequest.getRoom().getRoomId()));
        Session session = sessionFactory.getCurrentSession();
        if (occupiedRoomsId.size() != 0) {
            return (List<Room>) session.createCriteria(Room.class)
                    .add(Restrictions.not(Restrictions.in("roomId", occupiedRoomsId)))
                    .list();
        } else {
            return (List<Room>) session.createCriteria(Room.class).list();
        }
    }

}

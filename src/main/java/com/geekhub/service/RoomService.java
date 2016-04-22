package com.geekhub.service;

import com.geekhub.model.BookingRequest;
import com.geekhub.model.Hotel;
import com.geekhub.model.Room;
import com.geekhub.model.RoomType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
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
    @Autowired
    private RoomTypeService roomTypeService;

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
            List<Room> roomsByType = getHotelRooms(hotel, roomType);
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

    public List<Room> getHotelRooms(Hotel hotel) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Room.class)
                .add(Restrictions.eq("hotel", hotel))
                .list();
    }

    public List<Room> getHotelRooms(Hotel hotel, RoomType roomType) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Room.class)
                .add(Restrictions.eq("hotel", hotel))
                .add(Restrictions.eq("roomType", roomType))
                .list();
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

    public List<Room> getFreeRooms(List<BookingRequest> bookingRequests, Hotel hotel) {
        Set<Integer> occupiedRoomsId = new HashSet<>();
        bookingRequests.forEach(bookingRequest -> occupiedRoomsId.add(bookingRequest.getRoom().getRoomId()));
        Session session = sessionFactory.getCurrentSession();
        if (occupiedRoomsId.size() != 0) {
            return (List<Room>) session.createCriteria(Room.class)
                    .add(Restrictions.eq("hotel", hotel))
                    .add(Restrictions.not(Restrictions.in("roomId", occupiedRoomsId)))
                    .list();
        } else {
            return (List<Room>) session.createCriteria(Room.class)
                    .add(Restrictions.eq("hotel", hotel))
                    .list();
        }
    }

    public List<Room> getFreeRooms(List<BookingRequest> bookingRequests, Hotel hotel, RoomType roomType) {
        Set<Integer> occupiedRoomsId = new HashSet<>();
        bookingRequests.forEach(bookingRequest -> occupiedRoomsId.add(bookingRequest.getRoom().getRoomId()));
        Session session = sessionFactory.getCurrentSession();
        if (occupiedRoomsId.size() != 0) {
            return (List<Room>) session.createCriteria(Room.class)
                    .add(Restrictions.eq("hotel", hotel))
                    .add(Restrictions.eq("roomType", roomType))
                    .add(Restrictions.not(Restrictions.in("roomId", occupiedRoomsId)))
                    .list();
        } else {
            return (List<Room>) session.createCriteria(Room.class)
                    .add(Restrictions.eq("hotel", hotel))
                    .add(Restrictions.eq("roomType", roomType))
                    .list();
        }
    }

    public List<HashMap<String, String>> groupRoomsByType(List<Room> rooms) {
        List<HashMap<String, String>> groupedRooms = new ArrayList<>();

        for (Room room : rooms) {
            HashMap<String, String> roomMap = null;
            for (HashMap<String, String> groupedRoom : groupedRooms) {
                if (groupedRoom.get("roomTypeId").equals(String.valueOf(room.getRoomType().getRoomTypeId()))) {
                    roomMap = groupedRoom;
                }
            }
            if (roomMap == null) {
                roomMap = new HashMap<>();
                roomMap.put("roomTypeId", String.valueOf(room.getRoomType().getRoomTypeId()));
                roomMap.put("roomTypeName", String.valueOf(room.getRoomType().getDescription()));
                roomMap.put("numberOfGuests", String.valueOf(room.getNumberOfGuests()));
                roomMap.put("pricePerNight", String.valueOf(room.getPricePerNight()));
                roomMap.put("roomQuantity", "0");
                groupedRooms.add(roomMap);
            }
            roomMap.put("roomQuantity", String.valueOf(Integer.parseInt(roomMap.get("roomQuantity")) + 1));
        }

        return groupedRooms;
    }

    public List<Room> roomsProcessing(String roomsParam, Hotel hotel) {
        List<Room> rooms = new ArrayList<>();
        JSONArray roomsJSON = new JSONArray(roomsParam);
        for (int i = 0; i < roomsJSON.length(); i++) {
            JSONObject room = roomsJSON.getJSONObject(i);
            int roomsQuantity = Integer.parseInt(room.getString("roomsQuantity"));
            RoomType roomType = roomTypeService.getRoomTypeById(Integer.parseInt(room.getString("roomType")));
            int numberOfGuests = Integer.parseInt(room.getString("numberOfGuests").equals("") ? "0" : room.getString("numberOfGuests"));
            Integer pricePerNight = Integer.parseInt(room.getString("pricePerNight").equals("") ? "0" : room.getString("pricePerNight"));
            List<Room> existingRooms = getHotelRooms(hotel, roomType);
            int existingRoomsSize = existingRooms.size();
            for (int j = 0; j < roomsQuantity - existingRoomsSize; j++) {
                existingRooms.add(createRoom(roomType, numberOfGuests, pricePerNight));
            }
            existingRooms.forEach(existingRoom -> {
                existingRoom.setNumberOfGuests(numberOfGuests);
                existingRoom.setPricePerNight(pricePerNight);
            });
            rooms.addAll(existingRooms);
        }
        return rooms;
    }

}

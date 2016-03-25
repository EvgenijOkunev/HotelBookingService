package com.geekhub.service;

import com.geekhub.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Blob;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class HotelService {

    @Autowired
    private SessionFactory sessionFactory;

    public Hotel getHotelById(Integer id) {
        return (Hotel) sessionFactory.getCurrentSession().get(Hotel.class, id);
    }

    public List<Hotel> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return (List<Hotel>) session.createCriteria(Hotel.class)
                .addOrder(Order.asc("name"))
                .list();
    }

    public List<Hotel> getOwnersHotels(User owner) {
        Session session = sessionFactory.getCurrentSession();
        return (List<Hotel>) session.createCriteria(Hotel.class)
                .add(Restrictions.eq("owner", owner))
                .addOrder(Order.asc("name"))
                .list();
    }

    public void createHotel(String name, Blob description, Integer stars, City city, User owner, List<Room> rooms) {
        Session session = sessionFactory.getCurrentSession();
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setStars(stars);
        hotel.setCity(city);
        hotel.setOwner(owner);
        hotel.setRooms(rooms);
        rooms.forEach(room -> room.setHotel(hotel));
        session.save(hotel);
    }

    public void updateHotel(Hotel hotel, String name, Blob description, Integer stars, City city, List<Room> rooms) {
        Session session = sessionFactory.getCurrentSession();
        hotel.setName(name);
        hotel.setDescription(description);
        hotel.setStars(stars);
        hotel.setCity(city);
        hotel.setRooms(rooms);
        rooms.forEach(room -> room.setHotel(hotel));
        session.update(hotel);
    }

    public void deleteHotel(Hotel hotel) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(hotel);
    }

    public String prepareSuitableHotelsInformation(List<Room> freeRooms, City city, List<RoomType> roomTypes) {

        List<Room> freeRoomsInCity = freeRooms
                .stream()
                .filter(room -> room.getHotel().getCity().getCityId().equals(city.getCityId()))
                .collect(Collectors.toList());

        JSONArray hotelsInformation = new JSONArray();
        for (Room room : freeRoomsInCity) {
            Hotel hotel = room.getHotel();

            JSONObject hotelInformation = null;
            for (Object hotelInformationObject : hotelsInformation) {
                JSONObject object = (JSONObject) hotelInformationObject;
                if (object.get("hotelId").equals(hotel.getHotelId())) {
                    hotelInformation = object;
                }
            }
            if (hotelInformation == null) {
                hotelInformation = new JSONObject();
                hotelInformation.put("hotelName", hotel.getName());
                hotelInformation.put("hotelId", hotel.getHotelId());
                hotelInformation.put("roomsQuantity", 0);
                hotelsInformation.put(hotelInformation);
                JSONArray roomsInformation = new JSONArray();
                for (RoomType roomType : roomTypes) {
                    JSONObject roomTypeInformation = new JSONObject();
                    roomTypeInformation.put("roomTypeId", roomType.getRoomTypeId());
                    roomTypeInformation.put("roomTypeName", roomType.getName());
                    roomTypeInformation.put("quantity", 0);
                    roomTypeInformation.put("price", 0);
                    roomsInformation.put(roomTypeInformation);
                }
                hotelInformation.put("rooms", roomsInformation);
            }

            hotelInformation.put("roomsQuantity", (int) hotelInformation.get("roomsQuantity") + 1);
            JSONArray roomsInformation = (JSONArray) hotelInformation.get("rooms");
            JSONObject roomTypeInformation = new JSONObject();
            for (Object o : roomsInformation) {
                JSONObject jsonObject = (JSONObject) o;
                if (jsonObject.get("roomTypeId").equals(room.getRoomType().getRoomTypeId())) {
                    roomTypeInformation = jsonObject;
                }
            }
            roomTypeInformation.put("quantity", (int) roomTypeInformation.get("quantity") + 1);
            roomTypeInformation.put("price", room.getPricePerNight());
          //  hotelInformation.put(room.getRoomType().getName(), roomTypeInformation);
        }

        return new JSONObject().put("hotelsInformation", hotelsInformation).toString();

    }

}

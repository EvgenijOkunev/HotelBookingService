package com.geekhub.service;

import com.geekhub.model.Room;
import com.geekhub.model.RoomType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RoomService {

    @Autowired
    private SessionFactory sessionFactory;

    public Room createRoom(RoomType roomType, Integer numberOfGuests, Double pricePerNight) {
        Session session = sessionFactory.getCurrentSession();
        Room room = new Room();
        room.setRoomType(roomType);
        room.setNumberOfGuests(numberOfGuests);
        room.setPricePerNight(pricePerNight);
        session.save(room);
        return room;
    }

}

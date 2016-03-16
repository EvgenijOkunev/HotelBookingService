package com.geekhub.service;

import com.geekhub.model.RoomType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class RoomTypeService {

    @Autowired
    private SessionFactory sessionFactory;

    public void createRoomType(String name, String description) {
        Session session = sessionFactory.getCurrentSession();
        RoomType roomType = new RoomType();
        roomType.setName(name);
        roomType.setDescription(description);
        session.save(roomType);
    }

    public List<RoomType> getAll() {
        return (List<RoomType>) sessionFactory.getCurrentSession()
                .createCriteria(RoomType.class).list();
    }

    public RoomType getRoomTypeById(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (RoomType) currentSession.get(RoomType.class, id);
    }

    public RoomType getRoomTypeByName(String name) {
        return (RoomType) sessionFactory.getCurrentSession()
                .createCriteria(RoomType.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

}

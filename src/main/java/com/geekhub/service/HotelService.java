package com.geekhub.service;

import com.geekhub.model.Hotel;
import com.geekhub.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class HotelService {

    @Autowired
    private SessionFactory sessionFactory;

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
}

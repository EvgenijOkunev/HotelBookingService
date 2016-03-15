package com.geekhub.service;

import com.geekhub.model.City;
import com.geekhub.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class CityService {

    @Autowired
    private SessionFactory sessionFactory;

    public void createCity(String name, String description) {
        Session session = sessionFactory.getCurrentSession();
        City city = new City();
        city.setName(name);
        city.setDescription(description);
        session.save(city);
    }

    public List<City> getAll() {
        return (List<City>) sessionFactory.getCurrentSession()
                .createCriteria(City.class).list();
    }
}

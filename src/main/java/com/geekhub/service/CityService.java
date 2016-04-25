package com.geekhub.service;

import com.geekhub.model.City;
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

    public void createCity(String name) {
        Session session = sessionFactory.getCurrentSession();
        City city = new City();
        city.setName(name);
        session.save(city);
    }

    public List<City> getAll() {
        return (List<City>) sessionFactory.getCurrentSession()
                .createCriteria(City.class).list();
    }

    public City getCityById(Integer id) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (City) currentSession.get(City.class, id);
    }

}

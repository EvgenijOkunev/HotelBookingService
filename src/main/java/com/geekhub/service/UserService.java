package com.geekhub.service;

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
public class UserService {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Retrieves all users
     *
     * @return a list of users
     */
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return (List<User>) session.createCriteria(User.class)
                .addOrder(Order.asc("firstName"))
                .list();
    }

    /**
     * Retrieves a single user by email/login
     *
     * @return an instance of user
     */
    public User getUser(String email) {
        Session session = sessionFactory.getCurrentSession();
        return (User) session.createCriteria(User.class)
                .add(Restrictions.eq("email", email)).uniqueResult();
    }

    /**
     * Retrieves a single user by email/login and password
     *
     * @return an instance of user
     */
    public User getUser(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        return (User) session.createCriteria(User.class)
                .add(Restrictions.eq("email", email))
                .add(Restrictions.eq("password", password)).uniqueResult();
    }

    /**
     * Saves a new user
     *
     * @param firstName an instance of a new user
     */
    public void saveUser(String firstName, String lastName, String email, String phoneNumber, String password, boolean hotelOwner) {
        Session session = sessionFactory.getCurrentSession();
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setHotelOwner(hotelOwner);
        session.save(user);
    }

    /**
     * Edits an existing person
     *
     * @param user an instance of user with updated fields
     */
    public void editUser(User user) {

        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing user via id
        User existingUser = (User) session.get(User.class, user.getUserId());

        // Assign updated values to this person
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setHotelOwner(user.getHotelOwner());

        // Save updates
        session.save(existingUser);

    }

}

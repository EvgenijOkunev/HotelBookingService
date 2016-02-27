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
        Session session = sessionFactory.openSession();
            return (List<User>) session.createCriteria(User.class)
                    .addOrder(Order.asc("firstName"))
                    .list();
    }

    /**
     * Retrieves a single user by id
     *
     * @param id the id of existing user
     * @return an instance of user
     */
    public User getUserById(int id) {
        Session session = sessionFactory.getCurrentSession();
            return (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("id", id)).uniqueResult();
    }

    /**
     * Retrieves a single user by email/login
     *
     * @return an instance of user
     */
    public User getUserByEmail(int email) {
        Session session = sessionFactory.getCurrentSession();
            return (User) session.createCriteria(User.class)
                    .add(Restrictions.eq("email", email));
    }

    /**
     * Saves a new user
     *
     * @param user an instance of a new user
     */
    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    /**
     * Deletes an existing user
     *
     * @param id an id of the existing user
     */
    public void deleteUser(Integer id) {
        Session session = sessionFactory.getCurrentSession();
            session.delete(session.get(User.class, id));
    }

    /**
     * Edits an existing person
     *
     * @param user an instance of user with updated fields
     */
    public void editUser(User user) {

        Session session = sessionFactory.getCurrentSession();

        // Retrieve existing user via id
        User existingUser = (User) session.get(User.class, user.getId());

        // Assign updated values to this person
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setHotelOwner(user.getHotelOwner());

        // Save updates
        session.save(existingUser);

    }

}

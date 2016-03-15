package com.geekhub.service;

import com.geekhub.model.Role;
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
public class RoleService {

    @Autowired
    private SessionFactory sessionFactory;

    public void createRole(String name) {
        Session session = sessionFactory.getCurrentSession();
        Role role = new Role();
        role.setName(name);
        session.save(role);
    }

    public List<Role> getAll() {
        return (List<Role>) sessionFactory.getCurrentSession()
                .createCriteria(Role.class).list();
    }

    public Role getRoleByName(String name) {
        return (Role) sessionFactory.getCurrentSession()
                .createCriteria(Role.class)
                .add(Restrictions.eq("name", name))
                .uniqueResult();
    }

}

package com.geekhub.config;

import com.geekhub.model.Role;
import com.geekhub.service.RoleService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RoleService roleService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Session session = sessionFactory.openSession();
        if (session.createCriteria(Role.class).list().size() == 0) {
            roleService.createRole("Administrator");
            roleService.createRole("Hotel owner");
            roleService.createRole("User");
        }
    }

}
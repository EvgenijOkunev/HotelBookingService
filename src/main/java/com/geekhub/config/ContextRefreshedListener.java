package com.geekhub.config;

import com.geekhub.model.Role;
import com.geekhub.service.CityService;
import com.geekhub.service.RoleService;
import com.geekhub.service.RoomTypeService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleService roleService;
    @Autowired
    private CityService cityService;
    @Autowired
    private RoomTypeService roomTypeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (roleService.getAll().size() == 0) {
            roleService.createRole("Administrator");
            roleService.createRole("Hotel owner");
            roleService.createRole("User");
        }
        if (cityService.getAll().size() == 0) {
            cityService.createCity("Черкассы", "Родина Шевченко");
            cityService.createCity("Киев", "Столица Украины");
            cityService.createCity("Одесса", "Культурная столица Украины");
        }
        if (roomTypeService.getAll().size() == 0) {
            roomTypeService.createRoomType("Apartments", "Аппартаменты");
            roomTypeService.createRoomType("Lux", "Люкс");
            roomTypeService.createRoomType("Business", "Бизнес");
            roomTypeService.createRoomType("Standard", "Стандарт");
            roomTypeService.createRoomType("Economy", "Эконом");
        }
    }

}
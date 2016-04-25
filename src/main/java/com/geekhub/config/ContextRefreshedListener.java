package com.geekhub.config;

import com.geekhub.service.CityService;
import com.geekhub.service.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private CityService cityService;
    @Autowired
    private RoomTypeService roomTypeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (cityService.getAll().size() == 0) {
            cityService.createCity("Черкассы");
            cityService.createCity("Киев");
            cityService.createCity("Одесса");
            cityService.createCity("Харьков");
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
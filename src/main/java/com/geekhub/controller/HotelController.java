package com.geekhub.controller;

import com.geekhub.model.*;
import com.geekhub.service.CityService;
import com.geekhub.service.HotelService;
import com.geekhub.service.RoomService;
import com.geekhub.service.RoomTypeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private CityService cityService;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private RoomService roomService;

    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String showAllUsers(Model model, HttpServletRequest request) throws Exception {

        List<Hotel> hotels = hotelService.getOwnersHotels((User) request.getSession().getAttribute("user"));
        model.addAttribute("hotels", hotels);

        return "hotelsManagement";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAddHotel(Model model) {
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("roomTypes", roomTypeService.getAll());
        return "addHotel";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addHotel(HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Integer stars = Integer.parseInt(request.getParameter("stars"));
        City city = cityService.getCityById(Integer.parseInt(request.getParameter("city")));
        User hotelOwner = (User) request.getSession().getAttribute("user");

        List<Room> rooms = new ArrayList<>();
        JSONArray roomsJSON = new JSONArray(request.getParameter("rooms"));
        for (int i = 0; i < roomsJSON.length(); i++) {
            JSONObject room = roomsJSON.getJSONObject(i);
            int roomsQuantity = (int) room.get("roomsQuantity");
            RoomType roomType = roomTypeService.getRoomTypeById((Integer) room.get("roomType"));
            int numberOfGuests = (int) room.get("numberOfGuests");
            Double pricePerNight = (Double) room.get("pricePerNight");
            for (int j = 0; j < roomsQuantity; j++) {
               rooms.add(roomService.createRoom(roomType, numberOfGuests, pricePerNight));
            }
        }

        hotelService.createHotel(name, description, stars, city, hotelOwner, rooms);

        return "";
    }

}

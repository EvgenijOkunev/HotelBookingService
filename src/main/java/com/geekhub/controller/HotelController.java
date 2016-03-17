package com.geekhub.controller;

import com.geekhub.model.*;
import com.geekhub.service.CityService;
import com.geekhub.service.HotelService;
import com.geekhub.service.RoomService;
import com.geekhub.service.RoomTypeService;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
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
    @Autowired
    private SessionFactory sessionFactory;

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
    @ResponseBody
    public String addHotel(HttpServletRequest request) throws IOException {
        String name = request.getParameter("name");
        byte[] descriptionBytes = request.getParameter("description").getBytes();
        Session currentSession = sessionFactory.openSession();
        Blob description = Hibernate.getLobCreator(currentSession).createBlob(descriptionBytes);
        currentSession.close();
        Integer stars = Integer.parseInt(request.getParameter("stars"));
        City city = cityService.getCityById(Integer.parseInt(request.getParameter("city")));
        User hotelOwner = (User) request.getSession().getAttribute("user");

        List<Room> rooms = new ArrayList<>();
        JSONArray roomsJSON = new JSONArray(request.getParameter("rooms"));
        for (int i = 0; i < roomsJSON.length(); i++) {
            JSONObject room = roomsJSON.getJSONObject(i);
            int roomsQuantity = Integer.parseInt(room.getString("roomsQuantity"));
            RoomType roomType = roomTypeService.getRoomTypeById(Integer.parseInt(room.getString("roomType")));
            int numberOfGuests = Integer.parseInt(room.getString("numberOfGuests"));
            Integer pricePerNight = Integer.parseInt(room.getString("pricePerNight"));
            for (int j = 0; j < roomsQuantity; j++) {
                rooms.add(roomService.createRoom(roomType, numberOfGuests, pricePerNight));
            }
        }

        hotelService.createHotel(name, description, stars, city, hotelOwner, rooms);

        return "";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEditHotel(@RequestParam(value = "hotelId", required = true) Integer hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("cities", cityService.getAll());
        model.addAttribute("rooms", roomService.getInformationAboutHotelRooms(hotel, roomTypeService.getAll()));
        return "editHotel";
    }

}

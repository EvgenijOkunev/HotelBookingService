package com.geekhub.controller;

import com.geekhub.model.Hotel;
import com.geekhub.model.User;
import com.geekhub.service.CityService;
import com.geekhub.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public String showAllUsers(Model model, HttpServletRequest request) throws Exception {

        List<Hotel> hotels = hotelService.getOwnersHotels((User) request.getSession().getAttribute("user"));
        model.addAttribute("hotels", hotels);

        return "hotelsManagement";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addHotel(Model model) {
        model.addAttribute("cities", cityService.getAll());
        return "addHotel";
    }

}

package com.geekhub.controller;

import com.geekhub.model.Hotel;
import com.geekhub.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Controller
public class BookingRequestController {

    @Autowired
    private HotelService hotelService;

    @RequestMapping(value = "processBookingRequest", method = RequestMethod.POST)
    @ResponseBody
    public String processBookingRequest(HttpServletRequest request) throws ParseException {
        Hotel hotel = hotelService.getHotelById(Integer.parseInt(request.getParameter("hotelId")));
        DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        Date arrivalDate = formatter.parse(request.getParameter("arrivalDate"));
        Date departureDate = formatter.parse(request.getParameter("departureDate"));
        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userPhoneNumber = request.getParameter("userPhoneNumber");

        return "";
    }

}

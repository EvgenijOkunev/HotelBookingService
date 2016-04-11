package com.geekhub.controller;

import com.geekhub.model.Hotel;
import com.geekhub.model.User;
import com.geekhub.service.BookingRequestService;
import com.geekhub.service.HotelService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Controller
public class BookingRequestController {

    @Autowired
    private HotelService hotelService;
    @Autowired
    private BookingRequestService bookingRequestService;

    @RequestMapping(value = "processBookingRequest", method = RequestMethod.POST)
    @ResponseBody
    public String processBookingRequest(HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
        Hotel hotel = hotelService.getHotelById(Integer.parseInt(request.getParameter("hotelId")));
        DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        Date arrivalDate = formatter.parse(request.getParameter("arrivalDate").replace("+", " "));
        Date departureDate = formatter.parse(request.getParameter("departureDate").replace("+", " "));
        User guest = (User) request.getSession().getAttribute("user");
        String guestName = request.getParameter("userName");
        String guestEmail = request.getParameter("userEmail");
        String guestPhoneNumber = request.getParameter("userPhoneNumber");
        Map<Integer, Integer> rooms = new HashMap<>();
        JSONArray roomsJSON = new JSONArray(request.getParameter("rooms"));
        for (int i = 0; i < roomsJSON.length(); i++) {
            JSONObject room = roomsJSON.getJSONObject(i);
            int roomTypeId = Integer.parseInt(room.getString("roomTypeId"));
            int roomsQuantity = Integer.parseInt(room.getString("roomsQuantity"));
            rooms.put(roomTypeId, roomsQuantity);
        }

        try {
            bookingRequestService.prepareBookingRequest(hotel, arrivalDate, departureDate, guest, guestName, guestEmail, guestPhoneNumber, rooms);
        }
        catch (RuntimeException e) {
            response.sendError(500);
        }

        return "";
    }

}

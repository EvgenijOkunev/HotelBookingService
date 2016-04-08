package com.geekhub.controller;

import com.geekhub.model.*;
import com.geekhub.service.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
    private BookingRequestService bookingRequestService;
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
        Blob description = stringToBlob(request.getParameter("description"));
        Integer stars = Integer.parseInt(request.getParameter("stars"));
        City city = cityService.getCityById(Integer.parseInt(request.getParameter("city")));
        User hotelOwner = (User) request.getSession().getAttribute("user");
        List<Room> rooms = roomService.roomsProcessing(request.getParameter("rooms"), null);

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

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public String editHotel(@RequestParam(value = "hotelId", required = true) Integer hotelId, HttpServletRequest request) throws UnsupportedEncodingException {
        Hotel hotel = hotelService.getHotelById(hotelId);
        String name = request.getParameter("name");
        Blob description = stringToBlob(request.getParameter("description"));
        Integer stars = Integer.parseInt(request.getParameter("stars"));
        City city = cityService.getCityById(Integer.parseInt(request.getParameter("city")));
        List<Room> rooms = roomService.roomsProcessing(request.getParameter("rooms"), hotel);

        hotelService.updateHotel(hotel, name, description, stars, city, rooms);

        return "";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public void deleteHotel(@RequestParam(value = "hotelId", required = true) Integer hotelId, HttpServletResponse response) throws IOException {
        Hotel hotel = hotelService.getHotelById(hotelId);
        roomService.deleteHotelRooms(hotel);
        hotelService.deleteHotel(hotel);
        response.sendRedirect("/hotels/management");
    }

    @RequestMapping(value = "/getSuitableHotels", method = RequestMethod.GET)
    @ResponseBody
    public String getSuitableHotels(@RequestParam(value = "arrivalDate", required = true) String arrivalDateParam,
                                    @RequestParam(value = "departureDate", required = true) String departureDateParam,
                                    @RequestParam(value = "city", required = true) String cityParam) throws ParseException, IOException, SQLException {

        DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        Date arrivalDate = formatter.parse(arrivalDateParam);
        Date departureDate = formatter.parse(departureDateParam);
        City city = cityService.getCityById(Integer.parseInt(cityParam));

        List<BookingRequest> bookingRequests = bookingRequestService.getBookingRequests(arrivalDate, departureDate);
        List<Room> freeRooms = roomService.getFreeRooms(bookingRequests);

        return hotelService.prepareSuitableHotelsInformation(freeRooms, city, roomTypeService.getAll());
    }

    @RequestMapping(value = "/getDetailedInformation", method = RequestMethod.GET)
    public String getDetailedInformation(@RequestParam(value = "arrivalDate", required = true) String arrivalDateParam,
                                         @RequestParam(value = "departureDate", required = true) String departureDateParam,
                                         @RequestParam(value = "hotelId", required = true) String hotelIdParam,
                                         Model model) throws ParseException, IOException, SQLException {

        DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        DateFormat dateFormatForJS = new SimpleDateFormat("yyyy-MM-dd");
        Date arrivalDate = formatter.parse(arrivalDateParam);
        Date departureDate = formatter.parse(departureDateParam);
        Hotel hotel = hotelService.getHotelById(Integer.parseInt(hotelIdParam));

        List<BookingRequest> bookingRequests = bookingRequestService.getBookingRequests(arrivalDate, departureDate, roomService.getHotelRooms(hotel));
        List<Room> freeRooms = roomService.getFreeRooms(bookingRequests, hotel);
        List<HashMap<String, String>> groupedFreeRooms = roomService.groupRoomsByType(freeRooms);

        model.addAttribute("hotel", hotel);
        model.addAttribute("arrivalDate", arrivalDate);
        model.addAttribute("departureDate", departureDate);
        model.addAttribute("arrivalDateString", dateFormatForJS.format(arrivalDate));
        model.addAttribute("departureDateString", dateFormatForJS.format(departureDate));
        model.addAttribute("freeRooms", groupedFreeRooms);
        model.addAttribute("freeRoomsQuantity", freeRooms.size());
        model.addAttribute("nightsQuantity", Days.daysBetween(new DateTime(arrivalDate), new DateTime(departureDate)).getDays());

        return "detailedHotelInformation";
    }

    @RequestMapping(value = "/getFreeRoomsInformation", method = RequestMethod.GET)
    @ResponseBody
    public String getFreeRoomsInformation(@RequestParam(value = "arrivalDate", required = true) String arrivalDateParam,
                                          @RequestParam(value = "departureDate", required = true) String departureDateParam,
                                          @RequestParam(value = "hotelId", required = true) String hotelIdParam) throws ParseException {

        DateFormat formatter = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.ENGLISH);
        Date arrivalDate = formatter.parse(arrivalDateParam);
        Date departureDate = formatter.parse(departureDateParam);
        Hotel hotel = hotelService.getHotelById(Integer.parseInt(hotelIdParam));

        List<BookingRequest> bookingRequests = bookingRequestService.getBookingRequests(arrivalDate, departureDate, roomService.getHotelRooms(hotel));
        List<Room> freeRooms = roomService.getFreeRooms(bookingRequests, hotel);
        List<HashMap<String, String>> groupedFreeRooms = roomService.groupRoomsByType(freeRooms);

        JSONObject freeRoomsInformation = new JSONObject();
        freeRoomsInformation.put("roomsInformation", groupedFreeRooms);
        freeRoomsInformation.put("freeRoomsQuantity", freeRooms.size());

        return freeRoomsInformation.toString();
    }

    private Blob stringToBlob(String parameter) throws UnsupportedEncodingException {
        byte[] descriptionBytes = parameter.getBytes("UTF-8");
        Session currentSession = sessionFactory.openSession();
        Blob description = Hibernate.getLobCreator(currentSession).createBlob(descriptionBytes);
        currentSession.close();
        return description;
    }

}

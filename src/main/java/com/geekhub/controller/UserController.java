package com.geekhub.controller;

import com.geekhub.model.User;
import com.geekhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/editUserProfile", method = RequestMethod.GET)
    public String getEdit(HttpServletRequest request, Model model) {

        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("userAttribute", user);

        return "editUserProfile";
    }

    /**
     * Edits an existing person by delegating the processing to UserService.
     */
    @RequestMapping(value = "/editUserProfile", method = RequestMethod.POST)
    public void saveEdit(@ModelAttribute("userAttribute") User user, HttpServletRequest request, HttpServletResponse response) throws IOException {

        userService.editUser(user);
        request.getSession().setAttribute("user", user);

        response.sendRedirect("/");
    }

    @RequestMapping(value = "/userLoginProcessing", method = RequestMethod.POST)
    @ResponseBody
    public String userLoginProcessing(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = userService.getUser(email, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
        } else {
            response.sendError(500);
        }

        return "{\"msg\":\"success\"}";
    }

    @RequestMapping(value = "/checkEmailUnique", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmailUnique(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        User user = userService.getUser(email);
        if (user != null) {
            response.sendError(500);
        }

        return "{\"msg\":\"success\"}";
    }

    @RequestMapping(value = "/userRegistrationProcessing", method = RequestMethod.POST)
    @ResponseBody
    public String userRegistrationProcessing(HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        boolean hotelOwner = request.getParameter("hotelOwner").equals("true");

        userService.saveUser(firstName, lastName, email, phoneNumber, password, hotelOwner);

        return "{\"msg\":\"success\"}";
    }

}

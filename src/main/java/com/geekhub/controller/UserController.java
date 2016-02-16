package com.geekhub.controller;

import com.geekhub.model.User;
import com.geekhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/show-all", method = RequestMethod.GET)
    public String showAllUsers(Model model) {
        List<User> users = userService.getAll();

        model.addAttribute("users", users);

        return "usersView";
    }

}

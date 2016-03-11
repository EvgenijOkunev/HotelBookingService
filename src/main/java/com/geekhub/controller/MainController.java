package com.geekhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping
    public String showMainPage() throws Exception {
        return "mainPage";
    }

    @RequestMapping(value = "/login")
    public String showLoginPage() throws Exception {
        return "userLogin";
    }

    @RequestMapping(value = "/registration")
    public String showRegistrationPage() throws Exception {
        return "userRegistration";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().setAttribute("user", null);
        return "mainPage";
    }

}

package com.geekhub.controller;

import com.geekhub.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private CityService cityService;

    @RequestMapping
    public String showMainPage(Model model) throws Exception {
        model.addAttribute("cities", cityService.getAll());
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

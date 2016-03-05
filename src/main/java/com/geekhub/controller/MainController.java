package com.geekhub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}

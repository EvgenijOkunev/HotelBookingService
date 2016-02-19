package com.geekhub;

import com.geekhub.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;

@SpringBootApplication
public class HotelBookingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingServiceApplication.class, args);
    }

}

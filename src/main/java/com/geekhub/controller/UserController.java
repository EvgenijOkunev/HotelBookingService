package com.geekhub.controller;

import com.geekhub.model.User;
import com.geekhub.service.RoleService;
import com.geekhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /**
     * Handles and retrieves all users and show it in a JSP page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/show-all", method = RequestMethod.GET)
    public String showAllUsers(Model model) throws Exception {

        List<User> users = userService.getAll();
        model.addAttribute("users", users);

        return "usersView";
    }

    /**
     * Retrieves the add page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String getAdd(Model model) {

        model.addAttribute("userAttribute", new User());

        return "addUser";
    }

    /**
     * Adds a new user by delegating the processing to UserService.
     * Displays a confirmation JSP page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("userAttribute") User user) {

        user.setRole(user.getHotelOwner() ? roleService.getRoleByName("Hotel owner") : roleService.getRoleByName("User"));
        userService.saveUser(user);

        return "addedUser";
    }

    /**
     * Deletes an existing user by delegating the processing to UserService.
     * Displays a confirmation JSP page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam(value = "userId", required = true) Integer userId, Model model) {

        userService.deleteUser(userId);
        model.addAttribute("userId", userId);

        return "deletedUser";
    }

    /**
     * Retrieves the edit page
     *
     * @return the name of the JSP page
     */
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

        return "";
    }

    @RequestMapping(value = "/checkEmailUnique", method = RequestMethod.POST)
    @ResponseBody
    public String checkEmailUnique(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        User user = userService.getUser(email);
        if (user != null) {
            response.sendError(500);
        }

        return "";
    }

    @RequestMapping(value = "/userRegistrationProcessing", method = RequestMethod.POST)
    @ResponseBody
    public String userRegistrationProcessing(HttpServletRequest request) throws IOException {
        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setEmail(request.getParameter("email"));
        user.setPhoneNumber(request.getParameter("phoneNumber"));
        user.setPassword(request.getParameter("password"));
        user.setHotelOwner(request.getParameter("hotelOwner").equals("on"));

        userService.saveUser(user);

        return "";
    }

}

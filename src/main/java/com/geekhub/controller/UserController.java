package com.geekhub.controller;

import com.geekhub.model.User;
import com.geekhub.service.RoleService;
import com.geekhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

        // The "userAttribute" model has been passed to the controller from the JSP
        // We use the name "userAttribute" because the JSP uses that name

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
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String getEdit(@RequestParam(value = "userId", required = true) Integer userId, Model model) {

        // Retrieve existing Person and add to model
        model.addAttribute("userAttribute", userService.getUserById(userId));

        return "editUser";
    }

    /**
     * Edits an existing person by delegating the processing to UserService.
     * Displays a confirmation JSP page
     *
     * @return the name of the JSP page
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String saveEdit(@ModelAttribute("userAttribute") User user,
                           @RequestParam(value = "userId", required = true) Integer userId,
                           Model model) {

        // The "userAttribute" model has been passed to the controller from the JSP
        // We use the name "userAttribute" because the JSP uses that name

        // We manually assign the id because we disabled it in the JSP page
        // When a field is disabled it will not be included in the ModelAttribute
        user.setUserId(userId);

        userService.editUser(user);

        // Add id reference to Model
        model.addAttribute("userId", userId);

        return "editedUser";
    }

}

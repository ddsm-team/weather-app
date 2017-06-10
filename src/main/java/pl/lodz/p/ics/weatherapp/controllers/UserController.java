package pl.lodz.p.ics.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ics.weatherapp.exceptions.BadCredentialsException;
import pl.lodz.p.ics.weatherapp.exceptions.UserAlreadyExistsException;
import pl.lodz.p.ics.weatherapp.services.UserService;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(required = true) String login, @RequestParam(required = true) String password,
            Model model) {

        try {
            userService.login(login, password);

            model.addAttribute("success", "User logged in.");
        } catch (BadCredentialsException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@RequestParam(required = true) String login, @RequestParam(required = true) String password,
            @RequestParam(required = true) String city, @RequestParam(required = true) String country, Model model) {

        try {
            userService.register(login, password, city, country);
            model.addAttribute("success", "Account created.");
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "register";
    }
}

package pl.lodz.p.ics.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ics.weatherapp.models.User;
import pl.lodz.p.ics.weatherapp.services.UserManagementService;
import pl.lodz.p.ics.weatherapp.services.UserSecurityService;

@SuppressWarnings("SameReturnValue")
@Controller
public class UserController {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private UserSecurityService userSecurityService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("logged_in", userSecurityService.isLoggedIn());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {
        model.addAttribute("logged_in", userSecurityService.isLoggedIn());
        try {
            userSecurityService.autologin(username, password);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(Model model) {
        model.addAttribute("logged_in", userSecurityService.isLoggedIn());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String city,
                           @RequestParam String country,
                           Model model) {
        model.addAttribute("logged_in", userSecurityService.isLoggedIn());
        try {
            userManagementService.save(new User(username, password, city, country));
            userSecurityService.autologin(username, password);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/";
    }
}

package pl.lodz.p.ics.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ics.weatherapp.dao.UserDao;
import pl.lodz.p.ics.weatherapp.exceptions.LocationNotFoundException;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;
import pl.lodz.p.ics.weatherapp.models.Weather;
import pl.lodz.p.ics.weatherapp.services.UserSecurityService;
import pl.lodz.p.ics.weatherapp.services.WeatherService;

@SuppressWarnings("SameReturnValue")
@Controller
public class MainController {

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("logged_in", userSecurityService.isLoggedIn());
        if (userSecurityService.isLoggedIn()) {
            String username = ((User) userSecurityService.getUser()).getUsername();
            pl.lodz.p.ics.weatherapp.models.User user = userDao.findByUsername(username);
            String locationName
                    = String.format("%s, %s", user.getCity(), user.getCountry());
            try {
                LocationWeather weather
                        = weatherService.getWeatherForLocation(locationName);

                Weather w = weather.getWeather();

                model.addAttribute("user_location", weather.getLocation().getName());
                model.addAttribute("user_weather", true);
                model.addAttribute("user_tMin",
                        Double.valueOf(String.format("%.1f", w.getTemperatureMin())));
                model.addAttribute("user_wSpd",
                        Integer.valueOf(String.format("%.0f", w.getWindSpeed())));
                model.addAttribute("user_wDir",
                        Integer.valueOf(String.format("%.0f", w.getWindDirection())));
                model.addAttribute("user_cDns",
                        Integer.valueOf(String.format("%.0f",
                                100.0 * w.getCloudsDensity())));
            } catch (LocationNotFoundException e) {
                model.addAttribute("error", e.getMessage());
                return "home";
            }
        }
        return "home";
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public String weather(ModelMap model,
                          @RequestParam(value = "location") String locationName) {
        model.addAttribute("logged_in", userSecurityService.isLoggedIn());
        try {
            LocationWeather weather = weatherService.getWeatherForLocation(locationName);

            Weather w = weather.getWeather();

            model.addAttribute("location", weather.getLocation().getName());
            model.addAttribute("weather", true);
            model.addAttribute("tMin",
                    Double.valueOf(String.format("%.1f", w.getTemperatureMin())));
            model.addAttribute("tMax",
                    Double.valueOf(String.format("%.1f", w.getTemperatureMax())));
            model.addAttribute("pres",
                    Integer.valueOf(String.format("%.0f", w.getPressure())));
            model.addAttribute("humi",
                    Double.valueOf(String.format("%.1f", 100.0 * w.getHumidity())));
            model.addAttribute("wSpd",
                    Integer.valueOf(String.format("%.0f", w.getWindSpeed())));
            model.addAttribute("wDir",
                    Integer.valueOf(String.format("%.0f", w.getWindDirection())));
            model.addAttribute("cDns",
                    Integer.valueOf(String.format("%.0f", 100.0 * w.getCloudsDensity())));
        } catch (LocationNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "weather";
        }

        return "weather";
    }
}

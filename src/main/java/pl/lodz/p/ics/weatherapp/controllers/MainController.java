package pl.lodz.p.ics.weatherapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.lodz.p.ics.weatherapp.exceptions.LocationNotFoundException;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;
import pl.lodz.p.ics.weatherapp.models.Weather;
import pl.lodz.p.ics.weatherapp.services.WeatherService;

@Controller
public class MainController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public String weather(ModelMap model, @RequestParam(value = "location") String locationName) {

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

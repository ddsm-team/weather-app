package pl.lodz.p.ics.weatherapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.darksky.controllers.DarkSkyApiController;
import pl.lodz.p.ics.weatherapp.api.darksky.services.DarkSkyApiService;
import pl.lodz.p.ics.weatherapp.api.google.controllers.GoogleApiController;
import pl.lodz.p.ics.weatherapp.api.google.models.GoogleInfo;
import pl.lodz.p.ics.weatherapp.api.openweathermap.controllers.OpenWeatherMapApiController;
import pl.lodz.p.ics.weatherapp.api.openweathermap.services.OpenWeatherMapApiService;
import pl.lodz.p.ics.weatherapp.api.services.WeatherApiAggregatorService;
import pl.lodz.p.ics.weatherapp.dao.LocationDao;
import pl.lodz.p.ics.weatherapp.dao.LocationWeatherDao;
import pl.lodz.p.ics.weatherapp.dao.WeatherDao;
import pl.lodz.p.ics.weatherapp.models.Location;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;
import pl.lodz.p.ics.weatherapp.models.Weather;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private WeatherApiAggregatorService aggregatorService;

    @Autowired
    private DarkSkyApiService darkSkyApiService;

    @Autowired
    private OpenWeatherMapApiService openWeatherMapApiService;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private WeatherDao weatherDao;

    @Autowired
    private LocationWeatherDao locationWeatherDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @Transactional
    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public String weather(ModelMap model,
                          @RequestParam(value = "location") String locationName) {
        GoogleApiController googleApiController
                = new GoogleApiController(locationName);
        googleApiController.setObjectMapper(new ObjectMapper());
        GoogleInfo info = null;
        try {
            info = googleApiController.getMappedObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (info == null) {
            model.addAttribute("error", "Could not find the place.");
            return "weather";
        }

        locationName = info.getResults().get(0).getFormattedAddress();
        model.addAttribute("location", locationName);

        Timestamp nowFrom = new Timestamp(System.currentTimeMillis() - 3600000);
        Timestamp nowTo = new Timestamp(System.currentTimeMillis());
        List<LocationWeather> locationWeatherList = locationWeatherDao
                .findAllByLocationNameAndTimestampBetween(locationName, nowFrom, nowTo);

        LocationWeather locationWeather;

        System.out.println(locationWeatherList.size());

        if (locationWeatherList.isEmpty()) {
            Double latitude
                    = info.getResults().get(0).getGeometry().getLocation().getLat();
            Double longitude
                    = info.getResults().get(0).getGeometry().getLocation().getLng();

            DarkSkyApiController darkSkyApiController
                    = new DarkSkyApiController(latitude, longitude);
            OpenWeatherMapApiController openWeatherMapApiController
                    = new OpenWeatherMapApiController(latitude, longitude);

            darkSkyApiController.setObjectMapper(new ObjectMapper());
            openWeatherMapApiController.setObjectMapper(new ObjectMapper());

            darkSkyApiController.run();
            openWeatherMapApiController.run();

            darkSkyApiService.setController(darkSkyApiController);
            openWeatherMapApiService.setController(openWeatherMapApiController);

            aggregatorService.addService(darkSkyApiService);
            aggregatorService.addService(openWeatherMapApiService);

            WeatherApiData data = aggregatorService.getData(latitude, longitude);

            Location location = new Location();
            location.setName(locationName);
            location.setLatitude(latitude);
            location.setLongitude(longitude);
            location = locationDao.save(location);

            Weather weather = new Weather();
            weather.setTemperatureMin(data.getTemperatureMin());
            weather.setTemperatureMax(data.getTemperatureMax());
            weather.setPressure(data.getPressure());
            weather.setHumidity(data.getHumidity());
            weather.setWindSpeed(data.getWindSpeed());
            weather.setWindDirection(data.getWindDirection());
            weather.setCloudsDensity(data.getCloudsDensity());
            weather = weatherDao.save(weather);

            locationWeather = new LocationWeather();
            locationWeather.setTimestamp(nowTo);
            locationWeather.setLocation(location);
            locationWeather.setWeather(weather);
            locationWeather = locationWeatherDao.save(locationWeather);
        } else {
            locationWeather = locationWeatherList.get(0);
        }

        if (locationWeather != null) {
            Weather w = locationWeather.getWeather();
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
        } else {
            model.addAttribute("error", "Could not find the place.");
        }

        return "weather";
    }
}

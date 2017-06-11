package pl.lodz.p.ics.weatherapp.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import pl.lodz.p.ics.weatherapp.exceptions.LocationNotFoundException;
import pl.lodz.p.ics.weatherapp.models.Location;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;
import pl.lodz.p.ics.weatherapp.models.Weather;

@Service
public class WeatherService {

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

    public LocationWeather getWeatherForLocation(String locationName)
            throws LocationNotFoundException {
        GoogleInfo info = getGoogleInfoForLocation(locationName);
        if (info == null || info.getStatus().equals("ZERO_RESULTS")) {
            throw new LocationNotFoundException("Could not find the place.");
        }

        locationName = info.getResults().get(0).getFormattedAddress();

        Timestamp nowFrom = new Timestamp(System.currentTimeMillis() - 3600000);
        Timestamp nowTo = new Timestamp(System.currentTimeMillis());
        List<LocationWeather> locationWeatherList = locationWeatherDao
                .findAllByLocationNameAndTimestampBetween(locationName, nowFrom, nowTo);

        LocationWeather locationWeather;

        if (locationWeatherList.isEmpty()) {
            locationWeather = saveLocationWeather(locationName, info, nowTo);
        } else {
            locationWeather = locationWeatherList.get(0);
        }

        return locationWeather;
    }

    @Transactional
    public LocationWeather saveLocationWeather(String locationName, GoogleInfo info,
                                               Timestamp nowTo) {
        LocationWeather locationWeather;
        Double latitude = info.getResults().get(0).getGeometry().getLocation().getLat();
        Double longitude = info.getResults().get(0).getGeometry().getLocation().getLng();

        DarkSkyApiController darkSkyApiController
                = new DarkSkyApiController(latitude, longitude);
        OpenWeatherMapApiController openWeatherMapApiController
                = new OpenWeatherMapApiController(latitude,
                longitude);

        darkSkyApiController.setObjectMapper(new ObjectMapper());
        openWeatherMapApiController.setObjectMapper(new ObjectMapper());

        darkSkyApiController.run();
        openWeatherMapApiController.run();

        darkSkyApiService.setController(darkSkyApiController);
        openWeatherMapApiService.setController(openWeatherMapApiController);

        aggregatorService.addService(darkSkyApiService);
        aggregatorService.addService(openWeatherMapApiService);

        WeatherApiData data = aggregatorService.getData(latitude, longitude);

        Location location = saveLocationData(locationName, latitude, longitude);
        Weather weather = saveWeatherData(data);

        locationWeather = new LocationWeather();
        locationWeather.setTimestamp(nowTo);
        locationWeather.setLocation(location);
        locationWeather.setWeather(weather);
        locationWeather = locationWeatherDao.save(locationWeather);
        return locationWeather;
    }

    @Transactional
    private Weather saveWeatherData(WeatherApiData data) {
        Weather weather = new Weather();
        weather.setTemperatureMin(data.getTemperatureMin());
        weather.setTemperatureMax(data.getTemperatureMax());
        weather.setPressure(data.getPressure());
        weather.setHumidity(data.getHumidity());
        weather.setWindSpeed(data.getWindSpeed());
        weather.setWindDirection(data.getWindDirection());
        weather.setCloudsDensity(data.getCloudsDensity());
        weather = weatherDao.save(weather);
        return weather;
    }

    @Transactional
    private Location saveLocationData(String locationName, Double latitude,
                                      Double longitude) {
        Location location = new Location();
        location.setName(locationName);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location = locationDao.save(location);
        return location;
    }

    private GoogleInfo getGoogleInfoForLocation(String locationName) {
        GoogleApiController googleApiController = new GoogleApiController(locationName);
        googleApiController.setObjectMapper(new ObjectMapper());
        GoogleInfo info = null;
        try {
            info = googleApiController.getMappedObject();
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

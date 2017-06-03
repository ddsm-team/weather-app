package pl.lodz.p.ics.weatherapp.api.openweathermap.services;

import org.springframework.stereotype.Service;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.WeatherApiInterface;
import pl.lodz.p.ics.weatherapp.api.openweathermap.controllers.OpenWeatherMapApiController;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.OpenWeatherMap;

@Service
public class OpenWeatherMapApiService implements WeatherApiInterface {

    public OpenWeatherMapApiService() { }

    @Override
    public WeatherApiData getData(Double latitude, Double longitude) {
        OpenWeatherMapApiController controller = new OpenWeatherMapApiController(latitude,
                longitude);
        OpenWeatherMap openWeatherMap = controller.getOpenWeatherMap();

        return new WeatherApiData(
                openWeatherMap.getMain().getTempMin() - 273.15,
                openWeatherMap.getMain().getTempMax() - 273.15,
                openWeatherMap.getMain().getPressure().doubleValue(),
                openWeatherMap.getMain().getHumidity().doubleValue() / 100.0,
                openWeatherMap.getWind().getSpeed(),
                openWeatherMap.getWind().getDeg().doubleValue(),
                openWeatherMap.getClouds().getAll().doubleValue() / 100.0
        );
    }
}

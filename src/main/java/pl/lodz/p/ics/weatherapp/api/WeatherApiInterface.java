package pl.lodz.p.ics.weatherapp.api;

public interface WeatherApiInterface {

    WeatherApiData getData(Double latitude, Double longitude);
}

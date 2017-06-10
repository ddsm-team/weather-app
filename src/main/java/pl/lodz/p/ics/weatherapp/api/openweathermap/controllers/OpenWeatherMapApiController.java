package pl.lodz.p.ics.weatherapp.api.openweathermap.controllers;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import pl.lodz.p.ics.weatherapp.api.WeatherApiController;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.OpenWeatherMap;

import java.io.IOException;
import java.util.HashMap;

public class OpenWeatherMapApiController extends WeatherApiController {

    private OpenWeatherMap openWeatherMap;

    public OpenWeatherMapApiController() { }

    public OpenWeatherMapApiController(Double latitude, Double longitude) {
        super(latitude, longitude);
    }

    @Override
    public void run() {
        String jsonResponse = null;
        try {
            jsonResponse = sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonResponse != null && !jsonResponse.equals("")) {
            try {
                setOpenWeatherMap(getObjectMapper().readValue(jsonResponse,
                        OpenWeatherMap.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            setOpenWeatherMap(null);
        }
    }

    @Override
    protected HttpRequestBase getRequestObject() {
        String appid = "bd03e16df8e9644e200277c691e08b24";

        try {
            String url = "http://api.openweathermap.org/data/2.5/weather";

            HashMap<String, String> params = new HashMap<>(3);
            params.put("appid", appid);
            params.put("lat", Double.toString(getLatitude()));
            params.put("lon", Double.toString(getLongitude()));

            return new HttpGet(buildUri(url, params));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public OpenWeatherMap getOpenWeatherMap() {
        return openWeatherMap;
    }

    public void setOpenWeatherMap(OpenWeatherMap openWeatherMap) {
        this.openWeatherMap = openWeatherMap;
    }
}

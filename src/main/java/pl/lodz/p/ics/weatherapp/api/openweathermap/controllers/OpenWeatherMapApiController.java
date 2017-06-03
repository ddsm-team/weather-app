package pl.lodz.p.ics.weatherapp.api.openweathermap.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import pl.lodz.p.ics.weatherapp.api.WeatherApiController;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.OpenWeatherMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class OpenWeatherMapApiController extends WeatherApiController {

    private OpenWeatherMap openWeatherMap;

    public OpenWeatherMapApiController(Double latitude, Double longitude) {
        super(latitude, longitude);
    }

    @Override
    public void run() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = sendRequest();

        if (!jsonResponse.equals("")) {
            try {
                openWeatherMap = mapper.readValue(jsonResponse, OpenWeatherMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            openWeatherMap = null;
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
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OpenWeatherMap getOpenWeatherMap() {
        return openWeatherMap;
    }
}

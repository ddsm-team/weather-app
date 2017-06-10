package pl.lodz.p.ics.weatherapp.api.darksky.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import pl.lodz.p.ics.weatherapp.api.WeatherApiController;
import pl.lodz.p.ics.weatherapp.api.darksky.models.DarkSky;

public class DarkSkyApiController extends WeatherApiController {

    private DarkSky darkSky;

    public DarkSkyApiController(Double latitude, Double longitude) {
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
                setDarkSky(getObjectMapper().readValue(jsonResponse,
                        DarkSky.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            setDarkSky(null);
        }
    }

    public DarkSky getDarkSky() {
        return darkSky;
    }

    public void setDarkSky(DarkSky darkSky) {
        this.darkSky = darkSky;
    }

    @Override
    protected HttpRequestBase getRequestObject() {
        String key = "84be0d6fd8860f558926f31b50852501";

        long unixTime = System.currentTimeMillis() / 1000L;
        HashMap<String, String> params = new HashMap<>(3);
        params.put("exclude", "minutely,hourly,daily,alerts");

        try {
            String url = String.format("https://api.darksky.net/forecast/%s/%f,%f,%d",
                    key, getLatitude(), getLongitude(), unixTime);

            return new HttpGet(buildUri(url, params));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package pl.lodz.p.ics.weatherapp.api.google.controllers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import pl.lodz.p.ics.weatherapp.api.HttpRequestController;
import pl.lodz.p.ics.weatherapp.api.google.models.GoogleInfo;

public class GoogleApiController extends HttpRequestController {

    private String address;

    public GoogleApiController(String address) {
        this.address = address;
    }

    public GoogleInfo getMappedObject() throws IOException {
        String jsonResponse = null;
        try {
            jsonResponse = sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonResponse != null && !jsonResponse.equals("")) {
            try {
                return getObjectMapper().readValue(jsonResponse, GoogleInfo.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getAddress() {
        return address.replace(" ", "%20");
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected HttpRequestBase getRequestObject() {
        String api_key = "AIzaSyB3IdbTz3MH1nu6zdqQb4r06Dt866YkcnQ";

        try {
            String url = "https://maps.googleapis.com/maps/api/geocode/json";

            HashMap<String, String> params = new HashMap<>(3);
            params.put("key", api_key);
            params.put("address", getAddress());

            return new HttpPost(buildUri(url, params));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
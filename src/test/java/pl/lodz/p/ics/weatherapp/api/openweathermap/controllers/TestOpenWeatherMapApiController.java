package pl.lodz.p.ics.weatherapp.api.openweathermap.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Test;
import org.mockito.Mockito;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.OpenWeatherMap;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestOpenWeatherMapApiController {

    @Test
    public void testRunShouldCreateOpenWeatherMapObject() throws IOException {
        // GIVEN
        OpenWeatherMap owm = mock(OpenWeatherMap.class);

        String jsonResponse = getExpectedJsonResponse();
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readValue(jsonResponse, OpenWeatherMap.class))
                .thenReturn(owm);

        OpenWeatherMapApiController controller = mock(OpenWeatherMapApiController.class);
        when(controller.sendRequest()).thenReturn(jsonResponse);
        when(controller.getObjectMapper()).thenReturn(objectMapper);
        Mockito.doCallRealMethod().when(controller).setOpenWeatherMap(owm);
        Mockito.doCallRealMethod().when(controller).getOpenWeatherMap();
        Mockito.doCallRealMethod().when(controller).run();
        // WHEN
        controller.run();
        // THEN
        assertEquals(owm, controller.getOpenWeatherMap());
    }

    @Test
    public void testGetRequestObjectShouldReturnHttpGetRequestWithUri()
            throws URISyntaxException {
        // GIVEN
        String expectedMethod = "GET";

        OpenWeatherMapApiController controller = mock(OpenWeatherMapApiController.class);
        URI expectedUri = new URI("http://api.openweathermap.org/data/2.5/weather"
                + "?appid=bd03e16df8e9644e200277c691e08b24"
                + "&lat=100.0"
                + "&lon=120.0");
        when(controller.buildUri(anyString(), anyMapOf(String.class, String.class)))
                .thenReturn(expectedUri);
        when(controller.getRequestObject()).thenCallRealMethod();
        // WHEN
        HttpRequestBase request = controller.getRequestObject();
        // THEN
        assertEquals(expectedMethod, request.getMethod());
        assertEquals(expectedUri, request.getURI());
    }

    @Test
    public void testBuildUriShouldReturnValidUri() throws URISyntaxException {
        // GIVEN
        OpenWeatherMapApiController controller = mock(OpenWeatherMapApiController.class);

        Double expectedLatitude = 100.0;
        Double expectedLongitude = 120.0;

        String appid = "bd03e16df8e9644e200277c691e08b24";
        String url = "http://api.openweathermap.org/data/2.5/weather";
        HashMap<String, String> params = new HashMap<>(3);
        params.put("appid", appid);
        params.put("lat", Double.toString(expectedLatitude));
        params.put("lon", Double.toString(expectedLongitude));

        URI expectedUri = new URI(url
                + "?lat=" + Double.toString(expectedLatitude)
                + "&lon=" + Double.toString(expectedLongitude)
                + "&appid=" + appid);

        when(controller.buildUri(anyString(), anyMapOf(String.class, String.class)))
                .thenCallRealMethod();

        when(controller.getLatitude()).thenReturn(100.0);
        when(controller.getLongitude()).thenReturn(120.0);
        // WHEN
        URI uri = controller.buildUri(url, params);
        //THEN
        assertEquals(expectedUri, uri);
    }

    private String getExpectedJsonResponse() {
        return "{\n"
                + "    \"coord\": {\n"
                + "        \"lon\": -0.13,\n"
                + "        \"lat\": 51.51\n"
                + "    },\n"
                + "    \"weather\": [\n"
                + "        {\n"
                + "            \"id\": 500,\n"
                + "            \"main\": \"Rain\",\n"
                + "            \"description\": \"light rain\",\n"
                + "            \"icon\": \"10n\"\n"
                + "        }\n"
                + "    ],\n"
                + "    \"base\": \"cmc stations\",\n"
                + "    \"main\": {\n"
                + "        \"temp\": 286.164,\n"
                + "        \"pressure\": 1017.58,\n"
                + "        \"humidity\": 96,\n"
                + "        \"temp_min\": 286.164,\n"
                + "        \"temp_max\": 286.164,\n"
                + "        \"sea_level\": 1027.69,\n"
                + "        \"grnd_level\": 1017.58\n"
                + "    },\n"
                + "    \"wind\": {\n"
                + "        \"speed\": 3.61,\n"
                + "        \"deg\": 165.001\n"
                + "    },\n"
                + "    \"rain\": {\n"
                + "        \"3h\": 0.185\n"
                + "    },\n"
                + "    \"clouds\": {\n"
                + "        \"all\": 80\n"
                + "    },\n"
                + "    \"dt\": 1446583128,\n"
                + "    \"sys\": {\n"
                + "        \"message\": 0.003,\n"
                + "        \"country\": \"GB\",\n"
                + "        \"sunrise\": 1446533902,\n"
                + "        \"sunset\": 1446568141\n"
                + "    },\n"
                + "    \"id\": 2643743,\n"
                + "    \"name\": \"London\",\n"
                + "    \"cod\": 200\n"
                + "}";
    }
}

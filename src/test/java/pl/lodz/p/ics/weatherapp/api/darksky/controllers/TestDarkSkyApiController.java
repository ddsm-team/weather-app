package pl.lodz.p.ics.weatherapp.api.darksky.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Test;
import org.mockito.Mockito;
import pl.lodz.p.ics.weatherapp.api.darksky.models.DarkSky;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestDarkSkyApiController {

    @Test
    public void testRunShouldCreateOpenWeatherMapObject() throws IOException {
        // GIVEN
        DarkSky ds = mock(DarkSky.class);

        String jsonResponse = getExpectedJsonResponse();
        ObjectMapper objectMapper = mock(ObjectMapper.class);
        when(objectMapper.readValue(jsonResponse, DarkSky.class))
                .thenReturn(ds);

        DarkSkyApiController controller = mock(DarkSkyApiController.class);
        when(controller.sendRequest()).thenReturn(jsonResponse);
        when(controller.getObjectMapper()).thenReturn(objectMapper);
        Mockito.doCallRealMethod().when(controller).setDarkSky(ds);
        //noinspection ResultOfMethodCallIgnored
        Mockito.doCallRealMethod().when(controller).getDarkSky();
        Mockito.doCallRealMethod().when(controller).run();
        // WHEN
        controller.run();
        // THEN
        assertEquals(ds, controller.getDarkSky());
    }

    @Test
    public void testGetRequestObjectShouldReturnHttpGetRequestWithUri()
            throws URISyntaxException {
        // GIVEN
        String key = "84be0d6fd8860f558926f31b50852501";
        String expectedMethod = "GET";

        DarkSkyApiController controller = mock(DarkSkyApiController.class);
        URI expectedUri = new URI(String.format(
                "https://api.darksky.net/forecast/%s/%f,%f,%d?exclude=%s", key, 100.0,
                120.0, 0, "minutely,hourly,daily,alerts"));
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
        DarkSkyApiController controller = mock(DarkSkyApiController.class);

        Double expectedLatitude = 100.0;
        Double expectedLongitude = 120.0;

        String key = "84be0d6fd8860f558926f31b50852501";
        String url = String.format("https://api.darksky.net/forecast/%s/%f,%f,%d", key,
                expectedLatitude, expectedLongitude, 0);
        HashMap<String, String> params = new HashMap<>(3);
        params.put("exclude", "minutely,hourly,daily,alerts");

        URI expectedUri = new URI(url + "?exclude=minutely,hourly,daily,alerts");

        when(controller.buildUri(anyString(), anyMapOf(String.class, String.class)))
                .thenCallRealMethod();

        when(controller.getLatitude()).thenReturn(expectedLatitude);
        when(controller.getLongitude()).thenReturn(expectedLongitude);
        // WHEN
        URI uri = controller.buildUri(url, params);
        //THEN
        assertEquals(expectedUri, uri);
    }

    private String getExpectedJsonResponse() {
        return "{\"latitude\":0,\"longitude\":0,\"timezone\":\"Etc/GMT\",\"offset\":0,"
                + "\"currently\":{\"time\":1497097254,\"summary\":\"Mostly Cloudy\","
                + "\"icon\":\"partly-cloudy-day\",\"precipIntensity\":0.0009,"
                + "\"precipProbability\":0.01,\"precipType\":\"rain\","
                + "\"temperature\":79.06,\"apparentTemperature\":79.06,"
                + "\"dewPoint\":73.56,\"humidity\":0.83,\"windSpeed\":14.62,"
                + "\"windBearing\":156,\"cloudCover\":0.7,\"pressure\":1012.69,"
                + "\"ozone\":269.65},\"flags\":{\"sources\":[\"gfs\",\"cmc\",\"fnmoc\","
                + "\"madis\"],\"madis-stations\":[\"DNMN\",\"EEKA\",\"ETEB\",\"LIVR\","
                + "\"LTCM\",\"MMML\",\"MUGM\",\"PHNG\",\"RJAF\",\"RJEB\",\"RJOF\","
                + "\"RJTI\",\"SBCZ\",\"VTSK\",\"WLRNV\",\"WLSNV\"],\"units\":\"us\"}}";
    }
}

package pl.lodz.p.ics.weatherapp.api.darksky.services;

import org.junit.Before;
import org.junit.Test;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.darksky.controllers.DarkSkyApiController;
import pl.lodz.p.ics.weatherapp.api.darksky.models.Currently;
import pl.lodz.p.ics.weatherapp.api.darksky.models.DarkSky;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TestDarkSkyApiService {


    private Double temp = 285.5;
    private Double pressure = 1012.2;
    private Double humidity = 0.95;
    private Double windSpeed = 32.4;
    private Integer windDeg = 67;
    private Double clouds = 0.81;

    private DarkSkyApiService service;
    private DarkSkyApiController controller;

    @Before
    public void setUp() {
        Currently currently = mock(Currently.class);
        when(currently.getTemperature()).thenReturn(temp);
        when(currently.getPressure()).thenReturn(pressure);
        when(currently.getHumidity()).thenReturn(humidity);
        when(currently.getWindSpeed()).thenReturn(windSpeed);
        when(currently.getWindBearing()).thenReturn(windDeg);
        when(currently.getCloudCover()).thenReturn(clouds);

        DarkSky ds = mock(DarkSky.class);
        when(ds.getCurrently()).thenReturn(currently);

        controller = mock(DarkSkyApiController.class);
        when(controller.getDarkSky()).thenReturn(ds);

        service = new DarkSkyApiService();
        service.setController(controller);
    }

    @Test
    public void testGetDataShouldCallOpenWeatherMapApiControllerGetOpenWeatherMap() {
        // GIVEN
        Double latitude = 100.0;
        Double longitude = 120.0;
        // WHEN
        service.getData(latitude, longitude);
        // THEN
        //noinspection ResultOfMethodCallIgnored
        verify(controller, times(1)).getDarkSky();
    }

    @Test
    public void testGetDataReturnsCorrectValuesInWeatherApiDataObject() {
        // GIVEN
        Double latitude = 100.0;
        Double longitude = 120.0;
        WeatherApiData expected_data = new WeatherApiData(
                (temp - 32.0) / 1.8,
                (temp - 32.0) / 1.8,
                pressure,
                humidity,
                windSpeed * 1.609344,
                windDeg.doubleValue(),
                clouds
        );
        // WHEN
        WeatherApiData data = service.getData(latitude, longitude);
        // THEN
        assertEquals(expected_data, data);
    }
}

package pl.lodz.p.ics.weatherapp.api.openweathermap.services;

import org.junit.Before;
import org.junit.Test;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.openweathermap.controllers.OpenWeatherMapApiController;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.Clouds;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.Main;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.OpenWeatherMap;
import pl.lodz.p.ics.weatherapp.api.openweathermap.models.Wind;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TestOpenWeatherMapApiService {

    private Double tempMin = 285.5;
    private Double tempMax = 288.2;
    private Integer pressure = 1012;
    private Integer humidity = 95;
    private Double windSpeed = 32.4;
    private Integer windDeg = 67;
    private Integer clouds = 81;

    private OpenWeatherMapApiService service;
    private OpenWeatherMapApiController controller;

    @Before
    public void setUp() {
        Main mainMock = mock(Main.class);
        when(mainMock.getTempMin()).thenReturn(tempMin);
        when(mainMock.getTempMax()).thenReturn(tempMax);
        when(mainMock.getPressure()).thenReturn(pressure);
        when(mainMock.getHumidity()).thenReturn(humidity);

        Wind windMock = mock(Wind.class);
        when(windMock.getSpeed()).thenReturn(windSpeed);
        when(windMock.getDeg()).thenReturn(windDeg);

        Clouds cloudsMock = mock(Clouds.class);
        when(cloudsMock.getAll()).thenReturn(clouds);

        OpenWeatherMap owm = mock(OpenWeatherMap.class);
        when(owm.getMain()).thenReturn(mainMock);
        when(owm.getWind()).thenReturn(windMock);
        when(owm.getClouds()).thenReturn(cloudsMock);

        controller = mock(OpenWeatherMapApiController.class);
        when(controller.getOpenWeatherMap()).thenReturn(owm);

        service = new OpenWeatherMapApiService();
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
        verify(controller, times(1)).getOpenWeatherMap();
    }

    @Test
    public void testGetDataReturnsCorrectValuesInWeatherApiDataObject() {
        // GIVEN
        Double latitude = 100.0;
        Double longitude = 120.0;
        WeatherApiData expected_data = new WeatherApiData(
                tempMin - 273.15,
                tempMax - 273.15,
                pressure.doubleValue(),
                humidity.doubleValue() / 100.0,
                windSpeed,
                windDeg.doubleValue(),
                clouds.doubleValue() / 100.0
        );
        // WHEN
        WeatherApiData data = service.getData(latitude, longitude);
        // THEN
        assertEquals(expected_data, data);
    }
}

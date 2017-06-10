package pl.lodz.p.ics.weatherapp.api.services;

import org.junit.Test;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.WeatherApiInterface;
import pl.lodz.p.ics.weatherapp.api.services.WeatherApiAggregatorService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestWeatherApiAggregatorService {

    @Test
    public void testGetDataShouldReturnAverageDataValuesWhenServicesAvailable() {
        // GIVEN
        Double lat = 100.0;
        Double lon = 150.0;
        WeatherApiInterface service1 = mock(WeatherApiInterface.class);
        when(service1.getData(lat, lon)).thenReturn(new WeatherApiData(
                1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0
        ));
        WeatherApiInterface service2 = mock(WeatherApiInterface.class);
        when(service2.getData(lat, lon)).thenReturn(new WeatherApiData(
                3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0
        ));
        WeatherApiData expectedResult = new WeatherApiData(
                2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0
        );
        WeatherApiAggregatorService aggregator = new WeatherApiAggregatorService();
        aggregator.addService(service1);
        aggregator.addService(service2);
        // WHEN
        WeatherApiData result = aggregator.getData(lat, lon);
        // THEN
        assertEquals(expectedResult, result);
    }

    @Test
    public void testGetDataShouldReturnNullWhenNoServices() {
        // GIVEN
        Double lat = 100.0;
        Double lon = 150.0;
        WeatherApiAggregatorService aggregator = new WeatherApiAggregatorService();
        // WHEN
        WeatherApiData result = aggregator.getData(lat, lon);
        // THEN
        assertNull(result);
    }
}

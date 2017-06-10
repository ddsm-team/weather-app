package pl.lodz.p.ics.weatherapp.api.openweathermap.services;

import org.junit.Test;
import pl.lodz.p.ics.weatherapp.api.WeatherApiData;
import pl.lodz.p.ics.weatherapp.api.WeatherApiInterface;
import pl.lodz.p.ics.weatherapp.api.services.WeatherApiAggregatorService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestWeatherApiAggregatorService {

    @Test
    public void testGetDataShouldReturnAverageDataValues() {
        // GIVEN
        Double lat = 100.0;
        Double lon = 150.0;
        List<WeatherApiInterface> services = new ArrayList<>(2);
        WeatherApiInterface service1 = mock(WeatherApiInterface.class);
        when(service1.getData(lat, lon)).thenReturn(new WeatherApiData(
                1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0
        ));
        services.add(service1);
        WeatherApiInterface service2 = mock(WeatherApiInterface.class);
        when(service2.getData(lat, lon)).thenReturn(new WeatherApiData(
                3.0, 6.0, 9.0, 12.0, 15.0, 18.0, 21.0
        ));
        services.add(service2);
        WeatherApiData expectedResult = new WeatherApiData(
                2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0
        );
        WeatherApiAggregatorService aggregator
                = new WeatherApiAggregatorService(services);
        // WHEN
        WeatherApiData result = aggregator.getData(lat, lon);
        // THEN
        assertEquals(expectedResult, result);
    }
}

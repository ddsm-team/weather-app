package pl.lodz.p.ics.weatherapp.services;

import static org.junit.Assert.assertEquals;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import pl.lodz.p.ics.weatherapp.dao.LocationDao;
import pl.lodz.p.ics.weatherapp.dao.LocationWeatherDao;
import pl.lodz.p.ics.weatherapp.dao.WeatherDao;
import pl.lodz.p.ics.weatherapp.exceptions.LocationNotFoundException;
import pl.lodz.p.ics.weatherapp.models.Location;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;
import pl.lodz.p.ics.weatherapp.models.Weather;

@RunWith(MockitoJUnitRunner.class)
public class TestWeatherService {

    @Mock
    LocationDao locationDao;
    @Mock
    WeatherDao weatherDao;
    @Mock
    LocationWeatherDao locationWeatherDao;

    @InjectMocks
    private WeatherService weatherService;

    @Before
    public void setUp() {
        Mockito.when(locationDao.save(Mockito.any(Location.class))).thenReturn(null);
        Mockito.when(weatherDao.save(Mockito.any(Weather.class))).thenReturn(null);
        Mockito.when(locationWeatherDao.save(Mockito.any(LocationWeather.class))).thenReturn(null);
        Mockito.when(locationWeatherDao.findAllByLocationNameAndTimestampBetween(Mockito.any(String.class),
                Mockito.any(Timestamp.class), Mockito.any(Timestamp.class)))
                .thenReturn(new ArrayList<LocationWeather>());

        weatherService = new WeatherService();
    }

    @Test
    public void testGetWeatherForLocationShouldThrowException() {
        // GIVEN
        String location = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String message = null;
        // WHEN
        try {
            weatherService.getWeatherForLocation(location);
        } catch (LocationNotFoundException e) {
            message = e.getMessage();
        }
        // THEN
        assertEquals(message, "Could not find the place.");
    }

    // mock problem
    // @Test
    // public void testGetWeatherForLocationShouldReturnNonNullObject() {
    // // GIVEN
    // String location = "Łódź, Polska";
    // LocationWeather lw = null;
    // // WHEN
    // try {
    // lw = weatherService.getWeatherForLocation(location);
    // } catch (LocationNotFoundException e) {
    // e.printStackTrace();
    // }
    // // THEN
    // Assert.notNull(lw);
    // }
}

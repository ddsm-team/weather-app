package pl.lodz.p.ics.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;

public interface LocationWeatherDao extends CrudRepository<LocationWeather, Integer> { }

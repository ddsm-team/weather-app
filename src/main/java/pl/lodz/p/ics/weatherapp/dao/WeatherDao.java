package pl.lodz.p.ics.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.ics.weatherapp.models.Weather;

public interface WeatherDao extends CrudRepository<Weather, Integer> { }

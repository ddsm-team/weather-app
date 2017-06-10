package pl.lodz.p.ics.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.ics.weatherapp.models.LocationWeather;

import java.sql.Timestamp;
import java.util.List;

public interface LocationWeatherDao extends CrudRepository<LocationWeather, Integer> {

    List<LocationWeather> findAllByLocationNameAndTimestampBetween(String locationName,
                                                                   Timestamp from,
                                                                   Timestamp to);
}

package pl.lodz.p.ics.weatherapp.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.lodz.p.ics.weatherapp.models.Location;
import pl.lodz.p.ics.weatherapp.models.LocationKey;

import javax.transaction.Transactional;

public interface LocationDao extends CrudRepository<Location, LocationKey> {

    @Transactional
    @Query("SELECT 1 FROM Location l WHERE l.latitude=:latitude "
            + "AND l.longitude=:longitude")
    Location findByCoordinates(@Param("latitude") Double latitude,
                               @Param("longitude") Double longitude);
}

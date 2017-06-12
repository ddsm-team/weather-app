package pl.lodz.p.ics.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import pl.lodz.p.ics.weatherapp.models.Role;

public interface RoleDao extends CrudRepository<Role, Long> { }

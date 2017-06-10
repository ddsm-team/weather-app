package pl.lodz.p.ics.weatherapp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import pl.lodz.p.ics.weatherapp.models.User;

public interface UserDao extends CrudRepository<User, Integer> {

    User findByLogin(String login);
    
    @Transactional
    void deleteByLogin(String login);
}

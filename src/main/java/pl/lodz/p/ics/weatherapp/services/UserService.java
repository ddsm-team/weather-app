package pl.lodz.p.ics.weatherapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.lodz.p.ics.weatherapp.dao.UserDao;
import pl.lodz.p.ics.weatherapp.exceptions.BadCredentialsException;
import pl.lodz.p.ics.weatherapp.exceptions.UserAlreadyExistsException;
import pl.lodz.p.ics.weatherapp.exceptions.UserIsNotLoggedInException;
import pl.lodz.p.ics.weatherapp.models.User;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    public void register(String login, String password, String city, String country)
            throws UserAlreadyExistsException {
        User user = userDao.findByLogin(login);
        if (user != null) {
            throw new UserAlreadyExistsException("User already exists.");
        } else {
            userDao.save(new User(login, password, city, country));
        }
    }

    public User login(String login, String password) throws BadCredentialsException {
        User user = userDao.findByLogin(login);
        if (user == null) {
            throw new BadCredentialsException("Wrong credentials.");
        } else {
            if (!user.getPassword().equals(password)) {
                throw new BadCredentialsException("Wrong credentials.");
            } else {
                UsernamePasswordAuthenticationToken token
                        = new UsernamePasswordAuthenticationToken(user, user.getLogin());
                SecurityContextHolder.getContext().setAuthentication(token);
                return user;
            }
        }
    }

    public String getAddress() throws UserIsNotLoggedInException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        if (user == null) {
            throw new UserIsNotLoggedInException(
                    "To get user address, you need to log in first.");
        } else {
            return user.getCity() + ", " + user.getCountry();
        }
    }
}

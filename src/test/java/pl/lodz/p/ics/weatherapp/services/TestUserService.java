package pl.lodz.p.ics.weatherapp.services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import pl.lodz.p.ics.weatherapp.dao.UserDao;
import pl.lodz.p.ics.weatherapp.exceptions.BadCredentialsException;
import pl.lodz.p.ics.weatherapp.exceptions.UserAlreadyExistsException;
import pl.lodz.p.ics.weatherapp.exceptions.UserIsNotLoggedInException;
import pl.lodz.p.ics.weatherapp.models.User;

@RunWith(MockitoJUnitRunner.class)
public class TestUserService {

    @Mock
    UserDao userDao;

    @InjectMocks
    private UserService userService;

    private User fakeUser;
    private User testUser;

    @Before
    public void setUp() {
        fakeUser = new User("fake", "password", "city", "country");
        testUser = new User("testLogin", "testPassword", "testCity", "testCountry");

        Mockito.when(userDao.findByLogin(fakeUser.getLogin())).thenReturn(fakeUser);
        Mockito.when(userDao.findByLogin(testUser.getLogin())).thenReturn(null);
    }

    @Test
    public void testRegisterShouldCreateNewUser() {
        // GIVEN
        String response = "";
        String login = "testLogin";
        String password = "testPassword";
        String city = "testCity";
        String country = "testCountry";
        // WHEN
        try {
            userService.register(login, password, city, country);
            response = "Account created.";
        } catch (UserAlreadyExistsException e) {
            response = e.getMessage();
        }
        // THEN
        assertEquals(response, "Account created.");
    }

    @Test
    public void testRegisterShouldThrowException() {
        // GIVEN
        String response = "";
        String login = "fake";
        String password = "password";
        String city = "city";
        String country = "country";
        // WHEN
        try {
            userService.register(login, password, city, country);
            response = "Account created.";
        } catch (UserAlreadyExistsException e) {
            response = e.getMessage();
        }
        // THEN
        assertEquals(response, "User already exists.");
    }

    @Test
    public void testLoginShouldReturnUser() {
        // GIVEN
        String login = "fake";
        String password = "password";
        User user = null;
        // WHEN
        try {
            user = userService.login(login, password);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        // THEN
        assertEquals(user.getLogin(), login);
    }

    @Test
    public void testLoginShouldThrowBadCredentialsExceptionOnBadLogin() {
        // GIVEN
        String login = "dog";
        String password = "password";
        String message = null;
        // WHEN
        try {
            User user = userService.login(login, password);
        } catch (BadCredentialsException e) {
            message = e.getMessage();
        }
        // THEN
        assertEquals(message, "Wrong credentials.");
    }

    @Test
    public void testLoginShouldThrowBadCredentialsExceptionOnBadPassword() {
        // GIVEN
        String login = "fake";
        String password = "dog";
        String message = null;
        // WHEN
        try {
            User user = userService.login(login, password);
        } catch (BadCredentialsException e) {
            message = e.getMessage();
        }
        // THEN
        assertEquals(message, "Wrong credentials.");
    }

    @Test
    public void testLoginContextSettingsReturnsTrueWhenContextSaved() {
        // GIVEN
        String login = "fake";
        String password = "password";
        User user = null;
        // WHEN
        try {
            user = userService.login(login, password);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        // THEN
        assertEquals(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), user);
    }

    @Test
    public void testGetAddressReturnsTrueIfAddressWasReturned() throws BadCredentialsException {
        // GIVEN
        String login = "fake";
        String password = "password";
        User user = userService.login(login, password);
        String address = null;
        // WHEN
        try {
            address = userService.getAddress();
        } catch (UserIsNotLoggedInException e) {
            e.printStackTrace();
        }
        // THEN
        assertEquals(address, "city, country");
    }

    @Test
    public void testThrowsUserIsNotLoggedInExceptionInsteadOfAddress() {
        // GIVEN
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(null, null);
        SecurityContextHolder.getContext().setAuthentication(token);
        String address = null;
        String message = null;
        // WHEN
        try {
            address = userService.getAddress();
        } catch (UserIsNotLoggedInException e) {
            message = e.getMessage();
        }
        // THEN
        assertEquals(message, "To get user address, you need to log in first.");
    }
}

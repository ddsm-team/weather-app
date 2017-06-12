package pl.lodz.p.ics.weatherapp.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.lodz.p.ics.weatherapp.Application;
import pl.lodz.p.ics.weatherapp.models.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@FixMethodOrder(org.junit.runners.MethodSorters.NAME_ASCENDING)
public class TestUserDao {

    @Autowired
    private UserDao userDao;

    private User testUser;

    @Before
    public void setUp() {
        testUser = new User("adam", "password", "Warsaw", "Poland");
    }

    @Test
    public void atestSaveUserShouldReturnNotNullWhenUserCreated() {
        // GIVEN
        // WHEN
        userDao.save(testUser);
        // THEN
        Assert.assertNotNull(userDao.findByUsername("adam"));
    }
    
    @Test
    public void testShouldReturnNotNullWhenUserFound(){
        // GIVEN
        String login = testUser.getUsername();
        // WHEN
        User user = userDao.findByUsername(login);
        // THEN
        Assert.assertNotNull(user);
    }

    @Test
    public void ztestDeleteUserShouldReturnNullWhenNoUser() {
        // GIVEN
        String login = testUser.getUsername();
        // WHEN
        userDao.deleteByUsername(login);
        // THEN
        Assert.assertNull(userDao.findByUsername("adam"));
    }

}

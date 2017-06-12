package pl.lodz.p.ics.weatherapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.ics.weatherapp.dao.RoleDao;
import pl.lodz.p.ics.weatherapp.dao.UserDao;
import pl.lodz.p.ics.weatherapp.models.Role;
import pl.lodz.p.ics.weatherapp.models.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserManagementService {
    @Autowired
    private UserDao userRepository;

    @Autowired
    private RoleDao roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (Role role : roleRepository.findAll()) {
            roles.add(role);
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

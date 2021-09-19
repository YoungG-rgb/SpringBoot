package com.crudonspringboot.service;

import com.crudonspringboot.dao.RoleDao;
import com.crudonspringboot.dao.UserDao;
import com.crudonspringboot.models.Role;
import com.crudonspringboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserDao userDao;
    private final RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Transactional
    @Override
    public void add(User user, Long [] roles) {
        Set <Role> containerRoles = new HashSet<>();
        if (roles == null) {
            containerRoles.add(roleDao.getRoleById(2L));
        } else {
            containerRoles = Arrays.stream(roles)
                    .map(roleDao::getRoleById)
                    .collect(Collectors.toSet());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(containerRoles);
        userDao.add(user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Transactional
    @Override
    public void update(User user, Long [] roles) {
        if (roles != null) {
            user.setRoles(
                    Arrays.stream(roles)
                    .map(roleDao::getRoleById)
                    .collect(Collectors.toSet())
            );
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.update(user);
    }

    @Transactional
    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userDao.getUserByName(s);
        if (user == null){
            throw new UsernameNotFoundException("USERNAME IS NULL");
        }
        return user;
    }
}
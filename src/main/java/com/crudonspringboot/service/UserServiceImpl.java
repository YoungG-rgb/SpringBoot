package com.crudonspringboot.service;

import com.crudonspringboot.repository.RoleRepository;
import com.crudonspringboot.repository.UserRepository;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void add(User user, Long [] roles) {
        Set <Role> containerRoles = new HashSet<>();
        if (roles == null) {
            containerRoles.add(roleRepository.findById(2L).get());
        } else {
            containerRoles = Arrays.stream(roles)
                    .map(e -> roleRepository.findById(e).get())
                    .collect(Collectors.toSet());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(containerRoles);
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public void update(User user, Long [] roles) {
        user.setRoles(Arrays.stream(roles)
                .map(e -> roleRepository.findById(e).get())
                .collect(Collectors.toSet()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(s);
        if (user == null){
            throw new UsernameNotFoundException("USERNAME IS NULL");
        }
        return user;
    }
}
package com.crudonspringboot.service;


import com.crudonspringboot.models.Role;
import com.crudonspringboot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AddAdminAndRoleToDataBase {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AddAdminAndRoleToDataBase(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void doInit(){
        // roles
        Role roleAdmin = new Role("ROLE_ADMIN");
        Role roleUser = new Role("ROLE_USER");
        roleService.save(roleAdmin);
        roleService.save(roleUser);
        // Admin
        User admin = new User("Zhalaldin", "Toichubaev", (byte)20, "Zhalal", "admin");
        // User
        User user = new User("Igor", "Ptushkin",(byte)27, "Igorek","igorek123");
        // saveAll
        userService.add(admin, new Long[]{1L, 2L});
        userService.add(user, new Long[] {2L});
    }
}

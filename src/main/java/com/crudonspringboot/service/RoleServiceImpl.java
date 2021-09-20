package com.crudonspringboot.service;


import com.crudonspringboot.repository.RoleRepository;
import com.crudonspringboot.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Set<Role> getAllRoles() {
        return new HashSet<>(roleRepository.findAll());
        // git branch bootStrap
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = null;
        Optional <Role> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            role = optional.get();
        }
        return role;
    }
}

package com.crudonspringboot.repository;

import com.crudonspringboot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User getUserByLogin(String name);
}

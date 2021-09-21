package com.crudonspringboot;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CrudOnSpringBootApplicationTests {

    public static void main(String[] args) {
        String role = "ROLE_ADMIN";
        System.out.println(role.substring(5));
    }
}

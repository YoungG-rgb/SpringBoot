package com.crudonspringboot.controllers;

import com.crudonspringboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUserPage(Principal principal, Model model) {
        model.addAttribute("currentUser",userService.loadUserByUsername(principal.getName()));
        return "/user/userPage";
    }
}

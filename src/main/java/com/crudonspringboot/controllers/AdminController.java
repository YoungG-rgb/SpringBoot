package com.crudonspringboot.controllers;

import com.crudonspringboot.models.User;
import com.crudonspringboot.service.RoleService;
import com.crudonspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String index(@ModelAttribute("newUser") User user, Model model, Principal principal){
        model.addAttribute("user", userService.getAllUsers());
        model.addAttribute("currentUser", userService.loadUserByUsername(principal.getName()));
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "admin/index";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("user") User user,
                               @RequestParam(value = "selectedRoles", required = false) Long [] roles){
        userService.add(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/update")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("userForUpdate", userService.getById(id));
        return "admin/index";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("user") User user,
                               @RequestParam(value = "selectedRoles", required = false) Long [] selectedRoles,
                               @RequestParam(value = "hasRoles", required = false) Long [] hasRoles) {
        if (selectedRoles == null) {
            userService.update(user, hasRoles);
        } else {
            userService.update(user, selectedRoles);
        }
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
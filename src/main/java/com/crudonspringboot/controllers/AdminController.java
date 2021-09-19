package com.crudonspringboot.controllers;

import com.crudonspringboot.models.User;
import com.crudonspringboot.service.RoleService;
import com.crudonspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String index(Model model){
        model.addAttribute("user", userService.getAllUsers());
        return "admin/index";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id")int id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "admin/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("user") User user,
                            Model model){
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "admin/new";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("user") User user,
                               @RequestParam(value = "selectedRoles", required = false) Long [] roles){
        userService.add(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("AllRoles", roleService.getAllRoles());
        return "admin/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("user") User user,
                               @RequestParam(value = "selectedRoles", required = false) Long [] roles) {
        userService.update(user, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        userService.delete(id);
        return "redirect:/admin";
    }
}

package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUsers(Model model){
        List<User> users = userService.getAll();
        model.addAttribute("users", users);
        return "users";
    }


    @GetMapping("/add")
    public String addUser() {
        return "adduser";
    }

    @PostMapping("/add")
    public String addUserPost(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") long id,Model model){
        List<Role> roles = roleService.getallrole();
        model.addAttribute("allroles",roles);
        model.addAttribute("user",userService.getUser(id));
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateUserPost(@ModelAttribute User user,@PathVariable("id") long id){
        Role role = roleService.getRolebyId(1);
        user.addRole(role);
        userService.updateUser(user,id);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String getss(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        System.out.println(personDetails.getUser());
        model.addAttribute("user",personDetails.getUser());
        return "user";
    }

//    @GetMapping("/addRole")

}

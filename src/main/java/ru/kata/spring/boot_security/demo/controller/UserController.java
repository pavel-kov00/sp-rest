package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public UserController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getUser(Model model, Principal principal){
        User user =  userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }


//    @GetMapping("/error")
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleError(Exception ex) {
//        ModelAndView mav = new ModelAndView();
//        return mav;
//    }
}

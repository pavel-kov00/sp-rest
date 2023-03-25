package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.PersonDetails;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

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
//        model.addAttribute("userRoles", user.getRoles());
//        model.addAttribute("user",personDetails.getUser());
        return "user";
    }

//
//    @GetMapping("/ss")
//    public String getss(Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
////        PersonDetails personDetails = new PersonDetails(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
////        PersonDetails personDetails = (PersonDetails)  authentication.getPrincipal();
////        model.addAttribute("user", personDetails.getUser());
//        return "user";
//    }

    @GetMapping("/error")
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception ex) {
//        logger.error("Request: " + req.getRequestURL() + " raised " + ex);
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", ex);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        return mav;
    }
}

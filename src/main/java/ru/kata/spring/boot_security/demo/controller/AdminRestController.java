package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AdminRestController {

    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public AdminRestController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers (){
        return userService.getAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUsers (@PathVariable("id") long id){
        return userService.getUser(id);
    }

    @PostMapping("/addUser")
    public ResponseEntity<HttpStatus> create(@RequestBody User user, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new RuntimeException("error!");
        }
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/edit")
//    @PathVariable("id") long id,
    public ResponseEntity<HttpStatus> updateUserPost(@RequestBody User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) {
            throw new RuntimeException("error!");
        }
        userService.updateUser(user,user.getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return HttpStatus.OK;
    }


}

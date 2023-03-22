package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

     List<User> getAll();
     void addUser(User user);
     User getUser(long id);
     void deleteUser(long id);
     void updateUser(User user,long id);
     User getUserByName(String username);



}

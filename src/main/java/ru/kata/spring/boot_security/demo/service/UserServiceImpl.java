package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService{

    private static Logger loger = Logger.getLogger(UserServiceImpl.class.getName());

    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(PasswordEncoder bCryptPasswordEncoder, UserDao userDao, RoleService roleService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    public List<User> getAll() {
        return userDao.getall();
    }

    @Override
    @Transactional
    public void addUser(User user) {
        Set<Role> roles = user.getRoles();
        HashSet<Role> roles2 = new  HashSet<>();

        for(Role role:roles){
            roles2.add(roleService.getByName(role.getRolename()));
        }
        user.setRoles(roles2);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
    }

    @Override
    public User getUser(long id) {
        return userDao.getUser(id);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userDao.deleteUser(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, long id) {
        Set<Role> roles = user.getRoles();
        HashSet<Role> roles2 = new HashSet<>();

        for(Role role:roles){
            roles2.add(roleService.getByName(role.getRolename()));
            loger.log(Level.WARNING,role.getRole());
        }
        user.setRoles(roles2);
        user.setPassword(userDao.getUser(id).getPassword());
        userDao.updateUser(user,id);
    }

    @Override
    public User getUserByName(String username) {
        return userDao.getUserByName(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.getUserByName(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }

        for (Role r : user.getRoles()){
            loger.log(Level.WARNING,r.getRole());
        }

        return user;
    }

}
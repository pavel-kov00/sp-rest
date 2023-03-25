package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.PersonDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final UserDao userDao;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleService roleService) {
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
        List<Role> roles = user.getRoles();
        for (int i=0;i<roles.size();i++) {
            System.out.println("***************");
            System.out.println(roles.get(i).getRolename());
            System.out.println(roles.get(i).getId());
            System.out.println(roles.get(i).getRole());
            roles.set(i,roleService.getByName(roles.get(i).getRolename())); // получаю в этом месте ошибку
        }
//        Role role = roleService.getByName("ROLE_ADMIN"); // тестовый вызов ....пишет  ожидается Long
//       Provided id of the wrong type for class ru.kata.spring.boot_security.demo.model.Role. Expected: class java.lang.Long, got class java.lang.String
//
//
//        System.out.println(role.getRole());
        user.setRoles(roles);
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
//        user.setRoles(userDao.getUserByName(username).getRoles());

        if (user == null){
            throw new UsernameNotFoundException("User not found!");
        }
        // возвращаем только данные для Security
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(), mapRoleToAuthorities(user.getRoles()));
//        return new PersonDetails(user);
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList());
    }

}

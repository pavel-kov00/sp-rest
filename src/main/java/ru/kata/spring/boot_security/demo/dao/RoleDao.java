package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;

public interface RoleDao {

    List<Role> getallrole();

    Role getRolebyId(long id);
    Role getByName(String rolename);

}

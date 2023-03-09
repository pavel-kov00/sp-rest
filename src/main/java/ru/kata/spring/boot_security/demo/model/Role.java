package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;        // для spring security, запись как - ROLE_ADMIN

    @Column(name="role_name")
    private String rolename;   // для красивого отображения - Admin

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_role"
    , joinColumns = @JoinColumn(name="role_id")
    , inverseJoinColumns = @JoinColumn(name="user_id"))
    private List<User> users;

    public Role() {
    }

    public Role(String role, String rolename) {
        this.role = role;
        this.rolename = rolename;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}

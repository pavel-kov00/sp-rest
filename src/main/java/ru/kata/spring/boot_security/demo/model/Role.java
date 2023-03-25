package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="role")
    private String role;        // для spring security, запись как - ROLE_ADMIN

    @Column(name="role_name")
    private String rolename;   // для красивого отображения - Admin
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(name="user_role"
//    , joinColumns = @JoinColumn(name="role_id")
//    , inverseJoinColumns = @JoinColumn(name="user_id"))
    @ManyToMany(
//            fetch = FetchType.LAZY,
            mappedBy = "roles"
    )
    private List<User> users;

    public Role() {
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Role(String role, String rolename) {
        this.role = role;
        this.rolename = rolename;
    }

    public long getId() {
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

    @Override
    public String getAuthority() {
        return getRole();
    }
}

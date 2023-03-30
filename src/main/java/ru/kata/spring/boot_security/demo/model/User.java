package ru.kata.spring.boot_security.demo.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String password;
    private String lastname;
    private int age;
    private String email;
//    ********************************************************
//@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name="user_id")
//    ********************************************************

    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE}
            ,fetch = FetchType.LAZY
    )
    @JoinTable(name="user_role"
            , joinColumns = @JoinColumn(name="user_id")
            , inverseJoinColumns = @JoinColumn(name="role_id"))
    @OnDelete(action = OnDeleteAction.NO_ACTION) // чтобы данные в связанной таблице Role не удалялись
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String name, String password, String lastname, int age, String email) {
        this.name = name;
        this.password = password;
        this.lastname = lastname;
        this.age = age;
        this.email = email;
    }

    public User(String name, String password, String lastname, int age) {
        this.name = name;
        this.password = password;
        this.lastname = lastname;
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role){
        this.roles.add(role);
    }

    public void setId(long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", roles='" + roles.toString()  + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

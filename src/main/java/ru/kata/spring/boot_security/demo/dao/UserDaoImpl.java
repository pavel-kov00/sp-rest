package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<User> getall() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void addUser(User user) {
        entityManager.merge(user);
    }


    @Override
    public User getUser(long id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public void deleteUser(long id) {
        entityManager.remove(getUser(id));
    }

    @Override
    public void updateUser(User us, long id) {

        User user = getUser(id);
//        user.addRole((Role) us.getRoles().stream().filter(role -> role.getRolename().equals("Admin")));
//        user.addRole((Role) us.getRoles().stream().filter(role -> role.getRolename().equals("User")));
        user.setName(us.getName());
        user.setLastname(us.getLastname());
        user.setAge(us.getAge());
        user.setEmail(us.getEmail());
//        user.setRoles(us.getRoles());
        user.addRole(new Role("ROLE_ADMIN","Admin"));
        entityManager.persist(user);
    }
//    @Query("select u from User u join fetch u.roles where u.name =:username")
    @Override
    public User getUserByName(String username){
        System.out.println(username);
        try {
            TypedQuery<User> user = (TypedQuery<User>) entityManager.createQuery(
                            "from User u where u.name = :pname").
                    setParameter("pname", username);
            return user.getSingleResult();
        } catch (NoResultException e){
            return null;
        }
    }
}

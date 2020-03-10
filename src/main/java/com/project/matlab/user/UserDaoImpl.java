package com.project.matlab.user;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {


    public static final String FIND_ALL = "select u from User u";
    public static final String FIND_BY_USERNAME = "select u from User u where u.username= :username";
    private static UserDaoImpl INSTANCE;
    private static EntityManager entityManager;

    public static UserDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDaoImpl();
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("matlab");
            entityManager = factory.createEntityManager();
        }
        return INSTANCE;
    }


    public User save(User user) {
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public List<User> findAll() {
        Query query = entityManager.createQuery(FIND_ALL);
        List<User> users = new ArrayList(query.getResultList());
        return users;
    }


    @Override
    public User findById(long id) {
        return entityManager.getReference(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        Query query = entityManager.createQuery(FIND_BY_USERNAME, User.class);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }


}

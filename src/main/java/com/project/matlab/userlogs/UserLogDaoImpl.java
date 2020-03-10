package com.project.matlab.userlogs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UserLogDaoImpl implements UserLogDao {


    public static final String FIND_BY_USER_ID = "select ul from UserLog ul where ul.userId= :userId";
    private static UserLogDaoImpl INSTANCE;
    private static EntityManager entityManager;

    public static UserLogDaoImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserLogDaoImpl();
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("matlab");
            entityManager = factory.createEntityManager();
        }
        return INSTANCE;
    }


    public UserLog save(UserLog userLog) {
        entityManager.getTransaction().begin();
        entityManager.persist(userLog);
        entityManager.flush();
        entityManager.getTransaction().commit();
        return userLog;
    }


    @Override
    public List<UserLog> findByUserId(long userId) {
        Query query = entityManager.createQuery(FIND_BY_USER_ID, UserLog[].class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}

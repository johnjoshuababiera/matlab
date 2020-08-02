package com.project.fruit114net.userlogs;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserLogDaoImpl implements UserLogDao {


    public static final String FIND_ALL = "select ul from UserLog ul";
    public static final String FIND_BY_USER_ID = "select ul from UserLog ul where ul.userId= :userId";
    public static final String FILTER_BY_DATE_RANGE = "select ul from UserLog ul where ul.timestamp between :fromDate and :toDate";
    public static final String FIND_BY_USER_ID_AND_FILTER_BY_DATE_RANGE = "select ul from UserLog ul where ul.userId= :userId and ul.timestamp between :fromDate and :toDate";
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
        Query query = entityManager.createQuery(FIND_BY_USER_ID);
        query.setParameter("userId", userId);
        return new ArrayList<>(query.getResultList());
    }

    @Override
    public List<UserLog> findByUserIdAndFilterByDateRange(long userId, Date fromDate, Date toDate) {
        Query query = entityManager.createQuery(FIND_BY_USER_ID_AND_FILTER_BY_DATE_RANGE);
        query.setParameter("userId", userId);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        return new ArrayList<>(query.getResultList());
    }


    @Override
    public List<UserLog> findAll() {
        Query query = entityManager.createQuery(FIND_ALL);
        List<UserLog> userLogs = new ArrayList(query.getResultList());
        return userLogs;
    }

    @Override
    public List<UserLog> filterByDateRange(Date fromDate, Date toDate) {
        Query query = entityManager.createQuery(FILTER_BY_DATE_RANGE);
        query.setParameter("fromDate", fromDate);
        query.setParameter("toDate", toDate);
        return new ArrayList<>(query.getResultList());
    }
}

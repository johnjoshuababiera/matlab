package com.project.fruit114net.userlogs;

import java.util.Date;
import java.util.List;

public interface UserLogDao {
    UserLog save(UserLog userLog);
    List<UserLog> findByUserId(long userId);
    List<UserLog> findByUserIdAndFilterByDateRange(long userId, Date fromDate, Date toDate1);
    List<UserLog> findAll();
    List<UserLog> filterByDateRange(Date fromDate, Date toDate);
}

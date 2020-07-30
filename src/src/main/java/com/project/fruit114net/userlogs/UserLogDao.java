package com.project.fruit114net.userlogs;

import java.util.List;

public interface UserLogDao {
    UserLog save(UserLog userLog);
    List<UserLog> findByUserId(long userId);
    List<UserLog> findAll();
}

package com.project.matlab.userlogs;

import java.util.List;

public interface UserLogDao {
    UserLog save(UserLog userLog);
    List<UserLog> findByUserId(long userId);
}
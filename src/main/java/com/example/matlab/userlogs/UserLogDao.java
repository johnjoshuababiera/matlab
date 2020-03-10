package com.example.matlab.userlogs;

import com.example.matlab.user.User;

import java.util.List;

public interface UserLogDao {
    UserLog save(UserLog userLog);
    List<UserLog> findByUserId(long userId);
}

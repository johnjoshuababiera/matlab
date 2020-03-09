package com.example.matlab.user;

import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(long id);
    User findByUsername(String username);
    List<User> findAll();
}

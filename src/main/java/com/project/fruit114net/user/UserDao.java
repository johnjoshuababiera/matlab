package com.project.fruit114net.user;

import java.util.List;

public interface UserDao {
    User save(User user);
    User findById(long id);
    User findByUsername(String username);
    List<User> findAll();
    void delete(User user);
    void initializeAdmin();
}

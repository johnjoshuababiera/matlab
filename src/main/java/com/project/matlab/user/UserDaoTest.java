package com.project.matlab.user;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.util.List;

public class UserDaoTest {
    public static void main(String[] args) {
        UserDao userDao = UserDaoImpl.getInstance();
        List<User> users = userDao.findAll();

        User user = new User();
        user.setUsername("username");
        user.setFirstName("firstname");
        user.setLastName("lastname");
        user.setMiddleName("middlename");
        user.setPassword(BCrypt.withDefaults().hashToString(12, "password".toCharArray()));

        user = userDao.save(user);
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getId());

        user = userDao.findById(user.getId());
        System.out.println(user);
        user = userDao.findByUsername("username");
        System.out.println(user);

        BCrypt.Result result = BCrypt.verifyer().verify("password".toCharArray(), user.getPassword());
        System.out.println(result);


    }
}

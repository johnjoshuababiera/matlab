package com.project.fruit114net.util;

import com.project.fruit114net.user.User;

public class UserUtil {

    private static User user;
    public static User getUser() {
        return user;
    }

    public static boolean isAdmin(){
        return user.isAdmin();
    }
    public static void setUser(User user) {
        UserUtil.user = user;
    }
}

package com.project.matlab.util;

import com.project.matlab.user.User;

public class UserUtil {

    private static User user;
    public static User getUser() {
        return user;
    }
    public static void setUser(User user) {
        UserUtil.user = user;
    }
}

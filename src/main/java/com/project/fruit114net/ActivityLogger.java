package com.project.fruit114net;

import com.project.fruit114net.user.User;
import com.project.fruit114net.userlogs.UserLog;
import com.project.fruit114net.userlogs.UserLogDao;
import com.project.fruit114net.userlogs.UserLogDaoImpl;
import com.project.fruit114net.util.UserUtil;
import javafx.fxml.Initializable;

import java.util.Date;
import java.util.List;

public abstract class ActivityLogger implements Initializable {

    UserLogDao userLogDao = UserLogDaoImpl.getInstance();

    public UserLog createLog(String activity) {
        User user = UserUtil.getUser();
        UserLog userLog = new UserLog(activity, new Date(), user.getId(), user.getUsername());
        return userLogDao.save(userLog);
    }

    public List<UserLog> findByUser() {
        User user = UserUtil.getUser();
        return userLogDao.findByUserId(user.getId());
    }

    public List<UserLog> findAll() {
        return userLogDao.findAll();
    }
}

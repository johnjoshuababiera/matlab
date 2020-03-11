package com.project.matlab;

import com.project.matlab.user.User;
import com.project.matlab.userlogs.UserLog;
import com.project.matlab.userlogs.UserLogDao;
import com.project.matlab.userlogs.UserLogDaoImpl;
import com.project.matlab.util.UserUtil;
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

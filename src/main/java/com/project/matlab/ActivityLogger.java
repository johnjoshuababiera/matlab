package com.project.matlab;

import com.project.matlab.userlogs.UserLog;
import com.project.matlab.userlogs.UserLogDao;
import com.project.matlab.userlogs.UserLogDaoImpl;
import javafx.fxml.Initializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public abstract class ActivityLogger implements Initializable{

    UserLogDao userLogDao = UserLogDaoImpl.getInstance();

    public UserLog createLog(String activity, long userId){
        UserLog userLog = new UserLog(activity,new Date(), userId);
        return userLogDao.save(userLog);
    }

    public List<UserLog> findLogs(long userId){
        List<UserLog> userLogs = new ArrayList<>();
        userLogs.addAll(Arrays.asList(new UserLog("test",new Date(), 1l), new UserLog("test 2", new Date(), 2l)));
        return userLogs;
//        return userLogDao.findByUserId(userId);
    }
}

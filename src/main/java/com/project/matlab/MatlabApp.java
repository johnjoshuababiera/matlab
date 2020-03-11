package com.project.matlab;

import com.project.matlab.user.UserDao;
import com.project.matlab.user.UserDaoImpl;
import com.project.matlab.util.Fxml;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MatlabApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserDao userDao = UserDaoImpl.getInstance();
        userDao.initializeAdmin();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Fxml.LOG_IN));
        AnchorPane anchorPane = loader.load();
        anchorPane.requestFocus();
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
    }
}

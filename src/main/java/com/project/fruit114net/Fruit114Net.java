package com.project.fruit114net;

import com.project.fruit114net.user.UserDao;
import com.project.fruit114net.user.UserDaoImpl;
import com.project.fruit114net.util.Fxml;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Fruit114Net extends Application {

    public static void main(String[] args) {
        Application.launch(Fruit114Net.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        MatlabEngine matlabEngine = EngineUtil.getMatlabEngineInstance();
        UserDao userDao = UserDaoImpl.getInstance();
        userDao.initializeAdmin();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Fxml.LOG_IN));
        AnchorPane anchorPane = loader.load();
        anchorPane.requestFocus();
        primaryStage.setTitle("Automated Fruit Classification using Deep Convolutional Neural Network");
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
    }
}

package com.example.matlab;

import com.example.matlab.user.User;
import com.example.matlab.user.UserDao;
import com.example.matlab.user.UserDaoImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MatlabApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {


        TextField textField = new TextField();

        HBox hbox = new HBox(textField);

        Scene scene = new Scene(hbox, 200, 100);

        UserDao userDao = UserDaoImpl.getInstance();
        User user = userDao.findByUsername("username");

        textField.setText(user.getUsername());
//
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(getClass().getResource(Fxml.MAIN));

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Close");
            alert.setHeaderText("Close program?");
            alert.showAndWait()
                    .ifPresent(r -> {
                        if(r.equals(ButtonType.OK)){
                            System.exit(0);
                        }else{
                            event.consume();
                        }
                    });
        });
    }
}

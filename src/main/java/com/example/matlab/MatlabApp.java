package com.example.matlab;

import com.example.matlab.user.User;
import com.example.matlab.user.UserDao;
import com.example.matlab.user.UserDaoImpl;
import com.example.matlab.util.Fxml;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

public class MatlabApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(Fxml.MAIN));
        AnchorPane anchorPane = loader.load();
        anchorPane.requestFocus();
        primaryStage.setScene(new Scene(anchorPane));
        primaryStage.show();
        anchorPane.requestFocus();
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

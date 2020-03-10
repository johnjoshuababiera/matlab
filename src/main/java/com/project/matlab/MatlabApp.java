package com.project.matlab;

import com.project.matlab.util.Fxml;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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

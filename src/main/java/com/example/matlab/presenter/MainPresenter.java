package com.example.matlab.presenter;

import com.example.matlab.ActivityLogger;
import com.example.matlab.util.Fxml;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPresenter extends ActivityLogger {

    @FXML private BorderPane borderPane;
    @FXML private JFXButton btnAccountMgmnt;
    @FXML private JFXButton btnUploadWorkspace;
    @FXML private JFXButton btnSystemTesting;
    @FXML private JFXButton btnUserLogs;
    @FXML private JFXButton btnSignOut;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSystemTesting.setOnAction(event -> loadPane(Fxml.SYSTEM_TEST));
        btnUploadWorkspace.setOnAction(event -> loadPane(Fxml.UPLOAD_WORK_SPACE));
        btnUserLogs.setOnAction(event -> loadPane(Fxml.USER_LOGS));
        btnAccountMgmnt.setOnAction(event -> loadPane(Fxml.ACCOUNT_MANAGEMENT));
        btnSignOut.setOnAction(event -> signout(event));
    }


    private void loadPane(String name) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(name));
            AnchorPane anchorPane = loader.load();
            borderPane.setCenter(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void signout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Signout");
        alert.setHeaderText("Signout?");
        alert.showAndWait()
                .ifPresent(r -> {
                    if (r.equals(ButtonType.OK)) {
                        System.exit(0);
                    } else {
                        event.consume();
                    }
                });
    }



}

package com.project.matlab.presenter;

import com.jfoenix.controls.JFXButton;
import com.project.matlab.ActivityLogger;
import com.project.matlab.user.User;
import com.project.matlab.util.Fxml;
import com.project.matlab.util.UserUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    @FXML private JFXButton btnUpdateProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnUpdateProfile.setVisible(!UserUtil.isAdmin());
        btnUploadWorkspace.setVisible(UserUtil.isAdmin());
        btnUserLogs.setVisible(UserUtil.isAdmin());
        btnAccountMgmnt.setVisible(UserUtil.isAdmin());
        btnUpdateProfile.setManaged(!UserUtil.isAdmin());
        btnUploadWorkspace.setManaged(UserUtil.isAdmin());
        btnUserLogs.setManaged(UserUtil.isAdmin());
        btnAccountMgmnt.setManaged(UserUtil.isAdmin());

        btnUpdateProfile.setOnAction(event -> showUserForm(UserUtil.getUser(), event));
        btnSystemTesting.setOnAction(event -> loadPane(Fxml.SYSTEM_TEST));
        btnUploadWorkspace.setOnAction(event -> loadPane(Fxml.UPLOAD_WORK_SPACE));
        btnUserLogs.setOnAction(event -> loadPane(Fxml.USER_LOGS));
        btnAccountMgmnt.setOnAction(event -> loadPane(Fxml.ACCOUNT_MANAGEMENT));
        btnSignOut.setOnAction(event -> signout(event));
    }

    private void showUserForm(User user, ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Fxml.USER_FORM));
            AnchorPane anchorPane = loader.load();
            UserFormPresenter presenter = loader.getController();
            presenter.setUser(user);
            stage.setScene(new Scene(anchorPane));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

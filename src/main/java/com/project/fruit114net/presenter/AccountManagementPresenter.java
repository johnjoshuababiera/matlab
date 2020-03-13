package com.project.fruit114net.presenter;

import com.jfoenix.controls.JFXButton;
import com.project.fruit114net.ActivityLogger;
import com.project.fruit114net.user.User;
import com.project.fruit114net.user.UserDao;
import com.project.fruit114net.user.UserDaoImpl;
import com.project.fruit114net.util.Fxml;
import com.project.fruit114net.util.PresenterUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountManagementPresenter extends ActivityLogger {

    @FXML
    private TableView tblUsers;
    @FXML
    private JFXButton btnCreate;
    @FXML
    private JFXButton btnUpdate;
    @FXML
    private JFXButton btnDelete;
    private List<User> users = new ArrayList<>();
    private UserDao userDao;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userDao = UserDaoImpl.getInstance();
        initTable();
        initTableContents();
        initButtons();
    }

    private void initButtons() {
        btnCreate.setOnAction(event -> showUserForm(new User(), event));
        btnUpdate.setOnAction(event -> {
            User user = (User) tblUsers.getSelectionModel().getSelectedItem();
            showUserForm(user, event);
        });
        btnDelete.setOnAction(event -> {
            User user = (User) tblUsers.getSelectionModel().getSelectedItem();
            String username= user.getUsername();
            userDao.delete(user);
            createLog(String.format("Deleted %s user acount.", username));
            PresenterUtils.INSTANCE.displayInformation(String.format("Successfully deleted %s", username));
            initTableContents();
        });
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
            stage.setOnHidden(closeEvent -> initTableContents());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTable() {
        TableColumn<User, String> usernameColumn = getUsernameColumn();
        TableColumn<User, String> firstNameColumn = getFirstNameColumn();
        TableColumn<User, String> lastNameColumn = getLastNameColumn();
        TableColumn<User, String> middleNameColumn = getMiddleNameColumn();
        tblUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblUsers.getColumns().addAll(usernameColumn, firstNameColumn, lastNameColumn, middleNameColumn);
    }

    private void initTableContents() {
        tblUsers.getItems().clear();
        users = userDao.findAll();
        tblUsers.getItems().addAll(users);
    }

    private TableColumn<User, String> getUsernameColumn() {
        TableColumn<User, String> column = new TableColumn<>("Username");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        return column;
    }

    private TableColumn<User, String> getLastNameColumn() {
        TableColumn<User, String> column = new TableColumn<>("Last Name");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        return column;
    }


    private TableColumn<User, String> getFirstNameColumn() {
        TableColumn<User, String> column = new TableColumn<>("Middle Name");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMiddleName()));
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        return column;
    }


    private TableColumn<User, String> getMiddleNameColumn() {
        TableColumn<User, String> column = new TableColumn<>("Middle Name");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMiddleName()));
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        return column;
    }
}

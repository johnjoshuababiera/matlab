package com.project.matlab.presenter;

import com.jfoenix.controls.JFXButton;
import com.project.matlab.ActivityLogger;
import com.project.matlab.user.User;
import com.project.matlab.user.UserDao;
import com.project.matlab.user.UserDaoImpl;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AccountManagementPresenter extends ActivityLogger {

    @FXML private TableView tblUsers;
    @FXML private JFXButton btnCreate;
    @FXML private JFXButton btnUpdate;
    @FXML private JFXButton btnDelete;
    private List<User> users = new ArrayList<>();
    private UserDao userDao;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userDao = UserDaoImpl.getInstance();
        initTable();
        initButtons();
    }

    private void initButtons() {
        btnCreate.setOnAction(event -> {});
        btnUpdate.setOnAction(event -> {
            User user = (User) tblUsers.getSelectionModel().getSelectedItem();
            System.out.println(user);
        });
        btnDelete.setOnAction(event -> {});
    }

    private void initTable() {
        users = userDao.findAll();
        TableColumn<User, String> usernameColumn = getUsernameColumn();
        TableColumn<User, String> firstNameColumn = getFirstNameColumn();
        TableColumn<User, String> lastNameColumn = getLastNameColumn();
        TableColumn<User, String> middleNameColumn = getMiddleNameColumn();
        tblUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tblUsers.getColumns().addAll(usernameColumn, firstNameColumn, lastNameColumn, middleNameColumn);
        tblUsers.getItems().addAll(users);
    }

    private TableColumn<User, String> getUsernameColumn(){
        TableColumn<User, String> column = new TableColumn<>("Username");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        return column;
    }

    private TableColumn<User, String> getLastNameColumn(){
        TableColumn<User, String> column = new TableColumn<>("Last Name");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        return column;
    }


    private TableColumn<User, String> getFirstNameColumn(){
        TableColumn<User, String> column = new TableColumn<>("Middle Name");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMiddleName()));
        return column;
    }


    private TableColumn<User, String> getMiddleNameColumn(){
        TableColumn<User, String> column = new TableColumn<>("Middle Name");
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getMiddleName()));
        return column;
    }
}

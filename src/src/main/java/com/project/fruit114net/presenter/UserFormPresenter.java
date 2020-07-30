package com.project.fruit114net.presenter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.fruit114net.ActivityLogger;
import com.project.fruit114net.user.User;
import com.project.fruit114net.user.UserDao;
import com.project.fruit114net.user.UserDaoImpl;
import com.project.fruit114net.util.PresenterUtils;
import com.project.fruit114net.util.UserUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormPresenter extends ActivityLogger implements Initializable {

    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtMiddleName;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnSave;
    @FXML
    private JFXButton btnCancel;
    private UserDao userDao;
    private User user;
    private boolean onEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userDao = UserDaoImpl.getInstance();
        btnSave.setOnAction(event -> {

            if(txtUsername.getText() == null ||txtUsername.getText().isEmpty() || txtPassword.getText() == null || txtPassword.getText().isEmpty()){
                PresenterUtils.INSTANCE.displayError("Username or Password should not be empty.");
                return;
            }

            String oldUserName = onEdit ? user.getUsername() : null;
            user.setFirstName(txtFirstName.getText());
            user.setMiddleName(txtMiddleName.getText());
            user.setLastName(txtLastName.getText());
            user.setUsername(txtUsername.getText());
            user.setAdmin(onEdit ? user.isAdmin() : false);
            if (txtPassword.getText() != null && !txtPassword.getText().isEmpty()) {
                user.setPassword(BCrypt.withDefaults().hashToString(12, txtPassword.getText().toCharArray()));
            }
            User duplicate = userDao.findByUsername(user.getUsername());
            if((duplicate!=null && !onEdit) || (onEdit && user.getId() != duplicate.getId())){
                PresenterUtils.INSTANCE.displayError("Username already exists.");
                return;
            }
            User savedUser = userDao.save(user);
            if (UserUtil.getUser() != null && savedUser.getId() == UserUtil.getUser().getId()) {
                UserUtil.setUser(savedUser);
                logActivity(oldUserName);
            } else {
                createLog(String.format("%s account created", savedUser.getUsername()));
            }
            PresenterUtils.INSTANCE.displayInformation("User account created.");
            closeStage(event);
        });
        btnCancel.setOnAction(event -> closeStage(event));
    }

    private void logActivity(String oldUserName) {
        String activity = onEdit ? "Updated": "Created";
        String username = onEdit ? oldUserName : user.getUsername();
        createLog(String.format("%s %s user account.", activity, username));
    }

    private void closeStage(ActionEvent event) {
        Control control = (Control) event.getSource();
        Stage stage = (Stage) control.getScene().getWindow();
        stage.close();
    }

    public void setUser(User user) {
        this.user = user;
        this.onEdit = user.getId() != null;
        if (user.getId() != null) {
            txtUsername.setText(user.getUsername());
            txtFirstName.setText(user.getFirstName());
            txtMiddleName.setText(user.getMiddleName());
            txtLastName.setText(user.getLastName());
            txtPassword.setText("");
        }
    }


}

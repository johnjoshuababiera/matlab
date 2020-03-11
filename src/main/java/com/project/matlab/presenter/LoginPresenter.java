package com.project.matlab.presenter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.matlab.user.User;
import com.project.matlab.user.UserDao;
import com.project.matlab.user.UserDaoImpl;
import com.project.matlab.util.Fxml;
import com.project.matlab.util.PresenterUtils;
import com.project.matlab.util.UserUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPresenter implements Initializable {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;

    UserDao userDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userDao = UserDaoImpl.getInstance();
        btnLogin.setOnAction(event -> {
            if (txtUsername.getText() == null || txtUsername.getText().isEmpty() ||
                    txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
                PresenterUtils.INSTANCE.displayError("Please fill up username/password field.");
                return;
            }

            User user = userDao.findByUsername(txtUsername.getText());
            if(user==null){
                PresenterUtils.INSTANCE.displayError("Username not found.");
                return;
            }

            if(!BCrypt.verifyer().verify(txtPassword.getText().toCharArray(), user.getPassword()).verified){
                PresenterUtils.INSTANCE.displayError("Invalid Password.");
                return;
            }
            try{
                UserUtil.setUser(user);
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Fxml.MAIN));
                AnchorPane anchorPane = loader.load();
                stage.setScene(new Scene(anchorPane));
                stage.setOnShown(event1 -> txtPassword.getScene().getWindow().hide());
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }


}

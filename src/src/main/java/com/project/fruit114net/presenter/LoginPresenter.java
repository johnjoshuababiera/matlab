package com.project.fruit114net.presenter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.fruit114net.user.User;
import com.project.fruit114net.user.UserDao;
import com.project.fruit114net.user.UserDaoImpl;
import com.project.fruit114net.util.Fxml;
import com.project.fruit114net.util.PresenterUtils;
import com.project.fruit114net.util.UserUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPresenter implements Initializable {

    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private JFXButton btnSignup;

    UserDao userDao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userDao = UserDaoImpl.getInstance();
        btnSignup.setOnAction(event -> {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Fxml.USER_FORM));
                AnchorPane anchorPane = loader.load();
                UserFormPresenter presenter = loader.getController();
                presenter.setUser(new User());
                stage.setScene(new Scene(anchorPane));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node) (event.getSource())).getScene().getWindow());
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnLogin.setOnAction(event -> {
            if (txtUsername.getText() == null || txtUsername.getText().isEmpty() ||
                    txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
                PresenterUtils.INSTANCE.displayError("Please fill up username/password field.");
                return;
            }

            User user = userDao.findByUsername(txtUsername.getText());
            if(user==null || !BCrypt.verifyer().verify(txtPassword.getText().toCharArray(), user.getPassword()).verified){
                PresenterUtils.INSTANCE.displayError("Username and password did not match.");
                return;
            }

            /*if(!BCrypt.verifyer().verify(txtPassword.getText().toCharArray(), user.getPassword()).verified){
                PresenterUtils.INSTANCE.displayError("Password did not match.");
                return;
            }*/
            try{
                UserUtil.setUser(user);
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(Fxml.MAIN));
                AnchorPane anchorPane = loader.load();
                stage.setTitle("Automated Fruit Classification using Deep Convolutional Neural Network");
                stage.setScene(new Scene(anchorPane));
                stage.setOnShown(event1 -> txtPassword.getScene().getWindow().hide());
                stage.show();
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }


    @FXML
    void onEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            btnLogin.fire();
        }
    }



}

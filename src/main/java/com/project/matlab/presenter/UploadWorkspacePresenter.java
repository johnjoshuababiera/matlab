package com.project.matlab.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.matlab.ActivityLogger;
import javafx.fxml.FXML;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class UploadWorkspacePresenter extends ActivityLogger {


    @FXML private JFXTextField txtAlexNet;
    @FXML private JFXTextField txtGoogleNet;
    @FXML private JFXTextField txtRestNet;
    @FXML private JFXTextField txtFruitNet;
    @FXML private JFXButton btnAlexChoose;
    @FXML private JFXButton btnAlexUpload;
    @FXML private JFXButton btnGoogleChoose;
    @FXML private JFXButton btnGoogleUpload;
    @FXML private JFXButton btnResnetChoose;
    @FXML private JFXButton btnResnetUpload;
    @FXML private JFXButton btnFruitChoose;
    @FXML private JFXButton btnFruitUpload;

    private File alexFile;
    private File googleFile;
    private File resnetFile;
    private File fruitFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAlexChoose.setOnAction(event -> chooseFile(alexFile));
        btnGoogleChoose.setOnAction(event -> chooseFile(googleFile));
        btnResnetChoose.setOnAction(event -> chooseFile(resnetFile));
        btnFruitChoose.setOnAction(event -> chooseFile(fruitFile));
        btnAlexUpload.setOnAction(event -> {});
        btnGoogleUpload.setOnAction(event -> {});
        btnAlexUpload.setOnAction(event -> {});
        btnResnetUpload.setOnAction(event -> {});
        btnFruitUpload.setOnAction(event -> {});

    }

    private void chooseFile(File file){

    }
}

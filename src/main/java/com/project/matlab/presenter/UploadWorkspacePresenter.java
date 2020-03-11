package com.project.matlab.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.matlab.ActivityLogger;
import com.project.matlab.util.PresenterUtils;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
        btnAlexChoose.setOnAction(event -> chooseFile(0, txtAlexNet));
        btnGoogleChoose.setOnAction(event -> chooseFile(1, txtGoogleNet));
        btnResnetChoose.setOnAction(event -> chooseFile(2, txtRestNet));
        btnFruitChoose.setOnAction(event -> chooseFile(3, txtFruitNet));
        btnAlexUpload.setOnAction(event -> {
            uploadFile(alexFile);
            createLog("Upload AlexNet Workspace");
        });
        btnGoogleUpload.setOnAction(event ->  {
            uploadFile(googleFile);
            createLog("Upload GoogleNet Workspace");
        });
        btnResnetUpload.setOnAction(event ->  {
            uploadFile(resnetFile);
            createLog("Upload ResNet-50 Workspace");
        });
        btnFruitUpload.setOnAction(event ->  {
            uploadFile(fruitFile);
            createLog("Upload Fruit114Net Workspace");
        });
    }

    private void chooseFile(int fileCode, JFXTextField txtField){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Matlab Files", "*.mat"));
        File uploadedFile = fileChooser.showOpenDialog(null);
        if (uploadedFile != null) {
            txtField.setText(uploadedFile.toURI().toString());
            switch (fileCode){
                case 0:
                    alexFile=uploadedFile;
                    break;
                case 1:
                    googleFile=uploadedFile;
                    break;
                case 2:
                    resnetFile=uploadedFile;
                    break;
                case 3:
                    fruitFile=uploadedFile;
                    break;
            }
        }
    }

    private void uploadFile(File file){
        try {
            if(file==null){
                PresenterUtils.INSTANCE.displayError("Please select file before uploading.");
                return;
            }
            File newFile = new File(String.format("files/%s", "",file.getName()));
            if(newFile.exists()){
                newFile.delete();
            }
            FileUtils.copyFile(file, newFile);
        } catch (IOException e) {
            e.printStackTrace();
            PresenterUtils.INSTANCE.displayError("Failed to upload file.");
        }
    }
}

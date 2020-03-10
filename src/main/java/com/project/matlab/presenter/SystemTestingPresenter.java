package com.project.matlab.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.matlab.ActivityLogger;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SystemTestingPresenter extends ActivityLogger {

    @FXML private TextArea txtAreaFacts;
    @FXML private ImageView imgView;
    @FXML private JFXButton btnChooseFile;
    @FXML private JFXButton btnClassify;
    @FXML private JFXButton btnClear;
    @FXML private JFXTextField txtFile;
    @FXML private JFXTextField txtAlexPredicted;
    @FXML private JFXTextField txtGooglePredicted;
    @FXML private JFXTextField txtResNetPredicted;
    @FXML private JFXTextField txtFruit114Predicted;
    @FXML private JFXTextField txtAlexAccuracy;
    @FXML private JFXTextField txtGoogleAccuracy;
    @FXML private JFXTextField txtResNetAccuracy;
    @FXML private JFXTextField txtFruit114Accuracy;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnChooseFile.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.jpeg", "*.png"));
            File imageFile = fileChooser.showOpenDialog(null);
            if (imageFile != null) {
                Image image = new Image(imageFile.toURI().toString());
                imgView.setImage(image);
                txtFile.setText(imageFile.getAbsolutePath());
            }
        });
        btnClassify.setOnAction(event -> {
            txtAlexPredicted.setText("Apple");
            txtGooglePredicted.setText("Apple");
            txtResNetPredicted.setText("Apple");
            txtFruit114Predicted.setText("Apple");
            txtAlexAccuracy.setText("50%");
            txtGoogleAccuracy.setText("50%");
            txtResNetAccuracy.setText("50%");
            txtFruit114Accuracy.setText("50%");
        });
        btnClear.setOnAction(event -> {
            imgView.setImage(null);
            txtFile.setText(null);
            txtAlexPredicted.setText(null);
            txtGooglePredicted.setText(null);
            txtResNetPredicted.setText(null);
            txtFruit114Predicted.setText(null);
            txtAlexAccuracy.setText(null);
            txtGoogleAccuracy.setText(null);
            txtResNetAccuracy.setText(null);
            txtFruit114Accuracy.setText(null);
        });

    }
}

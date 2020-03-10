package com.example.matlab.presenter;

import com.example.matlab.ActivityLogger;
import com.example.matlab.userlogs.UserLog;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class UserLogsPresenter extends ActivityLogger{

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");

    @FXML
    private TableView tblUserLogs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<UserLog, String> timeStampColumn = getTimeStampColumn();
        TableColumn<UserLog, String> activityColumn = getActivityColumn();
        tblUserLogs.getColumns().addAll(timeStampColumn, activityColumn);
        List<UserLog> userLogList = findLogs(1l);
        tblUserLogs.getItems().addAll(userLogList);

    }

    private TableColumn<UserLog, String> getActivityColumn(){
        TableColumn<UserLog, String> column = new TableColumn<>("Activity");
        column.setPrefWidth(650);
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getActivity()));
        return column;
    }

    private TableColumn<UserLog, String> getTimeStampColumn(){
        TableColumn<UserLog, String> column = new TableColumn<>("Time Stamp");
        column.setPrefWidth(250);
        column.setCellValueFactory(param -> new SimpleStringProperty(simpleDateFormat.format(param.getValue().getTimestamp())));
        return column;
    }
}

package com.project.fruit114net.presenter;

import com.project.fruit114net.ActivityLogger;
import com.project.fruit114net.userlogs.UserLog;
import com.project.fruit114net.util.UserUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class UserLogsPresenter extends ActivityLogger {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");

    @FXML
    private TableView tblUserLogs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn<UserLog, String> usernameColumn = getUsernameColumn();
        TableColumn<UserLog, String> timeStampColumn = getTimeStampColumn();
        TableColumn<UserLog, String> activityColumn = getActivityColumn();
        tblUserLogs.getColumns().addAll(usernameColumn, timeStampColumn, activityColumn);

        List<UserLog> userLogList = UserUtil.isAdmin() ? findAll() : findByUser();
        tblUserLogs.getItems().addAll(userLogList);

    }

    private TableColumn<UserLog, String> getActivityColumn() {
        TableColumn<UserLog, String> column = new TableColumn<>("Activity");
        column.setPrefWidth(650);
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getActivity()));
        column.setSortable(false);
        return column;
    }

    private TableColumn<UserLog, String> getUsernameColumn() {
        TableColumn<UserLog, String> column = new TableColumn<>("Username");
        column.setPrefWidth(200);
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        column.setSortable(false);
        return column;
    }

    private TableColumn<UserLog, String> getTimeStampColumn() {
        TableColumn<UserLog, String> column = new TableColumn<>("Time Stamp");
        column.setPrefWidth(250);
        column.setSortable(false);
        column.setCellValueFactory(param -> new SimpleStringProperty(simpleDateFormat.format(param.getValue().getTimestamp())));
        return column;
    }
}

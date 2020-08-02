package com.project.fruit114net.presenter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.project.fruit114net.ActivityLogger;
import com.project.fruit114net.userlogs.UserLog;
import com.project.fruit114net.util.DateUtil;
import com.project.fruit114net.util.PresenterUtils;
import com.project.fruit114net.util.PrintUtil;
import com.project.fruit114net.util.UserUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class UserLogsPresenter extends ActivityLogger {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm aa");

    @FXML
    private JFXDatePicker datePickerFrom;

    @FXML
    private JFXDatePicker datePickerTo;

    @FXML
    private JFXButton btnFilter;
    
    @FXML
    private JFXButton btnPrint;

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

        btnFilter.setOnAction(event -> filterList());
        btnPrint.setOnAction(event -> printTable());

    }

    private void printTable() {
        try {
            Map<String, String> map = new HashMap<>();
            if(datePickerFrom.getValue()!=null && datePickerTo.getValue()!=null) {
                map.put("fromDate", DateUtil.format(DateUtil.localDateToDate(datePickerFrom.getValue()), "MMM dd, yyyy hh:mm aa"));
                map.put("toDate", DateUtil.format(DateUtil.localDateToDate(datePickerTo.getValue()), "MMM dd, yyyy hh:mm aa"));
            }
            PrintUtil.printJasperCollection(tblUserLogs.getItems(), new File("./reports/user_logs.jasper"), map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterList() {
        LocalDate localDateFrom = datePickerFrom.getValue();
        LocalDate localDateTo = datePickerTo.getValue();
        if(datePickerFrom.getValue()==null && datePickerTo.getValue()==null){
            List<UserLog> userLogList = UserUtil.isAdmin() ? findAll() : findByUser();
            tblUserLogs.getItems().clear();
            tblUserLogs.getItems().addAll(userLogList);
            return;
        }

        if (datePickerFrom.getValue() == null || datePickerTo.getValue() == null || localDateFrom.isAfter(localDateTo)) {
            PresenterUtils.INSTANCE.displayError("Invalid date range.");
            return;
        }
        Date dateFrom = DateUtil.toStartOfDay(DateUtil.localDateToDate(localDateFrom));
        Date dateTo = DateUtil.toEndOfDay(DateUtil.localDateToDate(localDateTo));
        List<UserLog> userLogList = UserUtil.isAdmin() ? findByUserFilterByDateRange(dateFrom, dateTo) : filterByDateRange(dateFrom, dateTo);
        tblUserLogs.getItems().clear();
        tblUserLogs.getItems().addAll(userLogList);
    }

    private TableColumn<UserLog, String> getActivityColumn() {
        TableColumn<UserLog, String> column = new TableColumn<>("Activity");
        column.setPrefWidth(575);
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getActivity()));
        column.setSortable(false);
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        return column;
    }

    private TableColumn<UserLog, String> getUsernameColumn() {
        TableColumn<UserLog, String> column = new TableColumn<>("Username");
        column.setPrefWidth(130);
        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getUsername()));
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        column.setSortable(false);
        return column;
    }

    private TableColumn<UserLog, String> getTimeStampColumn() {
        TableColumn<UserLog, String> column = new TableColumn<>("Time Stamp");
        column.setPrefWidth(220);
        column.setSortable(false);
        column.setCellValueFactory(param -> new SimpleStringProperty(simpleDateFormat.format(param.getValue().getTimestamp())));
        column.setStyle("-fx-background-color: rgba(69,69,69,0.26);");
        return column;
    }
}

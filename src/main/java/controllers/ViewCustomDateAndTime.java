package controllers;

import Services.ReminderServices;
import Services.TaskServices;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Reminder;
import model.Task;
import utils.Validator;

import java.time.LocalDate;
import java.time.LocalTime;

public class ViewCustomDateAndTime {
    private Task task;

    @FXML
    private JFXComboBox yearCombox;

    @FXML
    private JFXComboBox monthCombox;

    @FXML
    private JFXComboBox dayCombox;

    @FXML
    private Spinner<Integer> hourSpinnerCT;

    @FXML
    private Spinner<Integer> minutesSpinnerCT;

    @FXML
    private Button btnDoneCDT;

    @FXML
    private Button btnCancelCDT;

    @FXML
    private Label lblMessage;

    public void initialize() {
        for (int year = 2024; year <= 2100; year++) {
            yearCombox.getItems().add(year);
        }

        String[] months = {"January", "February", "March", "April", "May", "Jun", "July", "August", "September", "October", "November", "December"};
        monthCombox.getItems().addAll(months);


        monthCombox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            dayCombox.getItems().clear();
            int daysInMonth = 31;
            for (int day = 1; day <= daysInMonth; day++) {
                dayCombox.getItems().add(day);
            }
        });

        hourSpinnerCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 1));
        minutesSpinnerCT.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 1));

        // Crear método para manejar la lógica de los botones
        setUpButtonHandlers();
    }

    public void initData(String nameTask) {
        TaskServices taskServices = new TaskServices();
        task = taskServices.getTaskByName(nameTask);
    }

    private void setUpButtonHandlers() {
        btnDoneCDT.setOnAction(event -> handleDoneButton());
        btnCancelCDT.setOnAction(event -> handleCancelButton());
    }

    private void handleDoneButton() {

        if (yearCombox.getValue() != null && monthCombox.getValue() != null && dayCombox.getValue() != null ) {
            createReminder();

        } else {
            lblMessage.setText("Please fill in all fields");
        }

    }

    private void handleCancelButton() {
        Scene newCurrentScene = btnCancelCDT.getScene();
        Stage stage = (Stage) newCurrentScene.getWindow();
        stage.close();

        System.out.println("Recordatorio personalizado cancelado");
    }

    private void createReminder() {
        ReminderServices reminderServices = new ReminderServices();

        int year = (int) yearCombox.getValue();
        String month = (String) monthCombox.getValue();
        int day = (int) dayCombox.getValue();
        int hour = hourSpinnerCT.getValue();
        int minute = minutesSpinnerCT.getValue();

        LocalDate dateNotice = reminderServices.convertToLocalDate(year, month, day);
        LocalTime timeNotice = LocalTime.of(hour, minute);
        int IDTask = task.getID();

        Reminder reminder = new Reminder(dateNotice, timeNotice, IDTask);
        
        boolean result = reminderServices.createReminder(reminder);
        
        if (result) {
            Scene newCurrentScene = btnDoneCDT.getScene();
            Stage stage = (Stage) newCurrentScene.getWindow();
            stage.close();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("Reminder Created");
            alert.show();
        } else {
            lblMessage.setText("Error creating the reminder");
        }
    }
}

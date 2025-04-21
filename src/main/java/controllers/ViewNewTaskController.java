package controllers;

import Services.CategoryServices;
import Services.TaskServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Category;
import model.Task;
import utils.Paths;
import utils.SceneHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ViewNewTaskController {
  private Task newTask;

  @FXML
  private Label lblMessageNewTask;

  @FXML
  private Button btnAddNewTask;

  @FXML
  private Button btnCancelAddTask;

  @FXML
  private Button btnRemCustom;

  @FXML
  private TextField txtNewTaskName;

  @FXML
  private DatePicker datePickExpirationDate;

  @FXML
  private Spinner<Integer> spinnerHours;

  @FXML
  private Spinner<Integer> spinnerMinutes;

  @FXML
  private ComboBox<Category> cBoxCategory;

  @FXML
  private void initialize() {
    spinnerHours.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
    spinnerMinutes.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

    CategoryServices categoryServices = new CategoryServices();
    List<Category> categories = categoryServices.getCategories();
    cBoxCategory.getItems().addAll(categories);
  }

  @FXML
  private void handleBtnAddNewTask() {
    if(newTask == null) {
      if (!txtNewTaskName.getText().isBlank() && datePickExpirationDate != null && cBoxCategory != null) {
        if (datePickExpirationDate.getValue().isAfter(LocalDate.now())) {
          TaskServices taskServices = new TaskServices();

          String name = txtNewTaskName.getText();
          String status = "pending";
          LocalDate expirationDate = datePickExpirationDate.getValue();
          LocalTime expirationTime = LocalTime.of(spinnerHours.getValue(), spinnerMinutes.getValue());
          int IDCategory = cBoxCategory.getValue().getIDCategory();

          newTask = new Task(name, status, expirationDate, IDCategory, expirationTime);

          boolean result = taskServices.createTask(newTask);

          if (result) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Aggregate task");
            alert.show();

            Scene currentScene = btnAddNewTask.getScene();
            SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, currentScene);
          }
        } else {
          lblMessageNewTask.setText("The expiration date must not be less than the current expiration date.");
        }
      } else {
        lblMessageNewTask.setText("Please fill in all fields (Task Name, Expiry Date, Expiry Time,  and a Category)");
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText("Aggregate task");
      alert.show();

      Scene currentScene = btnAddNewTask.getScene();
      SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, currentScene);
    }

  }

  @FXML
  private void handleBtnCancel() {

    if (newTask != null) {
      TaskServices taskServices = new TaskServices();
      taskServices.deleteTask(newTask);
    }

    Scene currentScene = btnCancelAddTask.getScene();
    SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, currentScene);
  }

  private void createTask() {
    TaskServices taskServices = new TaskServices();

    String name = txtNewTaskName.getText();
    String status = "pending";
    LocalDate expirationDate = datePickExpirationDate.getValue();
    LocalTime expirationTime = LocalTime.of(spinnerHours.getValue(), spinnerMinutes.getValue());
    int IDCategory = cBoxCategory.getValue().getIDCategory();

    newTask = new Task(name, status, expirationDate, IDCategory, expirationTime);

    boolean result = taskServices.createTask(newTask);
    if (result) {
      try {
        FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(Paths.VIEW_CUSTOM_DATEANDTIME));
        Parent root = loader.load();
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);

        ViewCustomDateAndTime controller = loader.getController();
        controller.initData(newTask.getName());

        stage.show();

      } catch (IOException e) {
        e.printStackTrace();
      }


    } else {
      lblMessageNewTask.setText("The task could not be added");
    }
  }

  private
  @FXML
  void openCustomDay() {

    if (!txtNewTaskName.getText().isBlank() && datePickExpirationDate != null && cBoxCategory != null && spinnerMinutes.getValue() != null) {
      if (datePickExpirationDate.getValue().isAfter(LocalDate.now())) {
        createTask();
      } else {
        lblMessageNewTask.setText("The expiration date must not be less than the current expiration date.");
      }
    } else {
      lblMessageNewTask.setText("Please fill in all fields (Task Name, Expiry Date, Expiry Time,  and a Category) before to custom the reminder");
    }
  }
}



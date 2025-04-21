package controllers;

import Services.CategoryServices;
import Services.TaskServices;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.Category;
import model.Task;
import utils.Paths;
import utils.SceneHelper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ViewUpdateTaskController {
  private Task task;

  @FXML
  private Label lblMessageNewTask;
  
  @FXML
  private Button btnUpdateTask;

  @FXML
  private Button btnCancelUpdateTask;

  @FXML
  private TextField txtTaskName;

  @FXML
  private DatePicker datePickExpirationDate;

  @FXML
  private ComboBox<Category> cBoxCategory;

  @FXML
  private Spinner<Integer> spinnerHours;

  @FXML
  private Spinner<Integer> spinnerMinutes;

  void init(int IDTask) {
    TaskServices taskServices = new TaskServices();
    this.task = taskServices.getTask(IDTask);

    CategoryServices categoryServices = new CategoryServices();
    List<Category> categories = categoryServices.getCategories();
    cBoxCategory.getItems().addAll(categories);

    if (task != null) {
      String name = task.getName();
      int IDCategory = task.getIDCategory();
      LocalTime expirationTime = task.getExpirationTime();
      LocalDate  expirationDate = task.getExpirationDate();

      txtTaskName.setText(name);
      txtTaskName.positionCaret(txtTaskName.getText().length());

      datePickExpirationDate.setValue(expirationDate);

      SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, expirationTime.getHour());
      spinnerHours.setValueFactory(hourFactory);

      SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, expirationTime.getMinute());
      spinnerMinutes.setValueFactory(minuteFactory);

      for (Category category : categories) {
        if (category.getIDCategory() == IDCategory) {
          cBoxCategory.getSelectionModel().select(category);
          break;
        }
      }
    }
  }

  @FXML
  private void handleBtnCancelUpdateTask() {
    Scene currentScene = btnCancelUpdateTask.getScene();
    SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, currentScene);
  }

  @FXML
  private void handleBtnUpdateTask() {
    if(!txtTaskName.getText().isBlank() && datePickExpirationDate != null && cBoxCategory != null && spinnerMinutes.getValue() != null) {
      if (datePickExpirationDate.getValue().isAfter(LocalDate.now())) {
        updateTask();
      } else {
        lblMessageNewTask.setText("The expiration date must not be less than the current expiration date.");
      }
    } else {
      lblMessageNewTask.setText("Please fill in all fields (Task Name, Expiry Date, Expiry Time, and a Category)");
    }
  }

  private void updateTask() {
    TaskServices taskServices = new TaskServices();

    int IDTask = task.getID();

    String name = txtTaskName.getText();
    String status = task.getStatus();
    LocalDate expirationDate = datePickExpirationDate.getValue();
    LocalTime expirationTime = LocalTime.of(spinnerHours.getValue(), spinnerMinutes.getValue());
    int IDCategory = cBoxCategory.getValue().getIDCategory();

    Task updatedTask = new Task(name, status, expirationDate, IDCategory, expirationTime);

    updatedTask.setID(IDTask);

    boolean result = taskServices.updateTask(updatedTask);

    if (result) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText("Update Completed");
      alert.show();

      Scene currentScene = btnUpdateTask.getScene();
      SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, currentScene);
    } else {
      lblMessageNewTask.setText("Please update a field or cancel if you no longer want to update anything.");
    }
  }
}

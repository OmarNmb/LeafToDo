package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.io.File;

import Services.CategoryServices;
import Services.TaskServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Task;
import utils.SceneHelper;
import utils.SQLConnection.SQLConnection;
import utils.*;

public class ViewCard {
  private int IDTask;
  private String name;
  private String expirationDate;
  private String Category;
  private String status;
  private String notification = "off";

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private ImageView notificationImage;

  @FXML
  private Button checkButton;

  @FXML
  private Label dateLabel;

  @FXML
  private Label nametask;

  @FXML
  private ImageView checkImage;

  @FXML
  private Button notificationButton;

  @FXML
  private HBox viewCard;

  @FXML
  private Button deleteButton;

  @FXML
  private Button btnUpdateTask;

  @FXML
  void initialize() {
    assert checkButton != null : "fx:id=\"checkButton\" was not injected: check your FXML file 'ViewCard.fxml'.";
    assert nametask != null : "fx:id=\"nametask\" was not injected: check your FXML file 'ViewCard.fxml'.";
    assert notificationButton != null
        : "fx:id=\"notificationButton\" was not injected: check your FXML file 'ViewCard.fxml'.";
    assert viewCard != null : "fx:id=\"viewCard\" was not injected: check your FXML file 'ViewCard.fxml'.";
    assert deleteButton != null : "fx:id=\"deleteButton\" was not injected: check your FXML file 'ViewCard.fxml'.";
    if (notification.equals("on")) {
      File file = new File("src/main/resources/images/alert_active.png");
      Image image = new Image(file.toURI().toString());
      notificationImage.setImage(image);
    } else {
      File file = new File("src/main/resources/images/alert_inactive.png");
      Image image = new Image(file.toURI().toString());
      notificationImage.setImage(image);
    }

  }

  @FXML
  void deleteTask(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this task?", ButtonType.YES,
        ButtonType.NO);
    alert.setTitle("Delete Task");
    alert.setHeaderText(null);
    alert.showAndWait();

    if (alert.getResult() == ButtonType.YES) {
      TaskServices taskServices = new TaskServices();

      Task task = taskServices.getTaskByName(name);

      if (task != null) {
        taskServices.deleteTask(task);
        SceneHelper.changeScene(utils.Paths.VIEW_MAIN_INTERFACE, deleteButton);
      }
    }
  }

  @FXML
  void handleBtnUpdateTask(ActionEvent e) {
    int idTask = this.IDTask;

    Scene currentScene = btnUpdateTask.getScene();
    FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.VIEW_UPDATE_TASK));
    try {
      Parent root = loader.load();

      ViewUpdateTaskController controller = loader.getController();
      controller.init(idTask);

      currentScene.setRoot(root);

      Stage stage = (Stage) currentScene.getWindow();
      stage.sizeToScene();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @FXML
  void checkButtonAction(ActionEvent event) {
    if (status.equals("pending")) {
      status = "completed";
    } else {
      status = "pending";
    }
    String query = "UPDATE Task SET Status = ? WHERE IDTask = ?;";
    try (Connection conn = SQLConnection.getConnection()) {
      PreparedStatement preparedStatement = conn.prepareStatement(query);
      preparedStatement.setString(1, status);
      preparedStatement.setInt(2, IDTask);
      preparedStatement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    update();
    SceneHelper.changeScene(utils.Paths.VIEW_MAIN_INTERFACE, deleteButton);

  }

  public HBox getModel() {
    return viewCard;
  }

  public void insertData(String name, String expirationDate, String Category, String status) {
    this.name = name;
    this.expirationDate = expirationDate;
    this.Category = Category;
    this.status = status;
    update();
  }

  public void update() {
    nametask.setText(name);
    // Category es un color en hexadecimal
    nametask.setStyle("-fx-background-color: " + Category + ";" + // radios
        "-fx-background-radius: 36;");
    dateLabel.setText(expirationDate);
    // rojo si la fecha caduco, amarillo si es hoy, verde si es en el futuro
    if (LocalDate.parse(expirationDate).isBefore(LocalDate.now())) {
      dateLabel.setStyle("-fx-background-color: #FF6767;");
    } else if (LocalDate.parse(expirationDate).isEqual(LocalDate.now())) {
      dateLabel.setStyle("-fx-background-color: #FFB168;");
    } else {
      dateLabel.setStyle("-fx-background-color: #4CC1F4;");
    }
    // si el status es pending poner la imagen
    // "src\main\resources\images\taskLater.png" en checkImage
    if (status.equals("pending")) {
      File file;
      if (LocalDate.parse(expirationDate).isBefore(LocalDate.now())) {
        file = new File("src/main/resources/images/taskLater.png");
        Image image = new Image(file.toURI().toString());
        checkImage.setImage(image);
      } else {
        file = new File("src/main/resources/images/taskPending.png");
      }
      Image image = new Image(file.toURI().toString());
      checkImage.setImage(image);
      this.status = "pending";
    } else {
      File file = new File("src/main/resources/images/check_withoutbackground.png");
      Image image = new Image(file.toURI().toString());
      checkImage.setImage(image);
      dateLabel.setStyle("-fx-background-color: #98FB98;");
      this.status = "completed";
    }

    // suabiizar el borde del label
    dateLabel.setStyle(dateLabel.getStyle() + "-fx-border-color: #000000; -fx-border-radius: 36;" +
        "-fx-border-width: 1px; -fx-background-radius: 36;");
  }

  public void setIDTask(int IDTask) {
    this.IDTask = IDTask;
  }

  @FXML
    void notificationClick(ActionEvent event) {
        if (notification.equals("off")) {
            File file = new File("src/main/resources/images/alert_active.png");
            Image image = new Image(file.toURI().toString());
            notificationImage.setImage(image);
            notification = "on";
        } else {
            File file = new File("src/main/resources/images/alert_inactive.png");
            Image image = new Image(file.toURI().toString());
            notificationImage.setImage(image);
            notification = "off";
        }

    }
}

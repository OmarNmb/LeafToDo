package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import utils.Paths;
import utils.SceneHelper;
import utils.SQLConnection.SQLConnection;

import model.Task;
import model.User;
import Services.SessionServices;

public class ViewMainInterface {

  @FXML
  private VBox ScrollVBox;

  @FXML
  private Button logoutButton;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private Button userButton;

  @FXML
  private Button btnAddTask;

  // Inicializar
  @FXML
  void initialize() throws SQLException, JsonMappingException, JsonProcessingException {
    assert ScrollVBox != null
      : "fx:id=\"ScrollVBox\" was not injected: check your FXML file 'ViewMainInterface.fxml'.";
    assert logoutButton != null
      : "fx:id=\"logoutButton\" was not injected: check your FXML file 'ViewMainInterface.fxml'.";
    assert scrollPane != null
      : "fx:id=\"scrollPane\" was not injected: check your FXML file 'ViewMainInterface.fxml'.";
    assert userButton != null
      : "fx:id=\"userButton\" was not injected: check your FXML file 'ViewMainInterface.fxml'.";
    // Read database
    try (Connection conn = SQLConnection.getConnection()) {
      // leer modelo Task}
      // Como fechar, poner hoy con modulo de fecha
      // get user id from logincontroller
      User user = SessionServices.getCurrentUser();
      System.out.println(user.getIDUser());
      // get tasks from user
      // DECLARE @Tasks NVARCHAR(MAX);
      // DECLARE @User INT = 2;
      // EXEC GetTasksByUser @User, @Tasks OUTPUT;
      // SELECT @Tasks;
      String query = "DECLARE @Tasks NVARCHAR(MAX); DECLARE @User INT = ?; EXEC GetTasksByUser @User, @Tasks OUTPUT; SELECT @Tasks;";
      PreparedStatement stmt = conn.prepareStatement(query);
      stmt.setInt(1, user.getIDUser());

      boolean hasResults = stmt.execute();
      if (hasResults) {
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
          String tasks = rs.getString(1);
          /**
           * [{"IDTask":2,"Name":"Ir de
           * compras","Status":"Pendiente","ExpirationDate":"2024-04-20","IDUser":2,"IDCategory":1,
           * "ExpirationTime":"21:30:00"},{"IDTask":3,"Name":"Hacer
           * ejercicio","Status":"pendiente","ExpirationDate":
           * "2024-04-17","IDUser":2,"IDCategory":1,"ExpirationTime":"14:30:00"},{"IDTask":4,"Name":"Estudiar
           * para el examen","Status":
           * "pendiente","ExpirationDate":"2024-04-28","IDUser":2,"IDCategory":1,"ExpirationTime":"20:00:00"},{"IDTask":5,"Name":"Go
           * to the gym",
           * "Status":"pending","ExpirationDate":"2024-04-20","IDUser":2,"IDCategory":1,"ExpirationTime":"14:30:00"}]
           */
          if (tasks != null) {
            @SuppressWarnings("unchecked")
            Map<String, Object>[] taskMapArray = new ObjectMapper().readValue(tasks, Map[].class);
            for (Map<String, Object> taskMap : taskMapArray) {
              Task task = new Task((String) taskMap.get("Name"),
                (String) taskMap.get("Status"),
                LocalDate.parse((String) taskMap.get("ExpirationDate")),
                (int) taskMap.get("IDCategory"),
                LocalTime.parse((String) taskMap.get("ExpirationTime")));
              task.setID((int) taskMap.get("IDTask"));
              HBox card;
              card = task.getModel();
              ScrollVBox.getChildren().add(card);
            }
          }

        }
      }

      // finalizar la consulta
      conn.close();
    } catch (SQLException e) {
      System.out.println("Error al establecer la conexion: " + e.getMessage());
    }
  }

  @FXML
  void logoutButtonAction(ActionEvent event) {
    // cerrar sesion y volver a la pantalla de login
    SessionServices.logout();
    // Cargar ViewLogin
    Scene currScene = logoutButton.getScene();
    SceneHelper.changeScene(Paths.VIEW_LOGIN, currScene);
  }

  @FXML
  void handleBtnAddTaskAction(ActionEvent e) {
    Scene currentScene = btnAddTask.getScene();
    SceneHelper.changeScene(Paths.VIEW_ADD_NEW_TASK, currentScene);
  }

  @FXML
  void handleUserButton(ActionEvent e) {
    Scene currentScene = userButton.getScene();
    SceneHelper.changeScene(Paths.VIEW_MY_PROFILE, currentScene);
  }


}
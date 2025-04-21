package Services;

import model.Task;
import model.User;
import utils.SQLConnection.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class TaskServices {
  public boolean createTask(Task task) {
    boolean result = false;

    String name = task.getName();
    String status = task.getStatus();
    LocalDate expirationDate = task.getExpirationDate();
    int IDUser = task.getIDUser();
    int IDCategory = task.getIDCategory();
    LocalTime expirationTime = task.getExpirationTime();

    String query = "{CALL InsertTask (?, ?, ?, ?, ?, ?)}";

    try (Connection conn = SQLConnection.getConnection()) {
    CallableStatement statement = conn.prepareCall(query);

    statement.setString(1, name);
    statement.setString(2, status);
    statement.setDate(3, java.sql.Date.valueOf(expirationDate));
    statement.setInt(4, IDUser);
    statement.setInt(5, IDCategory);
    statement.setTime(6, java.sql.Time.valueOf(expirationTime));

    result = statement.executeUpdate() > 0;

    } catch (SQLException e) {
      System.out.println("Error adding task");
      e.printStackTrace();
    }

    return result;
  }

  public boolean updateTask(Task task) {
    boolean result = false;

    int IDTask = task.getID();
    String name = task.getName();
    LocalDate expirationDate = task.getExpirationDate();
    int IDCategory = task.getIDCategory();
    LocalTime expirationTime = task.getExpirationTime();

    String query ="{CALL UpdateTask (?, ?, ?, ?, ?)}";

    try (Connection conn = SQLConnection.getConnection()) {

      CallableStatement statement = conn.prepareCall(query);

      statement.setInt(1, IDTask);
      statement.setString(2, name);
      statement.setDate(3, java.sql.Date.valueOf(expirationDate));
      statement.setInt(4, IDCategory);
      statement.setTime(5, java.sql.Time.valueOf(expirationTime));

      result = statement.executeUpdate() > 0;

    } catch (SQLException e) {
      System.out.println("Error updating the task");
      e.printStackTrace();
    }

    return result;
  }

  public Task getTask(int IDTask) {
    Task task = null;
    String query = "SELECT IDTask, Name, Status, ExpirationDate, IDCategory, ExpirationTime FROM Task WHERE IDTask = ?;";

    try (Connection conn = SQLConnection.getConnection()){

      PreparedStatement statement = conn.prepareStatement(query);
      statement.setInt(1, IDTask);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        IDTask = resultSet.getInt("IDTask");
        String name = resultSet.getString("Name");
        String status = resultSet.getString("Status");
        int IDCategory = resultSet.getInt("IDCategory");
        LocalDate expirationDate = resultSet.getDate("ExpirationDate").toLocalDate();
        LocalTime expirationTime = resultSet.getTime("ExpirationTime").toLocalTime();

        task = new Task(name, status, expirationDate, IDCategory, expirationTime);
      }

    } catch (SQLException e) {
      System.out.println("Error updating the task");
      e.printStackTrace();
    }
    assert task != null;
    task.setID(IDTask);
    return task;
  }

  public Task getTaskByName(String nameTask) {
    Task task = null;
    String query = "SELECT IDTask, Name, Status, ExpirationDate, IDCategory, ExpirationTime FROM Task WHERE Name = ?;";

    try (Connection conn = SQLConnection.getConnection()){

      PreparedStatement statement = conn.prepareStatement(query);
      statement.setString(1, nameTask);

      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        int IDTask = resultSet.getInt("IDTask");
        String name = resultSet.getString("Name");
        String status = resultSet.getString("Status");
        int IDCategory = resultSet.getInt("IDCategory");
        LocalDate expirationDate = resultSet.getDate("ExpirationDate").toLocalDate();
        LocalTime expirationTime = resultSet.getTime("ExpirationTime").toLocalTime();

        task = new Task(name, status, expirationDate, IDCategory, expirationTime);
        task.setID(IDTask);
      }

    } catch (SQLException e) {
      System.out.println("Error updating the task");
      e.printStackTrace();
    }

    return task;
  }

  public void deleteTask(Task task) {
    String taskName = task.getName();
    LocalDate expirationDate = task.getExpirationDate();
    int IDCategory = task.getIDCategory();

    String query = "{CALL DeleteTaskAndReminders (?, ?, ?)}";

    try (Connection conn = SQLConnection.getConnection()){
      CallableStatement statement = conn.prepareCall(query);
      statement.setString(1, taskName);
      statement.setDate(2, java.sql.Date.valueOf(expirationDate));
      statement.setInt(3, IDCategory);

      statement.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error deleting the task");
      e.printStackTrace();
    }
  }
}

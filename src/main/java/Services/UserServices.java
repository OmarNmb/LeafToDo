package Services;

import model.User;
import utils.EncryptionUtils;
import utils.SQLConnection.SQLConnection;

import java.sql.*;

public class UserServices {
  public boolean createUser(String email, String userName, String password) {
    boolean result = false;
    String hashPassword = EncryptionUtils.encryptPassword(password);
    String query = "INSERT INTO UserAccount (Email, UserName, Password) VALUES (?, ?, ?);";

    try (Connection conn = SQLConnection.getConnection()) {
      try (PreparedStatement statement = conn.prepareCall(query)){
        statement.setString(1, email);
        statement.setString(2, userName);
        statement.setString(3, hashPassword);

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
          result = true;
          System.out.println("Cuenta creada");
        } else {
          System.out.println("no se creo la cuenta");
        }

      }
    } catch (SQLException ex) {
      System.out.println("Error creating user.");
      ex.printStackTrace();
    }

    return result;
  }

  public String getPasswordHashFromDB(int IDUser) {
    String query = "SELECT Password FROM UserAccount WHERE IDUser = ?;";
    try (Connection conn = SQLConnection.getConnection()) {
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setInt(1, IDUser);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        return resultSet.getString("Password");
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return null;
  }

  public boolean validateLogin(String potentialPassword, String userName) {
    boolean result = false;

    String query = "SELECT IDUser, Email, UserName, Password FROM UserAccount WHERE UserName = ?;";

    try (Connection conn = SQLConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {
      statement.setString(1, userName);
      ResultSet queryResult = statement.executeQuery();

      if (queryResult.next()) {
        String hashPasswordStored = queryResult.getString("Password");
        if (EncryptionUtils.validatePassword(potentialPassword, hashPasswordStored)) {
          User currentUser = new User(queryResult.getInt("IDUser"), queryResult.getString("Email"),
            queryResult.getString("UserName"), hashPasswordStored);
          SessionServices.setCurrentUser(currentUser);
          result = true;
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  public boolean updateUsername(int IDUser, String newUsername) {
    boolean result = false;
    String query = "UPDATE UserAccount SET UserName = ? WHERE IDUser = ?;";

      try (Connection conn = SQLConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

        statement.setString(1, newUsername);
        statement.setInt(2, IDUser);

        result = statement.executeUpdate() > 0;

      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }

    return result;
  }

  public boolean updatePassword(int IDUser, String newPassword) {
    boolean result = false;
    String hashNewPassword = EncryptionUtils.encryptPassword(newPassword);

    String query = "UPDATE UserAccount SET Password = ? WHERE IDUser = ?;";

    try (Connection conn = SQLConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

      statement.setString(1, hashNewPassword);
      statement.setInt(2, IDUser);

      result = statement.executeUpdate() > 0;

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  public boolean updateEmail(int IDUser, String newEmail) {
    boolean result = false;
    String query = "UPDATE UserAccount SET Email = ? WHERE IDUser = ?;";

    try (Connection conn = SQLConnection.getConnection(); PreparedStatement statement = conn.prepareStatement(query)) {

      statement.setString(1, newEmail);
      statement.setInt(2, IDUser);

      result = statement.executeUpdate() > 0;

    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return result;
  }

  public boolean verifyPassword(int IDUser, String potentialPassword) {
    boolean result = false;
    String hashPasswordStored = getPasswordHashFromDB(IDUser);

    if (hashPasswordStored != null && EncryptionUtils.validatePassword(potentialPassword, hashPasswordStored)) {
      result = true;
    }

    return result;
  }
}

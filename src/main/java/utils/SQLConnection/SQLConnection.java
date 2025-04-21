package utils.SQLConnection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLConnection {
  public static Connection getConnection() {
    Properties properties = new Properties();
    FileInputStream input = null;
    Connection conn = null;

    try {
      input = new FileInputStream("src/main/resources/db.properties");
      properties.load(input);

      String url = properties.getProperty("db.url");
      String username = properties.getProperty("db.username");
      String password = properties.getProperty("db.password");
      conn = DriverManager.getConnection(url, username, password);

    } catch (SQLException | IOException e) {
      System.out.println("Error al establecer la conexion: " + e.getMessage());
    } finally {
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return conn;
  }
}

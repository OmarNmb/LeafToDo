package Services;

import model.Category;
import utils.SQLConnection.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryServices {
  public List<Category> getCategories() {
    List<Category> categories = new ArrayList<>();

    String query = "SELECT IDCategory, Name, Color FROM Category;";

    try (Connection conn = SQLConnection.getConnection()) {
      PreparedStatement statement = conn.prepareStatement(query);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int IDCategory = resultSet.getInt("IDCategory");
        String name = resultSet.getString("Name");
        String colorCode = resultSet.getString("Color");

        Category category = new Category(IDCategory, name, colorCode);
        categories.add(category);
      }

    } catch (SQLException e) {
      System.out.println("Error retrieving categories");
      e.printStackTrace();
    }
    return categories;
  }

  public List<Category> getCategoriesByID(int ID) {
    List<Category> categories = new ArrayList<>();
    // DECLARE @IDCategory INT = 1;
    // EXEC GetCategoryByID @IDCategory;
    // I get id, name and color #
    
    String query = "EXEC GetCategoryByID @IDCategory = ?;";
    try (Connection conn = SQLConnection.getConnection()) {
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setInt(1, ID);
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        int IDCategory = resultSet.getInt("IDCategory");
        String name = resultSet.getString("Name");
        String colorCode = resultSet.getString("Color");

        Category category = new Category(IDCategory, name, colorCode);
        categories.add(category);
      }

    } catch (SQLException e) {
      System.out.println("Error retrieving categories");
      e.printStackTrace();
    }
    return categories;
  }

  public int getID(String categoryName) {
    int IDCategory = 0;
    String query = "SELECT IDCategory FROM Category WHERE Name = ?";
    try (Connection conn = SQLConnection.getConnection()) {
      PreparedStatement statement = conn.prepareStatement(query);
      statement.setString(1, categoryName);

      ResultSet result = statement.executeQuery();

      if (result.next()) {
        IDCategory = result.getInt("IDCategory");
      }
    } catch (SQLException e) {
      System.out.println("Error retrieving IDCategory");
      e.printStackTrace();
    }

    return IDCategory;
  }
}

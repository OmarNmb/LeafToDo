package Services;

import model.Reminder;
import utils.SQLConnection.SQLConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class ReminderServices {

  public boolean createReminder(Reminder reminder) {
    boolean result = false;
    LocalDate dateNotice = reminder.getDateNotice();
    LocalTime timeNotice = reminder.getTimeNotice();
    int IDTask = reminder.getIDTask();

    String query = "{CALL InsertReminder (?, ?, ?)};";

    try (Connection conn = SQLConnection.getConnection(); CallableStatement statement = conn.prepareCall(query)) {

      statement.setDate(1, java.sql.Date.valueOf(dateNotice));
      statement.setTime(2, java.sql.Time.valueOf(timeNotice));
      statement.setInt(3, IDTask);

      result = statement.executeUpdate() > 0;
      System.out.println("Reminder inserted");
    } catch (SQLException e) {
      System.out.println("Error inserting the reminder");
      e.printStackTrace();
    }
    return result;
  }

  public LocalDate convertToLocalDate(int year, String month, int day) {
    Map<String, Integer> monthMap = new HashMap<>();
    monthMap.put("January", 1);
    monthMap.put("February", 2);
    monthMap.put("March", 3);
    monthMap.put("April", 4);
    monthMap.put("May", 5);
    monthMap.put("June", 6);
    monthMap.put("July", 7);
    monthMap.put("August", 8);
    monthMap.put("September", 9);
    monthMap.put("October", 10);
    monthMap.put("November", 11);
    monthMap.put("December", 12);

    int monthNumber = monthMap.get(month);

    return LocalDate.of(year, monthNumber, day);
  }
}

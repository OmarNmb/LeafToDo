package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reminder {
  private LocalDate dateNotice;

  private LocalTime timeNotice;

  private int IDTask;

  public Reminder(LocalDate dateNotice, LocalTime timeNotice, int IDTask) {
    this.dateNotice = dateNotice;
    this.timeNotice = timeNotice;
    this.IDTask = IDTask;
  }

  public LocalDate getDateNotice() {
    return dateNotice;
  }

  public LocalTime getTimeNotice() {
    return timeNotice;
  }

  public int getIDTask() {
    return IDTask;
  }
}

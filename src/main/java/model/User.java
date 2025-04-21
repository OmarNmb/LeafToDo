package model;

public class User {
  private int IDUser;
  private String email;
  private String userName;
  private String password;

  public User(int IDUser, String email, String userName, String password) {
    this.IDUser = IDUser;
    this.email = email;
    this.userName = userName;
    this.password = password;
  }

  public int getIDUser() {
    return IDUser;
  }

  public String getUserName() {
    return userName;
  }

  public String getEmail() {
    return email;
  }
}

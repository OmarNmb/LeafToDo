package Services;

import model.User;

/*
Current logged in user
 */

public class SessionServices {
  private static User currentUser;

  public static void setCurrentUser(User user) {
    currentUser = user;
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  public static void logout() {
    currentUser = null;
  }
}

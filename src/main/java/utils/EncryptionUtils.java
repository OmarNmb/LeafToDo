package utils;

import org.mindrot.jbcrypt.BCrypt;

public class EncryptionUtils {
  public static String encryptPassword (String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  public static boolean validatePassword(String password, String hashedPassword) {
    return BCrypt.checkpw(password, hashedPassword);
  }
}

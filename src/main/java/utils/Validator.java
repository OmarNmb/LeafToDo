package utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

  private static final Pattern emailPattern = Pattern.compile(EMAIL_REGEX);

  private static final Pattern passwordPattern = Pattern.compile(PASSWORD_REGEX);

  public static boolean validateInputEmail(String email) {
    Matcher matcher = emailPattern.matcher(email);
    return matcher.matches();
  }

  public static boolean validateInputPassword(String password) {
    Matcher matcher = passwordPattern.matcher(password);
    return matcher.matches();
  }
}

package controllers;

import Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.Paths;
import utils.SceneHelper;
import utils.Validator;

import java.io.IOException;
import java.util.Objects;

public class ViewCreateAccountController {
  @FXML
  private Button btnLoginNow;

  @FXML
  private ImageView imgCheckEmail;

  @FXML
  private ImageView imgCheckPassword;

  @FXML
  private Label lblMessageEmail;

  @FXML
  private Label lblMessagePassword;

  @FXML
  private TextField txtNewEmail;

  @FXML
  private TextField txtNewUserName;

  @FXML
  private PasswordField txtNewPassword;

  @FXML
  private void handleBtnLoginNowAction(ActionEvent e) {
    backToLogin();
  }

  @FXML
  private void handleCreateAccountAction() {
    if (!txtNewEmail.getText().isBlank() && !txtNewPassword.getText().isBlank() && !txtNewUserName.getText().isBlank()) {
      validateEmail();
      validatePassword();
      createAccount();
    } else {
      lblMessageEmail.setText("Please fill in all fields.");
    }

  }

  private void backToLogin() {
    SceneHelper.changeScene(Paths.VIEW_LOGIN, btnLoginNow);
  }

  private void createAccount() {
    UserServices userServices = new UserServices();
    String email = txtNewEmail.getText();
    String userName = txtNewUserName.getText();
    String password = txtNewPassword.getText();

    boolean isValidEmail = Validator.validateInputEmail(email);
    boolean isValidPassword = Validator.validateInputPassword(password);


    if (isValidEmail && isValidPassword) {
      boolean isUserCreated = userServices.createUser(email, userName, password);
      if (isUserCreated) {
        lblMessageEmail.setText("Account successfully created");
        backToLogin();
      } else {
        lblMessageEmail.setText("This account already exists");
      }
    }
  }

  private void validateEmail() {
    String email = txtNewEmail.getText();
    boolean isValidEmail = Validator.validateInputEmail(email);

    Image checkImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.CHECK_IMAGE)));
    Image uncheckImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.UNCHECK_IMAGE)));

    if (isValidEmail) {
      imgCheckEmail.setImage(checkImg);
      lblMessageEmail.setText("");
    } else {
      imgCheckEmail.setImage(uncheckImg);
      lblMessageEmail.setText("Email invalid. Please enter a valid email");
    }
  }

  private void validatePassword() {
    String password = txtNewPassword.getText();
    boolean isValidPassword = Validator.validateInputPassword(password);

    Image checkImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.CHECK_IMAGE)));
    Image uncheckImg = new Image(Objects.requireNonNull(getClass().getResourceAsStream(Paths.UNCHECK_IMAGE)));

    if (isValidPassword) {
      imgCheckPassword.setImage(checkImg);
      lblMessagePassword.setText("");
    } else {
      imgCheckPassword.setImage(uncheckImg);
      lblMessagePassword.setText("Invalid password. Must be at least 6 characters long, " +
        "including at least one uppercase letter, one lowercase letter, one number and one symbol.");
    }
  }
}

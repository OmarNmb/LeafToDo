package controllers;

import Services.SessionServices;
import Services.UserServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import model.User;
import utils.Paths;
import utils.SceneHelper;
import utils.Validator;

public class ViewMyProfileController {

  @FXML
  private Label goLogOut;

  @FXML
  private Label goHome;

  @FXML
  private Button btnUpdateEmail;

  @FXML
  private Button btnUpdateUsername;

  private UserServices userServices;

  private User currentUser;

  @FXML
  private TextField txtUsername;

  @FXML
  private TextField txtEmail;

  @FXML
  private PasswordField txtCurrentPassword;

  @FXML
  private PasswordField txtNewPassword;

  @FXML
  private PasswordField txtConfirmNewPassword;

  @FXML
  private Button btnConfirmUpdateUsername;

  @FXML
  private Button btnCancelUpdateUsername;

  @FXML
  private Button btnUpdatePassword;

  @FXML
  private Button btnConfirmUpdatePassword;

  @FXML
  private Button btnCancelUpdatePassword;

  @FXML
  private Button btnConfirmUpdateEmail;

  @FXML
  private Button btnCancelUpdateEmail;

  @FXML
  private Button btnCancel;

  @FXML
  private Label lblMessageLogin;

  @FXML
  private Rectangle rectanglePassword1;

  @FXML
  private Rectangle rectanglePassword2;

  @FXML
  private Rectangle rectanglePassword3;

  @FXML
  private ImageView imgCurrentPassword;

  @FXML
  private ImageView imgNewPassword;

  @FXML
  private ImageView imgConfirmNewPassword;

   @FXML
  private void initialize() {
    userServices = new UserServices();
    currentUser = SessionServices.getCurrentUser();

    String currentUserName = currentUser.getUserName();
    String currentEmail = currentUser.getEmail();
    txtEmail.setText(currentEmail);
    txtUsername.setText(currentUserName);
  }

  @FXML
  private void handleBtnCancel(ActionEvent e) {
    Scene currentScene = btnCancel.getScene();
    SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, btnCancel);
  }

  @FXML
  private void handleBtnUpdateUsername(ActionEvent e) {
    txtUsername.setEditable(true);
    txtCurrentPassword.setVisible(true);

    rectanglePassword1.setVisible(true);
    imgCurrentPassword.setVisible(true);

    btnConfirmUpdateUsername.setVisible(true);
    btnCancelUpdateUsername.setVisible(true);
    btnUpdateUsername.setVisible(false);
  }

  @FXML
  private void handleBtnConfirmUpdateUsername(ActionEvent e) {
    boolean result = false;
    int IDUser = currentUser.getIDUser();
    String newUsername = txtUsername.getText();
    String password = txtCurrentPassword.getText();

    if (!newUsername.isBlank()) {
      if (userServices.verifyPassword(IDUser, password)) {
        result = userServices.updateUsername(IDUser, newUsername);
      } else {
        lblMessageLogin.setText("Invalid Password");
      }
    } else {
      lblMessageLogin.setText("Please enter a value for the username");
    }

    if (result) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText("Username updated");
      alert.show();

      txtUsername.setEditable(false);

      rectanglePassword1.setVisible(false);
      imgCurrentPassword.setVisible(false);
      txtCurrentPassword.setVisible(false);
      txtCurrentPassword.setText("");

      btnConfirmUpdateUsername.setVisible(false);
      btnCancelUpdateUsername.setVisible(false);
      btnUpdateUsername.setVisible(true);
    }

  }

  @FXML
  private void handleBtnCancelUpdateUserName(ActionEvent e) {
    txtUsername.setEditable(false);
    txtUsername.setText(currentUser.getUserName());

    rectanglePassword1.setVisible(false);
    imgCurrentPassword.setVisible(false);
    txtCurrentPassword.setVisible(false);
    txtCurrentPassword.setText("");

    btnConfirmUpdateUsername.setVisible(false);
    btnCancelUpdateUsername.setVisible(false);
    btnUpdateUsername.setVisible(true);

    lblMessageLogin.setText("");
  }

  @FXML
  private void handleBtnUpdatePassword(ActionEvent e) {
    rectanglePassword1.setVisible(true);
    imgCurrentPassword.setVisible(true);
    txtCurrentPassword.setVisible(true);

    rectanglePassword2.setVisible(true);
    imgNewPassword.setVisible(true);
    txtNewPassword.setVisible(true);

    rectanglePassword3.setVisible(true);
    imgConfirmNewPassword.setVisible(true);
    txtConfirmNewPassword.setVisible(true);

    btnCancelUpdatePassword.setVisible(true);
    btnConfirmUpdatePassword.setVisible(true);
    btnUpdatePassword.setVisible(false);
  }

  @FXML
  private void handleBtnConfirmUpdatePassword(ActionEvent e) {
    boolean result = false;
    int IDUser = currentUser.getIDUser();
    String currentPassword = txtCurrentPassword.getText();
    String newPassword = txtNewPassword.getText();
    String confirmNewPassword = txtConfirmNewPassword.getText();

    if (!newPassword.isBlank() && !confirmNewPassword.isBlank()) {
      if (Validator.validateInputPassword(newPassword)) {
        if (newPassword.equals(confirmNewPassword)) {
          if (userServices.verifyPassword(IDUser, currentPassword)) {

            result = userServices.updatePassword(IDUser, newPassword);

          } else {
            lblMessageLogin.setText("Invalid password");
          }

        } else {
          lblMessageLogin.setText("Please verify that the new password and confirmation are the same.");
        }
      } else {
        lblMessageLogin.setText("Invalid password. Must be at least 6 characters long, " +
          "including at least one uppercase letter, one lowercase letter, one number and one symbol.");
      }
    } else {
      lblMessageLogin.setText("Please enter a value for the new password and confirm it.");
    }

    if (result) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText("Password updated");
      alert.show();

      rectanglePassword1.setVisible(false);
      imgCurrentPassword.setVisible(false);
      txtCurrentPassword.setVisible(false);
      txtCurrentPassword.setText("");

      rectanglePassword2.setVisible(false);
      imgNewPassword.setVisible(false);
      txtNewPassword.setVisible(false);
      txtNewPassword.setText("");

      rectanglePassword3.setVisible(false);
      imgConfirmNewPassword.setVisible(false);
      txtConfirmNewPassword.setVisible(false);
      txtConfirmNewPassword.setText("");

      btnCancelUpdatePassword.setVisible(false);
      btnConfirmUpdatePassword.setVisible(false);
      btnUpdatePassword.setVisible(true);
      lblMessageLogin.setText("");
    }
  }

  @FXML
  private void handleBtnCancelUpdatePassword(ActionEvent e) {
    rectanglePassword1.setVisible(false);
    imgCurrentPassword.setVisible(false);
    txtCurrentPassword.setVisible(false);
    txtCurrentPassword.setText("");

    rectanglePassword2.setVisible(false);
    imgNewPassword.setVisible(false);
    txtNewPassword.setVisible(false);
    txtNewPassword.setText("");

    rectanglePassword3.setVisible(false);
    imgConfirmNewPassword.setVisible(false);
    txtConfirmNewPassword.setVisible(false);
    txtConfirmNewPassword.setText("");

    btnCancelUpdatePassword.setVisible(false);
    btnConfirmUpdatePassword.setVisible(false);
    btnUpdatePassword.setVisible(true);

    lblMessageLogin.setText("");
  }

  @FXML
  private void handleBtnUpdateEmail(ActionEvent e) {
    txtEmail.setEditable(true);
    txtCurrentPassword.setVisible(true);

    rectanglePassword1.setVisible(true);
    imgCurrentPassword.setVisible(true);

    btnConfirmUpdateEmail.setVisible(true);
    btnCancelUpdateEmail.setVisible(true);
    btnUpdateEmail.setVisible(false);
  }

  @FXML
  private void handleBtnConfirmUpdateEmail(ActionEvent e) {
    boolean result = false;
    int IDUser = currentUser.getIDUser();
    String newEmail = txtEmail.getText();
    String currentPassword = txtCurrentPassword.getText();

    if (!newEmail.isBlank()) {
      if(Validator.validateInputEmail(newEmail)) {
        if(userServices.verifyPassword(IDUser, currentPassword)) {

          result = userServices.updateEmail(IDUser, newEmail);

        } else {
          lblMessageLogin.setText("Invalid password");
        }
      } else {
        lblMessageLogin.setText("Email invalid. Please enter a valid email");
      }
    } else {
      lblMessageLogin.setText("Please enter an email value");
    }

    if (result) {
      Alert alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setHeaderText(null);
      alert.setContentText("Email updated");
      alert.show();

      txtEmail.setEditable(false);
      txtCurrentPassword.setVisible(false);

      rectanglePassword1.setVisible(false);
      imgCurrentPassword.setVisible(false);

      btnConfirmUpdateEmail.setVisible(false);
      btnCancelUpdateEmail.setVisible(false);
      btnUpdateEmail.setVisible(true);
      txtCurrentPassword.setText("");

      lblMessageLogin.setText("");
    }
  }

  @FXML
  private void handleBtnCancelUpdateEmail(ActionEvent e) {
    txtEmail.setEditable(false);
    txtCurrentPassword.setVisible(false);
    txtCurrentPassword.setText("");

    rectanglePassword1.setVisible(false);
    imgCurrentPassword.setVisible(false);

    btnConfirmUpdateEmail.setVisible(false);
    btnCancelUpdateEmail.setVisible(false);
    btnUpdateEmail.setVisible(true);

    lblMessageLogin.setText("");
  }

  @FXML
  private void handleBtnGoHome(MouseEvent e) {
    Scene currentScene = goHome.getScene();
    SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, goHome);
  }

  @FXML
  private void handleBtnLogOut(MouseEvent e) {
    Scene currentScene = goHome.getScene();
    SceneHelper.changeScene(Paths.VIEW_LOGIN, goHome);
  }
}

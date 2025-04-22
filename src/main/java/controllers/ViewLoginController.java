package controllers;

import Services.SessionServices;
import Services.UserServices;
import utils.EncryptionUtils;
import utils.SQLConnection.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import utils.Paths;
import utils.SceneHelper;

import java.io.IOException;
import java.sql.*;

import static java.lang.System.out;

public class ViewLoginController {

  @FXML
  private Button btnLogin;

  @FXML
  private Button btnNewAccount;

  @FXML
  private PasswordField txtPassword;

  @FXML
  private TextField txtUsername;

  @FXML
  private Label lblMessageLogin;

  @FXML
  private void handleBtnNewAccount(ActionEvent e) {
    try {

      FXMLLoader loader = new FXMLLoader(getClass().getResource(Paths.VIEW_CREATE_ACCOUNT));
      Parent root = loader.load();

      Scene scene = btnNewAccount.getScene();
      Stage currentStage = (Stage) scene.getWindow();

      currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }

  public void handleBtnLogin(ActionEvent e) {

    if (!txtUsername.getText().isBlank() && !txtPassword.getText().isBlank()) {
      validateLogin();
    } else {
      lblMessageLogin.setText("Please enter username and password.");
    }
  }

  public void validateLogin() {
    UserServices userServices = new UserServices();

    String password = txtPassword.getText();
    String userName = txtUsername.getText();
    boolean loggedIn = userServices.validateLogin(password, userName);

    if (loggedIn) {
      System.out.println("Login Successful");
      Scene currentScene = btnNewAccount.getScene();
      SceneHelper.changeScene(Paths.VIEW_MAIN_INTERFACE, btnNewAccount);
    } else {
      lblMessageLogin.setText("Invalid Login. Please try again.");
    }
  }
}

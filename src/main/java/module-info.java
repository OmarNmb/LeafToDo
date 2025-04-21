module application.db2team6 {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;
  requires com.fasterxml.jackson.databind;
  requires java.desktop;
requires javafx.graphics;
  requires jbcrypt;
    requires com.jfoenix;


    opens controllers to javafx.fxml;
  exports application;

}
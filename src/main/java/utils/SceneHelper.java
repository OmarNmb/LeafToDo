package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneHelper {
  public static void changeScene(String fxmlPath, Node anyNodeInsideScene) {
    try {
      FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(fxmlPath));
      Parent root = loader.load();

      // Obtener el Stage desde cualquier nodo de la escena actual
      Stage stage = (Stage) anyNodeInsideScene.getScene().getWindow();

      // Crear y asignar nueva escena
      Scene newScene = new Scene(root);
      stage.setScene(newScene);

      // Ajustar automáticamente el tamaño del stage al contenido de la nueva escena
      stage.sizeToScene();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneHelper {
  public static void changeScene(String path, Scene currentScene) {
    try {
      FXMLLoader loader = new FXMLLoader(SceneHelper.class.getResource(path));
      Parent root = loader.load();

      currentScene.setRoot(root);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}

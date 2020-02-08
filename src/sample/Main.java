package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        /*
         * Launch WhatsChat
         */

        try {
            Parent root = FXMLLoader.load(getClass().getResource("Scenes/Login.fxml"));

            primaryStage.setTitle("WhatsChat");
            primaryStage.getIcons().add(new Image(getClass().getResource("Assets/logo.png").toExternalForm()));
            Scene landingScene = new Scene(root, 1200, 675);
            primaryStage.setScene(landingScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

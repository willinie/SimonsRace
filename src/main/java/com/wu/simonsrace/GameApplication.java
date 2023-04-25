package com.wu.simonsrace;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * javafx starting class, initialize launcher interface
 */
public class GameApplication extends Application {

    @FXML
    private ImageView stepDiceImg;

    /**
     * javafx application starting method
     * @param stage primary stage
     * @throws IOException when FXML file not found
     */
    @Override
    public void start(Stage stage) throws IOException {


        // Initialize launcher UI
        FXMLLoader launcherFxmlLoader = new FXMLLoader(GameApplication.class.getResource("launcher-view.fxml"));
        Scene launcherScene = new Scene(launcherFxmlLoader.load(), 600, 400);
        stage.setTitle("Launcher");
        stage.setScene(launcherScene);

        // Initialize controller
        LauncherController launcherController = launcherFxmlLoader.getController();
        launcherController.initLauncher();

        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}
package com.wu.simonsrace;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for launcher scene
 */
public class LauncherController {
    @FXML
    public Button launchButton;
    @FXML
    private ChoiceBox<Integer> boardLengthChoiceBox;
    @FXML
    private ChoiceBox<Integer> boardWidthChoiceBox;
    @FXML
    private ChoiceBox<Integer> playerChoiceBox;

    /**
     * Launcher starting class
     */
    public void initLauncher(){
        boardLengthChoiceBox.getItems().addAll(7,8,9,10);
        boardLengthChoiceBox.setValue(7);

        boardWidthChoiceBox.getItems().addAll(4,5,6);
        boardWidthChoiceBox.setValue(4);

        playerChoiceBox.getItems().addAll(2,3,4);
        playerChoiceBox.setValue(2);
    }

    /**
     * generate new game scene with input data
     * @throws IOException when FXML file not found
     */
    public void onLaunchButtonClick() throws IOException {
        int gridLength = boardLengthChoiceBox.getValue();
        int boardWidth = boardWidthChoiceBox.getValue();
        int playerNum = playerChoiceBox.getValue();

        // Test ChoiceBox output
        System.out.printf("%d %d %d\n", gridLength, boardWidth, playerNum);

        // Load game scene
        FXMLLoader gameFxmlLoader = new FXMLLoader(GameApplication.class.getResource("board-view-example.fxml"));
        Scene gameScene = new Scene(gameFxmlLoader.load(), 640, 800);
        Stage stage = (Stage) boardLengthChoiceBox.getScene().getWindow();
        stage.setTitle("Simon's Race");
        stage.setScene(gameScene);

        // Get GameController
        GameController gameController = gameFxmlLoader.getController();
        gameController.initGridPane(gridLength, boardWidth, playerNum);

    }

}

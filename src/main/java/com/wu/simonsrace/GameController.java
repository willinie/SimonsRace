package com.wu.simonsrace;

import com.wu.simonsrace.elements.board.Player;
import com.wu.simonsrace.elements.board.Players;
import com.wu.simonsrace.elements.board.Trap;
import com.wu.simonsrace.elements.dice.DirectionDice;
import com.wu.simonsrace.elements.dice.StepDice;
import com.wu.simonsrace.game.GameUtil;
import com.wu.simonsrace.structure.Board;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Controller class for actual game scene
 */
public class GameController {
    private final StepDice stepDice = new StepDice();
    private int step;
    private DirectionDice.Direction direction;
    private final DirectionDice directionDice = new DirectionDice();
    private static Board board;
    private static Players players;
    private static int currentPlayerIndex; // Range [0, players.size()-1]

    @FXML
    public AnchorPane upperAnchorPane;
    @FXML
    public Label roundLabel;
    @FXML
    public Label penaltyLabel;
    @FXML
    private ImageView stepDiceImg;
    @FXML
    private Label directionDiceTxt;
    @FXML
    private GridPane boardPane;

    @FXML
    private Button rollButton;
    @FXML
    private Button leftButton;
    @FXML
    private Button rightButton;
    @FXML
    private Button missButton;

    /**
     * defines action taken when roll button was clicked
     */
    @FXML
    public void onRollButtonClick() {
        // Control UI
        penaltyLabel.setText("");
        step = stepDice.rollStep();
        direction = directionDice.rollDirection();
        System.out.printf("Direction: %s, Step: %d\n", direction.toString(), step);

        if (step == 1) stepDiceImg.setImage(new Image(getClass().getResourceAsStream("1.jpg")));
        if (step == 2) stepDiceImg.setImage(new Image(getClass().getResourceAsStream("2.jpg")));
        if (step == 3) stepDiceImg.setImage(new Image(getClass().getResourceAsStream("3.jpg")));
        if (step == 4) stepDiceImg.setImage(new Image(getClass().getResourceAsStream("4.jpg")));

        if (direction == DirectionDice.Direction.FORWARD) directionDiceTxt.setText("Forward");
        if (direction == DirectionDice.Direction.BACKWARD) directionDiceTxt.setText("Backward");
        if (direction == DirectionDice.Direction.MISS) directionDiceTxt.setText("Miss");

        Player currentPlayer = players.get(currentPlayerIndex);
        if(board.boardArray[currentPlayer.positionX][currentPlayer.positionY] instanceof Trap && ((Trap) board.boardArray[currentPlayer.positionX][currentPlayer.positionY]).timer > 0)
        {
            ((Trap) board.boardArray[currentPlayer.positionX][currentPlayer.positionY]).timer--;
            penaltyLabel.setText("You are in a trap! " + ((Trap) board.boardArray[currentPlayer.positionX][currentPlayer.positionY]).timer + " round(s) remaining");
            onMissButtonClick();
            return;
        }

        // If direction dice is miss then skip this player
        if(direction == DirectionDice.Direction.MISS)
        {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
            roundLabel.setText(String.format("P%d's round!", currentPlayerIndex+1));
        }
        else
        {
            for(int i = 1; i <= step; i++)
            {
                int[] target = GameUtil.getTarget(direction, i, players.get(currentPlayerIndex));
                // If target grid is unavailable
                if(!GameUtil.verifyTarget(target, board, players))
                {
                    rollButton.setDisable(true);
                    leftButton.setDisable(false);
                    rightButton.setDisable(false);
                    missButton.setDisable(false);
                    penaltyLabel.setText("Road blocked, please select left/right/miss");
                    return;
                }

            }
            // If target grid is available, must go to target grid
            movePlayer(boardPane, board, players, direction);
            Player player = players.get(currentPlayerIndex);
            // If player walked into a trap
            if(board.boardArray[player.positionX][player.positionY] instanceof Trap)
            {
                ((Trap) board.boardArray[player.positionX][player.positionY]).timer = 3;
                penaltyLabel.setText("You are in a trap! 3 rounds remaining");
                for(Node node : boardPane.getChildren())
                {
                    if(node instanceof HBox &&
                            GridPane.getRowIndex(node) == players.get(currentPlayerIndex).positionX &&
                            GridPane.getColumnIndex(node) == players.get(currentPlayerIndex).positionY)
                    {
                        node.setStyle("-fx-background-color: red");
                    }
                }
            }
            if (players.get(currentPlayerIndex).positionX == 0) {
                endGame(players.get(currentPlayerIndex));
                return;
            }
            onMissButtonClick();

        }


    }

    /**
     * defines action taken when someone won and end the game
     * @param player winner of the game
     */
    private void endGame(Player player) {
        roundLabel.setText(String.format("Player%d win!!", player.id));
        rollButton.setDisable(true);
        leftButton.setDisable(true);
        rightButton.setDisable(true);
        missButton.setDisable(true);

        try{
            File file = new File("src/main/resources/com/wu/simonsrace/winner.csv");
            if(file.createNewFile())
                System.out.println("New file created");
            else
                System.out.println("File existed");
        }
        catch(IOException ioe) {
            ioe.printStackTrace();
        }

        BufferedReader br = null;
        BufferedWriter bw = null;
        String line = "";
        String newLines = "";

        try {

            br = new BufferedReader(new FileReader("src/main/resources/com/wu/simonsrace/winner.csv"));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] data = line.split(",");
                int times = Integer.parseInt(data[1]);
                if(("player" + player.id).equals(data[0])) times++;
                data[1] = Integer.toString(times);
                System.out.printf("%s won: %s times\n", data[0], data[1]);
                newLines = newLines + data[0] + "," + data[1] + "\n";
            }
            br.close();

            bw = new BufferedWriter(new FileWriter("src/main/resources/com/wu/simonsrace/winner.csv"));
            bw.write(newLines);
            bw.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    /**
     *
     * @param boardPane gridPane object, UI representing board
     * @param board logic game board object
     * @param players players object
     * @param direction direction to move player
     */
    private void movePlayer(GridPane boardPane, Board board, Players players, DirectionDice.Direction direction) {
        // Remove current player grid
        for(Node node : boardPane.getChildren())
        {
            if(node instanceof VBox &&
                    GridPane.getRowIndex(node) == players.get(currentPlayerIndex).positionX &&
                    GridPane.getColumnIndex(node) == players.get(currentPlayerIndex).positionY)
            {
                boardPane.getChildren().remove(node);
                break;
            }
        }
        // Set logic player position
        players.get(currentPlayerIndex).setPosition(GameUtil.getTarget(direction, step, players.get(currentPlayerIndex)));
        // Draw new player
        VBox vbox = new VBox();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("p" + players.get(currentPlayerIndex).id +".png")));
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(boardPane.heightProperty().divide(board.boardLength*2));
        imageView.fitWidthProperty().bind(boardPane.widthProperty().divide(board.boardWidth));
        vbox.getChildren().addAll(imageView, new Label("Player " + players.get(currentPlayerIndex).id));
        vbox.setAlignment(Pos.CENTER);
        boardPane.add(vbox, players.get(currentPlayerIndex).positionY, players.get(currentPlayerIndex).positionX, 1,1);
    }

    @FXML
    public void onLeftButtonClick(){
        for(int i = 1; i <= step; i++)
        {
            int[] target = GameUtil.getTarget(DirectionDice.Direction.LEFT, i, players.get(currentPlayerIndex));
            if(!GameUtil.verifyTarget(target, board, players))
            {
                onMissButtonClick();
                return;
            }

        }
        movePlayer(boardPane, board, players, DirectionDice.Direction.LEFT);
        onMissButtonClick();


    }

    @FXML
    public void onRightButtonClick(){
        for(int i = 1; i <= step; i++)
        {
            int[] target = GameUtil.getTarget(DirectionDice.Direction.RIGHT, i, players.get(currentPlayerIndex));
            if(!GameUtil.verifyTarget(target, board, players))
            {
                onMissButtonClick();
                return;
            }

        }
        movePlayer(boardPane, board, players, DirectionDice.Direction.RIGHT);
        onMissButtonClick();


    }

    @FXML
    public void onMissButtonClick(){
        currentPlayerIndex = (currentPlayerIndex+1) % players.size();
        missButton.setDisable(true);
        leftButton.setDisable(true);
        rightButton.setDisable(true);
        rollButton.setDisable(false);
        roundLabel.setText(String.format("P%d's round!", currentPlayerIndex+1));
    }

    /**
     *
     * @param gridLength length of the board excluding starting row and ending row
     * @param boardWidth width of the board
     * @param playerNum number of players
     */
    @FXML
    public void initGridPane(int gridLength, int boardWidth, int playerNum){
        // Initialize game logic
        currentPlayerIndex = 0;
        board = Board.gnrtBoard(gridLength, boardWidth);
        players = Players.gnrtPlayers(board, playerNum);
        board.printBoard_CLI(players); // Test board logic

        // Initialize UI
        for (int i = 0; i < board.boardWidth - 4; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / board.boardWidth);
            boardPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < board.boardLength - 9; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / board.boardLength);
            boardPane.getRowConstraints().add(rowConst);
        }
        for(int i = 0; i < board.boardLength; i++)
        {
            for(int j = 0; j < board.boardWidth; j++)
            {
                HBox hbox = new HBox();
                ImageView imageView;
                if ((i + j) % 2 == 0) hbox.setStyle("-fx-background-color: #81c483;");
                switch (board.getElement(i, j))
                {
                    case "f":
                        imageView = new ImageView(new Image(getClass().getResourceAsStream("fire.png")));
                        imageView.setPreserveRatio(true);
                        imageView.fitHeightProperty().bind(boardPane.heightProperty().divide(board.boardLength));
                        imageView.fitWidthProperty().bind(boardPane.widthProperty().divide(board.boardWidth));
                        hbox.getChildren().add(imageView);
                        break;
                    case "h":
                        imageView = new ImageView(new Image(getClass().getResourceAsStream("hole.png")));
                        imageView.setPreserveRatio(true);
                        imageView.fitHeightProperty().bind(boardPane.heightProperty().divide(board.boardLength));
                        imageView.fitWidthProperty().bind(boardPane.widthProperty().divide(board.boardWidth));
                        hbox.getChildren().add(imageView);
                        break;
                }
                hbox.setAlignment(Pos.CENTER);
                boardPane.add(hbox, j, i, 1, 1);
            }
        }

        for(Player player: players.getPlayers()){
            VBox vbox = new VBox();
            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("p" + player.id +".png")));
            imageView.setPreserveRatio(true);
            imageView.fitHeightProperty().bind(boardPane.heightProperty().divide(board.boardLength*2));
            imageView.fitWidthProperty().bind(boardPane.widthProperty().divide(board.boardWidth));
            vbox.getChildren().addAll(imageView, new Label("Player " + player.id));
            vbox.setAlignment(Pos.CENTER);
            boardPane.add(vbox, player.positionY, player.positionX, 1,1);

        }

        // Disable buttons
        leftButton.setDisable(true);
        rightButton.setDisable(true);
        missButton.setDisable(true);
    }
}
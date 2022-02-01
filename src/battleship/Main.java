package battleship;

import java.util.ArrayList;
import java.util.Random;

import Exceptions.InvalidCountException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application {

    private int isShipPlaced = 0;

    private boolean enemyTurn = true;
    private boolean running = false;

    private Random random = new Random();

    private int[] type = new int[5];
    private int[] isHitScore = new int[5];
    private int[] isSunkScore = new int[5];
    private String[] nameType = new String[5];
    private int enemyScore = 0;
    private int myScore = 0;
    private int myShots = 0;
    private int enemyShots = 0;
    private int myKill = 0;
    private int enKill = 0;
    private float myPerc = 0;

    private Board EnemyBoard;
    private Board PlayerBoard;

    private ArrayList<Cell> playerHistory = new ArrayList<>(5);
    private ArrayList<Cell> enemyHistory = new ArrayList<>(5);
    private ArrayList<Cell> shot = new ArrayList<>(1);
    private ArrayList<Cell> shootLeft = new ArrayList<>();
    private ArrayList<Cell> shootRight = new ArrayList<>();
    private ArrayList<Cell> shootUp = new ArrayList<>();
    private ArrayList<Cell> shootDown = new ArrayList<>();

    /**
     * Method for the random  first play
     * @return boolean randomStart
     */
    private boolean enemyTurn() {

        Random randomSTART = new Random();
        return randomSTART.nextBoolean();
    }

    /**
     * Method that contains the most of the game buttons and calculations
     * @return the root that contains the game content
     * @throws InvalidCountException for wrong number of ships
     */
    private Parent createContent() throws InvalidCountException{

        /* --- Set values --- */
        nameType[0] = "Carrier";
        nameType[1] = "Battleship";
        nameType[2] = "Cruiser";
        nameType[3] = "Submarine";
        nameType[4] = "Destroyer";

        type[0] = 5;
        type[1] = 4;
        type[2] = 3;
        type[3] = 3;
        type[4] = 2;
        isHitScore[0] = 350;
        isHitScore[1] = 250;
        isHitScore[2] = 100;
        isHitScore[3] = 100;
        isHitScore[4] = 50;
        isSunkScore[0] = 1000;
        isSunkScore[1] = 500;
        isSunkScore[2] = 250;
        isSunkScore[3] = 0;
        isSunkScore[4] = 0;

        /* --- Set board --- */
        BorderPane root = new BorderPane();
        root.setPrefSize(800, 800);

        /* ----- Top  bar ---- */
        ToolBar toolbar = new ToolBar();
        toolbar.setStyle("-fx-min-width: 1000");
        root.setTop(toolbar);

        Text txt1 = new Text("My Ships");
        TextField txt2 = new TextField("N/A");
        txt2.setDisable(true);
        txt2.setStyle("-fx-opacity: 1;" + "-fx-max-width: 60");

        Text txt3 = new Text("Enemy Ships");
        TextField txt4 = new TextField("N/A");
        txt4.setDisable(true);
        txt4.setStyle("-fx-opacity: 1;" + "-fx-max-width: 60");

        Text txt5 = new Text("Player Points");
        TextField txt6 = new TextField("N/A");
        txt6.setDisable(true);
        txt6.setStyle("-fx-opacity: 1;" + "-fx-max-width: 60");

        Text txt7 = new Text("Enemy Points");
        TextField txt8 = new TextField("N/A");
        txt8.setDisable(true);
        txt8.setStyle("-fx-opacity: 1;" + "-fx-max-width: 60");

        Separator separator = new Separator();
        Separator separator2 = new Separator();

        toolbar.getItems().addAll(txt1, txt2, txt3, txt4, separator, txt5, txt6, txt7, txt8, separator2);


        /* -----  Right side bar ----- */

        Text playerPlays = new Text(" \n Player Shots left");
        Text enemyPlays = new Text("Enemy Shots left");

        TextField txt9 = new TextField("40");
        TextField txt10 = new TextField("40");
        txt9.setDisable(true);
        txt9.setStyle("-fx-opacity: 1;");
        txt10.setDisable(true);
        txt10.setStyle("-fx-opacity: 1;");

        playerPlays.setStyle("-fx-font-weight: bold");
        enemyPlays.setStyle("-fx-font-weight: bold");

        VBox rightvbox = new VBox(10, playerPlays, txt9, enemyPlays, txt10);
        rightvbox.setAlignment(Pos.TOP_CENTER);
        rightvbox.setStyle("-fx-padding: 16;" + "-fx-border-color: black;");
        root.setRight(rightvbox);


        /* ---- Left side bar ---- */

        Text MenuItems = new Text("Menus");
        MenuItems.setStyle("-fx-font-weight: bold");
        MenuItem menuItem1 = new MenuItem("Start");
        menuItem1.setStyle("-fx-padding: 5 22 10 22;");
        MenuItem menuItem2 = new MenuItem("Load");
        menuItem2.setStyle("-fx-padding: 5 22 10 22;");
        MenuItem menuItem3 = new MenuItem("Exit");
        menuItem3.setStyle("-fx-padding: 5 22 5 22;");
        MenuButton menuButton = new MenuButton("Application", null, menuItem1, menuItem2, menuItem3);
        menuButton.setStyle("-fx-padding: 0 0 0 0;");

        Text menu2 = new Text(" \n \n \n \n \n \n \n \n ");
        MenuItem menuItem4 = new MenuItem("Enemy Ships");
        menuItem4.setStyle("-fx-padding: 5 9 10 9;");
        MenuItem menuItem5 = new MenuItem("Player Shots");
        menuItem5.setStyle("-fx-padding: 5 9 10 9;");
        MenuItem menuItem6 = new MenuItem("Enemy Shots");
        menuItem6.setStyle("-fx-padding: 5 9 5 9;");
        MenuButton menuButton2 = new MenuButton("Details", null, menuItem4, menuItem5, menuItem6);
        menuButton2.setStyle("-fx-padding: 0 15 0 15;");


        menuItem1.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Start");
            alert.setHeaderText("Start game again??");
            new ButtonType("Yes");
            new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    event.consume();
                } else {
                    close();
                    try {
                        start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        menuItem3.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Exit game??");
            new ButtonType("Yes");
            new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL) {
                    event.consume();
                } else {
                    close();
                }
            });
        });
        menuItem4.setOnAction(event -> {

            Ship Shipd;
            StringBuilder shipsText = new StringBuilder();
            if (EnemyBoard.Ships.isEmpty()) {
                shipsText = new StringBuilder("No enemy ships on the board");
            } else {
                for (int i = 0; i < EnemyBoard.Ships.size(); i++) {
                    Shipd = EnemyBoard.Ships.get(i);
                    if (!Shipd.isHit()) {
                        shipsText.append(Shipd.shipType).append(": healthy\n");
                    } else {
                        if (Shipd.isAlive()) {
                            shipsText.append(Shipd.shipType).append(": hit\n");
                        } else {
                            shipsText.append(Shipd.shipType).append(": sunk\n");
                        }
                    }
                }
            }

            infoBox(shipsText.toString(), "Enemy Ships Information");
        });

        menuItem5.setOnAction(ePlayerShots -> {
            StringBuilder myShots = new StringBuilder();
            if (playerHistory.isEmpty()) {
                myShots = new StringBuilder("You haven't shot yet");
            }
            for (Cell shotCell : playerHistory) {
                if (shotCell.ship == null) {
                    myShots.append("(").append(shotCell.x).append(".").append(shotCell.y).append(")").append(": Miss \n");
                } else {
                    myShots.append("(").append(shotCell.x).append(".").append(shotCell.y).append(")").append(": Hit ").append(shotCell.ship.shipType).append("\n");
                }
            }
            infoBox(myShots.toString(), "My Shot History");

        });

        menuItem6.setOnAction(eEnemyShots -> {
            StringBuilder enemyShots = new StringBuilder();
            if (enemyHistory.isEmpty()) {
                enemyShots = new StringBuilder("No shot yet");
            }
            for (Cell shotCell : enemyHistory) {
                if (shotCell.ship == null) {
                    enemyShots.append("(").append(shotCell.x).append(".").append(shotCell.y).append(")").append(": Miss \n");
                } else {
                    enemyShots.append("(").append(shotCell.x).append(".").append(shotCell.y).append(")").append(": Hit ").append(shotCell.ship.shipType).append("\n");
                }
            }
            infoBox(enemyShots.toString(), "Enemy Shot History");
        });

        VBox leftvbox = new VBox(10, MenuItems, menuButton, menu2, menuButton2);
        leftvbox.setAlignment(Pos.TOP_CENTER);
        leftvbox.setStyle("-fx-padding: 16;" + "-fx-border-color: black;");
        root.setLeft(leftvbox);




        EnemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();


            myShots = myShots + 1;
            myScore = myScore + cell.highscore;
            myKill = myKill + cell.perc;
            txt4.setText(String.valueOf(EnemyBoard.ships));
            txt6.setText(String.valueOf(myScore));
            txt10.setText(String.valueOf(myPerc));
            txt9.setText(String.valueOf(40 - myShots));


            if (playerHistory.size() == 5) {
                playerHistory.remove(0);
            }
            playerHistory.add(cell);

            enemyTurn = true;
            CheckIfWin();



            if (EnemyBoard.ships == 0) {
                System.out.println("YOU WIN");
                System.exit(0);
            }

            if (enemyTurn)
                enemyMove(txt2, txt8, txt10);
        });

        PlayerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (isShipPlaced == 0) {
                if (PlayerBoard.placeShip(new Ship(type[isShipPlaced], event.getButton() == MouseButton.PRIMARY, isHitScore[isShipPlaced], isSunkScore[isShipPlaced], nameType[isShipPlaced]), cell.x, cell.y)) {
                    ++isShipPlaced;
                }
            }
            if (isShipPlaced == 1) {
                if (PlayerBoard.placeShip(new Ship(type[isShipPlaced], event.getButton() == MouseButton.PRIMARY, isHitScore[isShipPlaced], isSunkScore[isShipPlaced], nameType[isShipPlaced]), cell.x, cell.y)) {
                    ++isShipPlaced;
                }
            }
            if (isShipPlaced == 2) {
                if (PlayerBoard.placeShip(new Ship(type[isShipPlaced], event.getButton() == MouseButton.PRIMARY, isHitScore[isShipPlaced], isSunkScore[isShipPlaced], nameType[isShipPlaced]), cell.x, cell.y)) {
                    ++isShipPlaced;
                }
            }
            if (isShipPlaced == 3) {
                if (PlayerBoard.placeShip(new Ship(type[isShipPlaced], event.getButton() == MouseButton.PRIMARY, isHitScore[isShipPlaced], isSunkScore[isShipPlaced], nameType[isShipPlaced]), cell.x, cell.y)) {
                    ++isShipPlaced;
                }
            }
            if (isShipPlaced == 4) {
                if (PlayerBoard.placeShip(new Ship(type[isShipPlaced], event.getButton() == MouseButton.PRIMARY, isHitScore[isShipPlaced], isSunkScore[isShipPlaced], nameType[isShipPlaced]), cell.x, cell.y)) {
                    ++isShipPlaced;
                }
            }
            if (isShipPlaced == 5) {
                try {
                    startGame(txt2, txt8, txt10);
                } catch (InvalidCountException e) {
                    e.printStackTrace();
                }
            }

            if (isShipPlaced > 5){
                try {
                    throw new InvalidCountException("Error!");
                } catch (InvalidCountException e) {
                    e.printStackTrace();
                }
            }
        });

        Text txtME = new Text();
        Text txtENEMY = new Text();
        txtME.setText("My Board");
        txtME.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20");
        txtENEMY.setText("Enemy Board");
        txtENEMY.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 20");

        VBox vbox = new VBox(50, EnemyBoard, PlayerBoard);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);
        return root;
    }

    /**
     * Method for the enemy moves. Describes how enemy places ships
     * @param shipText for player's ships
     * @param scoreText for enemy's score
     * @param shotsText for enemy's shots
     */
    private void enemyMove(TextField shipText, TextField scoreText, TextField shotsText) {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = PlayerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();
            if(enemyTurn && !cell.ship.isAlive()){
                shootDown.clear();
                shootUp.clear();
                shootLeft.clear();
                shootRight.clear();
            }

            enemyScore = enemyScore + cell.highscore;
            enemyShots = enemyShots + 1;
            enKill = enKill + cell.perc;
            scoreText.setText(String.valueOf(enemyScore));
            shotsText.setText(String.valueOf(40 - enemyShots));
            shipText.setText(String.valueOf(PlayerBoard.ships));

            CheckIfWin();

            if (enemyHistory.size() == 5) {
                enemyHistory.remove(0);
            }
            enemyHistory.add(cell);

            if (shot.size() == 1) {
                shot.remove(0);
            }
            shot.add(cell);

            enemyTurn = false;

            if (PlayerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                System.exit(0);
            }
        }
    }

    /**
     *Method for the random placement of the computer's ships
     * @param shipText for player's ships
     * @param scoreText for enemy's score
     * @param shotsText for enemy's shots
     * @throws InvalidCountException
     */
    private void startGame(TextField shipText, TextField scoreText, TextField shotsText) throws InvalidCountException {
        //int length = 5;
        int count = 0;

        while (count <= 4) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (EnemyBoard.placeShip(new Ship(type[count], Math.random() < 0.5, isHitScore[count], isSunkScore[count], nameType[count]), x, y)) {
                count++;
            }
        }
        if(count>5){
            throw new InvalidCountException("Error with number of ships");
        }
        if (enemyTurn()) {
            infoBox("Wait for your turn!", "Play second!");
            enemyMove(shipText, scoreText, shotsText);
        } else {
            infoBox("You start!", "Play first");
        }

        running = true;
    }
    private Stage thisStage;

    /* Close game  */
    private void close() {
        thisStage.close();
    }

    /**
     * method to start the game again
     * @throws Exception
     */
    private void start() throws Exception {
        close();
        Main app = new Main();
        app.start(new Stage());
    }

    /**
     * Method for the pop-up messages
     * @param infoMessage the content of the message
     * @param title of the pop-up window
     */
    private static void infoBox(String infoMessage, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    /**
     * Method to check who won
     */
    /* --- Who won --- */
    private void CheckIfWin() {
        if (PlayerBoard.ships == 0 || ((myShots == 40 && enemyShots == 40) && myScore < enemyScore)) {
            System.out.println("YOU LOSE");
            infoBox("YOU LOSE", "Wasted!");
            enemyTurn = false;
            running = false;
        } else if (EnemyBoard.ships == 0 || ((myShots == 40 && enemyShots == 40) && myScore > enemyScore)) {
            System.out.println("YOU WIN");
            infoBox("YOU WIN", "Victory!");
            enemyTurn = false;
            running = false;
        }
        else if(myShots == 40 && enemyShots == 40){
            System.out.println("DRAWS");
            infoBox("DRAWS", "Draws!");
            enemyTurn = false;
            running = false;
        }
    }

    /**
     * Main method to run the app
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("MediaLab Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
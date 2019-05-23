package comp1110.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Developed by Carry Zhang, inspired by the code from the Game class in Assignment 1
 */

public class Game extends Application {

    /**
     * Dimensions
     */

    private static final int WINDOW_WIDTH = 1440;
    private static final int WINDOW_HEIGHT = 920;
    private static final int BOARD_SIZE = 700;
    private static final int MARGIN = 110;
    private static final int PIECE_SIZE = 100;

    /**
     * Groups for various components
     */

    private final Group root = new Group();
    private final Group display = new Group();
    private final Group grid = new Group();
    private final Group exitSigns = new Group();
    private final Group dice = new Group();
    private final Group specials = new Group();
    private final Group buttons = new Group();
    private final Group score = new Group();
    private final Group theBoard = new Group();

    /**
     * Class Variables
     */

    private int genCounter = 0;
    private int tileCounter;
    private String diceroll;
    private String placementString;
    private ArrayList<String> boardStringArg = new ArrayList<>();
    private String boardString = "";
    private char rowChar;
    private ArrayList<String> pos = new ArrayList<>();

    /* Location of the asset images */

    private static final String PIECE_LOCATION = "comp1110/ass2/gui/assets/";

    /**
     * The Class Dice, which handles drag & drops, as well as placing generated dice on screen
     */

    class Dice extends ImageView {
        double mouseX, mouseY;      // the last known mouse positions
        double startX, startY;
        String piece;
        int row, col;
        int rotation = 0;

        Dice(int ord, String piece) {
            this.piece = piece;
            if (ord < 5) {
            this.startX = MARGIN*2+BOARD_SIZE;
            this.startY = MARGIN+100+(ord*100)+(ord*10);}
            else {
                this.startX = MARGIN*2+BOARD_SIZE+150;
                this.startY = MARGIN+((ord-5)*100)+(ord*10);}
            setLayoutX(startX);
            setLayoutY(startY);
            snapToHome();
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {      // mouse is being dragged
                if (theBoard.getChildren().contains(this)) {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Warning");
                    warning.setHeaderText("Can't Touch This");
                    warning.setContentText("Tiles that are placed on the board can not be moved");
                    warning.showAndWait();
                } else {
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                drag(movementX, movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                setOpacity(0.6);
                }
            });

            setOnMouseReleased(event -> {     // drag is complete
                if (onBoard()) {
                    setPosition();
                    if (RailroadInk.isBoardStringWellFormed(boardString+getPlacementString())) {
                        if (RailroadInk.isValidPlacementSequence(boardString + getPlacementString()) && !(pos.contains(Integer.toString(row) + Integer.toString(col))) && !theBoard.getChildren().contains(this)) {
                            snapToGrid();
                            addToPos();
                            System.out.println(getPlacementString());
                            if (ord < 5) {
                                tileCounter = tileCounter - 1;
                                diceroll = diceroll.replaceFirst(this.piece,"");
                            }
                        } else snapToHome();
                    } else snapToHome();}
                else {
                    Alert warning = new Alert(Alert.AlertType.WARNING);
                    if (!onBoard()) {
                        warning.setTitle("Warning");
                        warning.setHeaderText("Tile not placed on the board");
                        warning.setContentText("Please move the tile to the board");
                        warning.showAndWait();
                        System.out.println((getLayoutY() - MARGIN)/ PIECE_SIZE);
                        System.out.println((getLayoutX() - MARGIN)/ PIECE_SIZE);
                    } else {
                    if (!RailroadInk.isValidPlacementSequence(boardString+getPlacementString())) {
                        System.out.println(boardString);
                        System.out.println(getPlacementString());
                        warning.setTitle("Warning");
                        warning.setHeaderText("Illegal Placement");
                        warning.setContentText("The placement is illegal, please refer to about page");
                        warning.showAndWait();
                    }
                    if (pos.contains(Integer.toString(row) + Integer.toString(col))) {
                        warning.setTitle("Warning");
                        warning.setHeaderText("Duplicated Placement");
                        warning.setContentText("Duplicated placement is not allowed");
                        warning.showAndWait();
                    }
                }
                    snapToHome();
                }
            });
            setOnScroll(event -> {
                if (dice.getChildren().contains(this) || specials.getChildren().contains(this)) {
                SetRotation();
                    System.out.println(boardString);
                }
            }
            );
        }

        /**
         * Move the piece to the new position
         * @param movementX & movementY distance the mouse has moved since its original position
         */

        void drag(double movementX, double movementY) {
            setLayoutX(getLayoutX() + movementX);
            setLayoutY(getLayoutY() + movementY);
        }

        /**
         * Check if the piece is on the board
         * @return true if piece is on board
         */

        private boolean onBoard() {
            return getLayoutX() > (MARGIN) && (getLayoutX() < (MARGIN + BOARD_SIZE))
                    && getLayoutY() > (MARGIN) && (getLayoutY() < (MARGIN + BOARD_SIZE));
        }

        /**
         * Get the path of piece image
         * @param piece 2-character string of the piece
         * @return a path string of the given piece
         */

        String pieceLocation(String piece) {
            return PIECE_LOCATION+piece+".png";
        }

        /**
         * Return the piece to starting position
         */

        private void snapToHome() {
            setImage(new Image(pieceLocation(piece)));
            setLayoutX(startX);
            setLayoutY(startY);
            setRotate(0);
            setFitHeight(PIECE_SIZE);
            setFitWidth(PIECE_SIZE);
            setOpacity(1.0);
        }

        /**
         * Put the piece on the grid
         */

        private void setPosition(){
            row = (int) (getLayoutY() - MARGIN)/ PIECE_SIZE;
            col = (int) (getLayoutX() - MARGIN)/ PIECE_SIZE;
            rowChar = (char) ('A' + row);
        }

        /**
         * Generate the tile placement string
         * @return a string representing the placement
         */

        private String getPlacementString() {
            return (this.piece+rowChar+""+col+rotation);
        }

        /**
         * Update the board string to include the latest placement
         */

        private void updateBoardString() {
            boardStringArg.add(getPlacementString());
            boardString = "";
            for (String s : boardStringArg)
            {
                boardString += s + "";
            }
        }

        /**
         * Put the piece on the grid
         */

        private void snapToGrid() {
            setLayoutX(MARGIN + col * PIECE_SIZE);
            setLayoutY(MARGIN + row * PIECE_SIZE);
            setOpacity(1.0);
            theBoard.getChildren().add(this);
            updateBoardString();
            updateScore(boardString);

        }

        private void SetRotation(){
            rotation = (rotation + 1) % 8;
            if (rotation > 0 && rotation < 4) {
                setRotate (rotation * 90);
            }
            if (rotation == 4) {
                setRotate(0);
                setScaleX(-1);
            }
            if (rotation > 4) {
                setRotate ((rotation-4) * 90);
                setScaleX(-1);
            }
            System.out.println(rotation);
        }

        private void addToPos() {
            pos.add(Integer.toString(row) + Integer.toString(col));
        }
    }


    /**
     * The grid and exits for the Game, as well as the special tiles
     */

    private void makeGrid() {
        for (int i = 0; i <= BOARD_SIZE; i = i + 100) {
            Line x = new Line(0,i,BOARD_SIZE,i);
            Line y = new Line(i,0,i,BOARD_SIZE);
            grid.getChildren().addAll(x,y);
        }
        Line centre1 = new Line(200,200,500,200);
        Line centre2 = new Line(200,200,200,500);
        Line centre3 = new Line(200,500,500,500);
        Line centre4 = new Line(500,200,500,500);
        centre1.setStroke(Color.RED);
        centre2.setStroke(Color.RED);
        centre3.setStroke(Color.RED);
        centre4.setStroke(Color.RED);
        grid.getChildren().addAll(centre1,centre2,centre3,centre4);
        grid.setLayoutX(MARGIN);
        grid.setLayoutY(MARGIN);


        /**
         * Railway and Highway Exits
         */

        int[] HwyExitX = {210, 610, 210, 610, 40, 780};
        int[] HwyExitY = {40, 40, 780, 780, 410, 410};
        int[] RilExitX = {410, 410, 40, 40, 780, 780};
        int[] RilExitY = {40, 780, 210, 610, 210, 610};
        for (int i = 0; i < HwyExitX.length; i++) {
            Image exit = new Image(PIECE_LOCATION+"HighExit"+".png");
            ImageView imgv = new ImageView(exit);
            imgv.setFitHeight(100);
            imgv.setFitWidth(100);
            imgv.setLayoutX(HwyExitX[i]);
            imgv.setLayoutY(HwyExitY[i]);
            if (i > 1) {
                imgv.setScaleY(-1);
            }
            if (i > 3 && i < 5) {
                imgv.setRotate(90);
            }
            if (i == 5) {
                imgv.setRotate(270);
            }
            exitSigns.getChildren().add(imgv);
        }

        for (int i = 0; i < RilExitX.length; i++) {
            Image exit = new Image(PIECE_LOCATION+"RailExit"+".png");
            ImageView imgv = new ImageView(exit);
            imgv.setFitHeight(100);
            imgv.setFitWidth(100);
            imgv.setLayoutX(RilExitX[i]);
            imgv.setLayoutY(RilExitY[i]);
            if (i == 1) {
                imgv.setScaleY(-1);
            }
            if (i == 2 || i == 3) {
                imgv.setRotate(270);
            }
            if (i == 4 || i == 5) {
                imgv.setRotate(90);
            }
            exitSigns.getChildren().add(imgv);
        }

        for (int i = 0; i<6; i++) {
            Dice special = new Dice(5+i,"S"+i);
            specials.getChildren().add(special);
        }
    }

    /**
     * The score display
     */

    private void updateScore(String placement) {
        display.getChildren().clear();
        int score = RailroadInk.getBasicScore(placement);
        Text num = new Text("" + score);
        num.setLayoutX(MARGIN*2+BOARD_SIZE);
        num.setLayoutY(85);
        display.getChildren().addAll(num);
    }

    /**
     * Buttons and various controls
     */

    private void makeControls() {
        Button generate = new Button("Generate");
        generate.setOnAction(event -> {
            if (tileCounter > 0 ) {
                if (!RailroadInk.generateMove(boardString,diceroll).equals("")) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setHeaderText("Remain valid tiles");
                warning.setContentText("All valid tiles must be moved to the board before a new dice can be generated");
                warning.showAndWait();
                    System.out.println(RailroadInk.generateMove(boardString,diceroll));
                }
                else {
                    tileCounter = 4;
                    score.getChildren().clear();
                    dice.getChildren().clear();
                    diceroll = RailroadInk.generateDiceRoll();
                    for (int i = 0; i < 4; i++) {
                        String piece = diceroll.substring(i*2, i*2+2);
                        dice.getChildren().add(new Dice(i, piece));
                    }
                    genCounter++;
                    Text count = new Text("This is round "+genCounter);
                    count.setLayoutX(MARGIN*2+BOARD_SIZE);
                    count.setLayoutY(BOARD_SIZE+MARGIN+20);
                    score.getChildren().add(count);
                }
            } else {
            if (genCounter < 7) {
                tileCounter = 4;
                score.getChildren().clear();
                dice.getChildren().clear();
                diceroll = RailroadInk.generateDiceRoll();
                for (int i = 0; i < 4; i++) {
                    String piece = diceroll.substring(i*2, i*2+2);
                    dice.getChildren().add(new Dice(i, piece));
                }
                genCounter++;
                Text count = new Text("This is round "+genCounter);
                count.setLayoutX(MARGIN*2+BOARD_SIZE);
                count.setLayoutY(BOARD_SIZE+MARGIN+20);
                score.getChildren().add(count);
            } else {
                generate.setDisable(true);
                score.getChildren().clear();
                Text count = new Text("This is the last round");
                count.setLayoutX(MARGIN*2+BOARD_SIZE);
                count.setLayoutY(BOARD_SIZE+MARGIN+20);
                score.getChildren().add(count);
            }
        }
        }
        );
        generate.setLayoutX(MARGIN*2+BOARD_SIZE); // set position for the generate button
        generate.setLayoutY(BOARD_SIZE+MARGIN+50);
        Button clear = new Button("Restart");   // restart the game
        clear.setOnAction(event -> {
            tileCounter = 0;
            genCounter = 0;
            generate.setDisable(false);
            theBoard.getChildren().clear();
            specials.getChildren().clear();
            grid.getChildren().clear();
            exitSigns.getChildren().clear();
            dice.getChildren().clear();
            display.getChildren().clear();
            score.getChildren().clear();
            boardStringArg.clear();
            boardString = "";
            makeGrid();
            pos.clear();
        });
        clear.setLayoutX(MARGIN*2+BOARD_SIZE+100); // set position for the clear button
        clear.setLayoutY(BOARD_SIZE+MARGIN+50);
        Text txt = new Text("Current basic score is: ");
        txt.setLayoutX(MARGIN*2+BOARD_SIZE);
        txt.setLayoutY(70);
        Button about = new Button("About");   // restart the game
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        about.setOnAction(event -> {
            info.setTitle("About");
            info.setHeaderText("About RailroadInk");
            info.setContentText("Developed by Carry Zhang, Qixia Lu and Keyu Liu for the Australian National University for assessment purposes only. \n" +
                    "\n" +
                    "The below texts are extracted from the COMP1110 2019 S1 Assignment 2 README.md file\n" +
                    "\n" +
                    "The objective is to place Tiles representing Highway and Railway routes so as to create a network connecting as many Exits as possible.\n" +
                    "\n" +
                    "The game is played over seven rounds. Each round, the four tile dice are rolled to determine the tiles that may be placed for that round. When placing a tile, it may be flipped or rotated in any direction. All four tiles must be placed, unless doing so would result in an illegal placement After placement is finished, the dice are re-rolled and the next round begins.\n" +
                    "\n" +
                    "In addition to the regular tiles, each round the player may choose to place one of six special tiles, with a maximum of three special tiles per game. The special tiles may also be flipped and rotated.\n" +
                    "\n" +
                    "The game ends at the end of the 7th round.");
            info.showAndWait();
        });
        about.setLayoutX(MARGIN*2+BOARD_SIZE+170); // set position for the info button
        about.setLayoutY(BOARD_SIZE+MARGIN+50);
        buttons.getChildren().addAll(generate,clear,txt,about);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Railroad Link");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        makeGrid();
        makeControls();
        root.getChildren().addAll(grid,display,exitSigns,dice,buttons,specials,score,theBoard);// Adding groups to group root
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

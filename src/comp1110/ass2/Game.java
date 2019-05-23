package comp1110.ass2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    int genCounter = 0;


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
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                drag(movementX, movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                setOpacity(0.8);
            });

            setOnMouseReleased(event -> {     // drag is complete
                if (onBoard()) {
                    setPosition();
                    snapToGrid();
                } else {
                    snapToHome();
                }
            });
            setOnScroll(event -> {
                SetRotation();
            });

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


        /*
        private String getPlacementString() {
            StringBuilder sb = new StringBuilder();
            for (String piecePlacementString : "piecePlacements") {
                sb.append(piecePlacementString);
            }
            return sb.toString();
        }
        */

        private void setPosition(){
            row = (int) (getLayoutY() - MARGIN + 50)/ PIECE_SIZE;
            col = (int) (getLayoutX() - MARGIN + 50)/ PIECE_SIZE;
        }
        /**
         * Put the piece on the grid
         */

        private void snapToGrid() {
            this.setLayoutX(MARGIN + col * PIECE_SIZE);
            this.setLayoutY(MARGIN + row * PIECE_SIZE);
            setOpacity(1.0);
            theBoard.getChildren().add(this);
        }

        private void SetRotation(){
            rotation = (rotation + 1) % 8;
            setRotate (rotation * 90);
            if (rotation >= 4) {
                setScaleX(-1);
            }
        }
    }


    /**
     * The grid and exits for the Game, as well as the special tiles
     */

    void makeGrid() {
        for (int i = 0; i <= BOARD_SIZE; i = i + 100) {
            Line x = new Line(0,i,BOARD_SIZE,i);
            Line y = new Line(i,0,i,BOARD_SIZE);
            grid.getChildren().addAll(x,y);
        }
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

    void displayScore(String placement) {
        int score = RailroadInk.getBasicScore(placement);
        Text txt = new Text("Current score is: ");
        Text num = new Text("" + score);
        txt.setLayoutX(900);
        txt.setLayoutY(70);
        num.setLayoutX(900);
        num.setLayoutY(85);
        display.getChildren().addAll(txt,num);
    }

    /**
     * Buttons and various controls
     */

    private void makeControls() {
        Button generate = new Button("Generate");
        generate.setOnAction(event -> {
            if (genCounter < 7) {
                score.getChildren().clear();
                dice.getChildren().clear();
                String diceroll = RailroadInk.generateDiceRoll();
                for (int i = 0; i < 4; i++) {
                    String piece = diceroll.substring(2 * i, 2 * i + 2);
                    dice.getChildren().add(new Dice(i, piece));
                }
                genCounter++;
                Text count = new Text("This is round "+(genCounter));
                count.setLayoutX(MARGIN*2+BOARD_SIZE);
                count.setLayoutY(BOARD_SIZE+MARGIN+50);
                score.getChildren().add(count);
            } else {
                generate.setDisable(true);
                score.getChildren().clear();
                Text count = new Text("This is the last round");
                count.setLayoutX(MARGIN*2+BOARD_SIZE);
                count.setLayoutY(BOARD_SIZE+MARGIN+50);
                score.getChildren().add(count);
            }
        });
        generate.setLayoutX(MARGIN*2+BOARD_SIZE); // set position for the generate button
        generate.setLayoutY(BOARD_SIZE+MARGIN+50);
        Button clear = new Button("Restart");   // restart the game
        clear.setOnAction(event -> {
            genCounter = 0;
            theBoard.getChildren().clear();
            specials.getChildren().clear();
            grid.getChildren().clear();
            exitSigns.getChildren().clear();
            dice.getChildren().clear();
            makeGrid();
        });
        clear.setLayoutX(MARGIN*2+BOARD_SIZE+100); // set position for the clear button
        clear.setLayoutY(BOARD_SIZE+MARGIN+50);
        buttons.getChildren().addAll(generate,clear);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Railroad Link");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        displayScore(""); // temporary placeholder
        makeGrid();
        makeControls();
        root.getChildren().addAll(grid,display,exitSigns,dice,buttons,specials,score,theBoard);// Adding groups to group root

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

package comp1110.ass2;

import javafx.application.Application;
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

    private static final int WINDOW_WIDTH = 1080;
    private static final int WINDOW_HEIGHT = 920;
    private static final int BOARD_SIZE = 700;
    private static final int MARGIN = 110;
    private static final int PIECE_SIZE = 100;
    Button generate;

    /**
     * Groups for various components
     */

    private final Group root = new Group();
    private final Group display = new Group();
    private final Group grid = new Group();
    private final Group exitSigns = new Group();
    private final Group dice = new Group();

    /* Location of the asset images */

    private static final String PIECE_LOCATION = "comp1110/ass2/gui/assets/";

    /**
     * The Class Dice, which handles drag & drops, as well as placing generated dice on screen
     */

    class Dice extends ImageView {
        double mouseX, mouseY;      // the last known mouse positions
        double startX, startY;
        String piece;
        String PIECE_LOCATION = "comp1110/ass2/gui/assets/" + piece + ".png";

        Dice(int ord, String piece) {
            this.piece = piece;
            this.startX = MARGIN*2+BOARD_SIZE;
            this.startY = MARGIN+100+(ord*100);
            setLayoutX(startX);
            setLayoutY(startY);
            snapToHome();

            setOnMouseDragged(event -> {      // mouse is being dragged
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                drag(movementX, movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });


        }

        void drag(double movementX, double movementY) {
            setLayoutX(getLayoutX() + movementX);
            setLayoutY(getLayoutY() + movementY);
        }

        private boolean onBoard() {
            return getLayoutX() > (MARGIN) && (getLayoutX() < (WINDOW_WIDTH - MARGIN - BOARD_SIZE))
                    && getLayoutY() > (MARGIN) && (getLayoutY() < (WINDOW_HEIGHT - MARGIN));
        }

        private void snapToHome() {
            setImage(new Image(PIECE_LOCATION));
            setLayoutX(startX);
            setLayoutY(startY);
            setRotate(0);
            setFitHeight(PIECE_SIZE);
            setFitWidth(PIECE_SIZE);
            setOpacity(1.0);
        }
    }

    /**
     * The grid and exits for the Game
     */

    void makeGrid() {
        for (int i = 0; i <= BOARD_SIZE; i = i + 100) {
            Line x = new Line(0,i,BOARD_SIZE,i);
            Line y = new Line(i,0,i,BOARD_SIZE);
            grid.getChildren().addAll(x,y);
        }
        grid.setLayoutX(MARGIN);
        grid.setLayoutY(MARGIN);


        /* Railway and Highway exits*/

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
        makeControls();
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
        generate = new Button("Generate Dice Roll");
        generate.setOnAction(event -> {
            dice.getChildren().clear();
            String diceroll = RailroadInk.generateDiceRoll();
            for (int i = 0; i < 4; i++) {
                String piece = diceroll.substring(2 * i, 2 * i + 2);
                dice.getChildren().add(new Dice(i, piece));
            }
        });

        generate.setLayoutX(MARGIN*2+BOARD_SIZE);
        generate.setLayoutY(BOARD_SIZE+MARGIN);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Railroad Link");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        displayScore("");
        makeGrid();
        root.getChildren().addAll(grid,display,exitSigns,dice);// Adding group images to group root

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}

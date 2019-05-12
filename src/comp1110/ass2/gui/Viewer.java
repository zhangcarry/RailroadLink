package comp1110.ass2.gui;

import com.sun.javafx.embed.swing.FXDnD;
import comp1110.ass2.RailroadInk;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A very simple viewer for tile placements in the Railroad Ink game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various tile placements.
 */
public class Viewer extends Application {
    /* In this part we may set the main principles for the game. */
    /* board layout */
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;

    private static final int BOARD_STARTX = 150;
    private static final int BOARD_ENDX = 710;
    private static final int BOARD_STARTY = 100;
    private static final int BOARD_ENDY = 660;
    private static final int TILE_LENGTH = 80;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group imagegroup = new Group();
    private final Group board = new Group();
    private final Group Dice = new Group();

    TextField textField;


    class FXdice extends ImageView {

        String name;
        double homex, homey;
        double mouseX, mouseY;
        int rotation = 0;

        FXdice(String name, int num) {

            this.name = name;
            this.homex = 800;
            this.homey = num * 100;
            snapToHome();

            /**
             *drag the dice
             */
            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });

            setOnMouseDragged(event -> {
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                drag(movementX, movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });

            setOnMouseReleased(event -> {
                if (onBoard()) {
                    setPosition();

                } else snapToHome();
            });

            /**
             * rotate the dice
             */
            setOnScroll(event -> {
                rotate();
            });

            /**
             * scale the dice
             */
            setOnMouseClicked(event -> {
//                scale();
            });


        }

        private void drag(double movementX, double movementY) {
            setLayoutX(getLayoutX() + movementX);
            setLayoutY(getLayoutY() + movementY);
        }

        private boolean onBoard() {
            return getLayoutX() > (BOARD_STARTX - 40) && getLayoutX() < (BOARD_ENDX - 40) && getLayoutY() > (BOARD_STARTY - 40) && getLayoutY() < (BOARD_ENDY - 40);
        }

        private void setPosition() {
            int r = (int) (getLayoutY() - BOARD_STARTY + 40) / 80;
            int c = (int) (getLayoutX() - BOARD_STARTX + 40) / 80;
            char row = (char) ('A' + r);
            setLayoutX(BOARD_STARTX + c * TILE_LENGTH);
            setLayoutY(BOARD_STARTY + r * TILE_LENGTH);

            String re = row + "" + c;
            System.out.println(re);

        }

        private void rotate() {
            rotation = (rotation + 1) % 4;
            setRotate(rotation * 90);
        }

//        private void scale(){
//            if(rotation%2==0){
//                setRotate(rotation*90);
//                setScaleX(1);
//            }
//            if(rotation%2==1){
//                setRotate(rotation*90);
//                setScaleY(1);
//            }
//            rotation = rotation+4;
//        }


        private void snapToHome() {
            setImage(new Image(Viewer.class.getResource(Viewer.URI_BASE + name + ".png").toString()));
            setFitHeight(TILE_LENGTH);
            setFitWidth(TILE_LENGTH);
            setLayoutX(homex);
            setLayoutY(homey);
            setRotate(0);
        }

    }





    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        imagegroup.getChildren().clear();
        int tile1=0, tile2=2;
        int rotate = 0, y = 0;
        int ya = 2, xa = 3, or = 4;


        while (or < placement.length()){
            int i = 1;
            String tile = placement.substring(tile1,tile2);
            char xaxis = placement.charAt(xa);
            char yaxis = placement.charAt(ya);
            char orientation = placement.charAt(or);
            int x = Character.getNumericValue(xaxis) * TILE_LENGTH + BOARD_STARTX;
            tile = tile + ".png";
            y = (yaxis - 'A') * TILE_LENGTH + BOARD_STARTY;
//        switch (yaxis){
//            case 'A':
//                y = 100;
//                break;
//            case 'B':
//                y = 200;
//                break;
//            case 'C':
//                y = 300;
//                break;
//            case 'D':
//                y = 400;
//                break;
//            case 'E':
//                y = 500;
//                break;
//            case 'F':
//                y = 600;
//                break;
//            case 'G':
//                y = 700;
//                break;
//        }
            switch (orientation){
                case '0':
                    rotate = 0;
                    break;
                case '1':
                    rotate = 90;
                    break;
                case '2':
                    rotate = 180;
                    break;
                case '3':
                    rotate = 270;
                    break;
                case '4':
                    i = -1;
                    break;
                case '5':
                    i = -1;
                    rotate = 90;
                    break;
                case '6':
                    i = -1;
                    rotate = 180;
                    break;
                case '7':
                    i = -1;
                    rotate = 270;
                    break;
            }
            StackPane pane = new StackPane();
            imagegroup.getChildren().addAll(pane);
            ImageView tileImage = new ImageView();
            tileImage.setImage(new Image(Viewer.class.getResource(Viewer.URI_BASE + tile).toString()));
            tileImage.setFitHeight(TILE_LENGTH);
            tileImage.setFitWidth(TILE_LENGTH);
            tileImage.setLayoutX(x);
            tileImage.setLayoutY(y);
            tileImage.setScaleX(i);
            tileImage.setRotate(rotate);
            pane.getChildren().add(tileImage);
            imagegroup.getChildren().add(tileImage);
            tile1 +=5;
            tile2 +=5;
            xa += 5;
            ya +=5;
            or += 5;
        }



        // FIXME Task 4: implement the simple placement viewer
    }

    /**
     * Create the board
     */

    public void makeBoard() {

        for (int i = 0; i < 8; i++) {
            Line l = new Line();
            l.setStartX(BOARD_STARTX);
            l.setEndX(BOARD_ENDX);
            l.setStartY(BOARD_STARTY + i * TILE_LENGTH);
            l.setEndY(BOARD_STARTY + i * TILE_LENGTH);
            board.getChildren().add(l);
        }
        for (int i = 0; i < 8; i++) {
            Line l = new Line();
            l.setStartX(BOARD_STARTX + i * TILE_LENGTH);
            l.setEndX(BOARD_STARTX + i * TILE_LENGTH);
            l.setStartY(BOARD_STARTY);
            l.setEndY(BOARD_ENDY);
            board.getChildren().add(l);
        }

        int[] ExithX = {230, 550, 685, 550, 230, 95};
        int[] ExithY = {45, 45, 340, 635, 635, 340};

        for (int i = 0; i < ExithX.length; i++) {
            ImageView Exith = new ImageView();
            Exith.setImage(new Image(Viewer.class.getResource(Viewer.URI_BASE + "HighExit.png").toString()));
            Exith.setFitHeight(TILE_LENGTH);
            Exith.setFitWidth(TILE_LENGTH);
            Exith.setLayoutX(ExithX[i]);
            Exith.setLayoutY(ExithY[i]);
            if (i == 2) {
                Exith.setRotate(90);
            }
            if (i == 3 || i == 4) {
                Exith.setRotate(180);
            }
            if (i == 5) {
                Exith.setRotate(270);
            }
            board.getChildren().add(Exith);
        }

        int[] ExitrX = {390, 685, 685, 390, 95, 95};
        int[] ExitrY = {45, 180, 500, 635, 500, 180};

        for (int i = 0; i < ExitrX.length; i++) {
            ImageView Exitr = new ImageView();
            Exitr.setImage(new Image(Viewer.class.getResource(Viewer.URI_BASE + "RailExit.png").toString()));
            Exitr.setFitHeight(TILE_LENGTH);
            Exitr.setFitWidth(TILE_LENGTH);
            Exitr.setLayoutX(ExitrX[i]);
            Exitr.setLayoutY(ExitrY[i]);
            if (i == 1 || i == 2) {
                Exitr.setRotate(90);
            }
            if (i == 3) {
                Exitr.setRotate(180);
            }
            if (i == 4 || i == 5) {
                Exitr.setRotate(270);
            }
            board.getChildren().add(Exitr);
        }

    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });


        Button roll = new Button("ROLL!");

        roll.setOnAction(e -> {
            String dicestr = RailroadInk.generateDiceRoll();
            System.out.println(dicestr);
            for (int i = 0; i < 4; i++) {
                System.out.println(dicestr.substring(2 * i, 2 * i + 2));
                Dice.getChildren().add(new FXdice(dicestr.substring(2 * i, 2 * i + 2), i + 1));
            }
        });

        roll.setLayoutX(800);
        roll.setLayoutY(VIEWER_HEIGHT - 50);






        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button, roll);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().addAll(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        root.getChildren().add(imagegroup);
        root.getChildren().add(Dice);
        makeBoard();
        root.getChildren().add(board);
        makeControls();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

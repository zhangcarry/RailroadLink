package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

// import javafx.scene.image.Image ; Adding images of placements to the stage.

/**
 * A very simple viewer for tile placements in the Railroad Ink game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various tile placements.
 */

/**
 * Developed by Carry Zhang
 */

public class Viewer extends Application {
    /* board layout */
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;
    private static final int BOARD_SIZE = 700;


    public static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group images = new Group(); // New group for placements
    private final Group controls = new Group();
    private final Group grid = new Group();
    TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        images.getChildren().clear();
        for (int i = 0; i < placement.length(); i = i + 5) { // Now updated for multi-piece support
            String piece = placement.substring(i,i + 2);
            char ori = placement.charAt(i + 4);
            char layOutY = placement.charAt(i + 2);
            char layOutX = placement.charAt(i + 3);
            int oriFX = 0; // Default value for rotation
            int rotateFX = 1; // Default value for the flip, 1 indicates no flipping involved
            int layOutXFX = 0;
            int layOutYFX = 0;
            String PIECE_LOCATION = "comp1110/ass2/gui/assets/" + piece + ".png"; // Defining the location of images
            Image img = new Image(PIECE_LOCATION);
            HBox hb = new HBox();
            ImageView imgV = new ImageView(img);
            imgV.setFitHeight(100); // Defining the Height and Width of the image
            imgV.setFitWidth(100);
            if (layOutX == '1') {
                layOutXFX = 100;
            }
            if (layOutX == '2') {
                layOutXFX = 200;
            }
            if (layOutX == '3') {
                layOutXFX = 300;
            }
            if (layOutX == '4') {
                layOutXFX = 400;
            }
            if (layOutX == '5') {
                layOutXFX = 500;
            }
            if (layOutX == '6') {
                layOutXFX = 600;
            }
            if (layOutY == 'B') {
                layOutYFX = 100;
            }
            if (layOutY == 'C') {
                layOutYFX = 200;
            }
            if (layOutY == 'D') {
                layOutYFX = 300;
            }
            if (layOutY == 'E') {
                layOutYFX = 400;
            }
            if (layOutY == 'F') {
                layOutYFX = 500;
            }
            if (layOutY == 'G') {
                layOutYFX = 600;
            }
            if (ori == '1') {
                oriFX = 90;
            }
            if (ori == '2') {
                oriFX = 180;
            }
            if (ori == '3') {
                oriFX = 270;
            }
            if (ori == '4') {
                rotateFX = -1;
            }
            if (ori == '5') {
                rotateFX = -1;
                oriFX = 90;
            }
            if (ori == '6') {
                rotateFX = -1;
                oriFX = 180;
            }
            if (ori == '7') {
                rotateFX = -1;
                oriFX = 270;
            }
            hb.setLayoutX(layOutXFX);
            hb.setLayoutY(layOutYFX);
            imgV.setScaleX(rotateFX); // The scaling controls for flipping images
            imgV.setRotate(oriFX); // Rotation controls
            hb.getChildren().addAll(imgV); // Adding the ImageView to HBox
            images.getChildren().add(hb); // Adding HBox to the group images
        }
    }

    void makeGrid() {
        for (int i = 0; i <= BOARD_SIZE; i = i + 100) {
            Line x = new Line(0,i,BOARD_SIZE,i);
            Line y = new Line(i,0,i,BOARD_SIZE);
            grid.getChildren().addAll(x,y);
        }
        int[] HwyExitX = {230, 550, 685, 550, 230, 95};
        int[] HwyExitY = {45, 45, 340, 635, 635, 340};
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
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        root.getChildren().addAll(controls,images,grid); // Adding group images to group root

        makeGrid();
        makeControls();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

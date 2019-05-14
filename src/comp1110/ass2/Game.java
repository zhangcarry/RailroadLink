package comp1110.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
    private static final int WINDOW_WIDTH = 1440;
    private static final int WINDOW_HEIGHT = 920;
    private static final int BOARD_SIZE = 700;

    private final Group root = new Group();
    private final Group display = new Group();
    private final Group grid = new Group();
    private final Group dice = new Group();

    private static final String PIECE_LOCATION = "comp1110/ass2/gui/assets/";

    void makeGrid() {
        for (int i = 0; i <= BOARD_SIZE; i = i + 100) {
            Line x = new Line(110,i+110,BOARD_SIZE+110,i+110);
            Line y = new Line(i+110,110,i+110,BOARD_SIZE+110);
            grid.getChildren().addAll(x,y);
        }

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
            grid.getChildren().add(imgv);
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
            grid.getChildren().add(imgv);
        }
    }

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

    void makeControls() {
        Button generate = new Button("Generate Dice Roll");
        generate.setOnAction(e -> {
            dice.getChildren().clear();
            String diceroll = RailroadInk.generateDiceRoll();
            System.out.println(diceroll);
            dice.getChildren().add(generate);
        });
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Railroad Link");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        makeControls();
        displayScore("");
        makeGrid();
        root.getChildren().addAll(grid,display);// Adding group images to group root

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package comp1110.ass2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game extends Application {
    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;

    private final Group root = new Group();
    private final Group display = new Group();

    void displayScore(String placement) {
        int score = RailroadInk.getBasicScore(placement);
        Text num = new Text("Current score is: " + score);
        num.setLayoutX(20);
        num.setLayoutY(700);
        display.getChildren().add(num);
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

        root.getChildren().addAll(); // Adding group images to group root

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

package comp1110.ass2;

import java.util.Arrays;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        String diceroll = RailroadInk.generateDiceRoll();

        for (int i = 0; i < 4; i++) {
            String piece = diceroll.substring(2 * i, 2 * i + 2);
            String PIECE_LOCATION = "comp1110/ass2/gui/assets/" + piece + ".png";
            System.out.println(piece);
            System.out.println(PIECE_LOCATION);
        }
    }
}

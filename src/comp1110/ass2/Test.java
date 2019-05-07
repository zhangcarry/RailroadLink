package comp1110.ass2;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        String boardString = "A4A16B2B14S2C13S5D12S3D25";
        Character [] centrecol = {'3', '4', '5'};
        Character [] centrerow = {'C', 'D', 'E'};
        int centreTiles = 0;
        for (int i = 0; i<boardString.length(); i=i+5) {
            boolean isRowValid = Arrays.asList(centrerow).contains(boardString.charAt(i+2));
            boolean isColValid = Arrays.asList(centrecol).contains(boardString.charAt(i+3));
            if (isRowValid && isColValid) {
                centreTiles++;
            }
        }
        System.out.println(centreTiles);
    }
}

package comp1110.ass2;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.awt.font.FontRenderContext;
import java.util.*;

public class RailroadInk {
    public static boolean isFaceValid(String tilePlacementString) {
        Character [] faceB = {'0', '1', '2'};
        Character [] faceAS = {'0', '1', '2', '3', '4', '5'};
        if (tilePlacementString.charAt(0) == 'B'){
            return (Arrays.asList(faceB).contains(tilePlacementString.charAt(1)));
        }
        else {
            return (Arrays.asList(faceAS).contains(tilePlacementString.charAt(1)));
        }
    }
    /**
     * Determine whether a tile placement string is well-formed:
     * - it consists of exactly 5 characters;
     * - the first character represents a die A or B, or a special tile S
     * - the second character indicates which tile or face of the die (0-5 for die A and special tiles, or 0-2 for die B)
     * - the third character represents the placement row A-G
     * - the fourth character represents the placement column 0-6
     * - the fifth character represents the orientation 0-7
     *
     * @param tilePlacementString a candidate tile placement string
     * @return true if the tile placement is well formed
     */
    public static boolean isTilePlacementWellFormed(String tilePlacementString) {
        // FIXME Task 2: determine whether a tile placement is well-formed
        Character [] row = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        Character [] die = {'A', 'B', 'S'};
        Character [] col = {'0', '1', '2', '3', '4', '5', '6'};
        Character [] ori = {'0', '1', '2', '3', '4', '5', '6', '7'};
        boolean isLengthValid = tilePlacementString.length() == 5;
        if (isLengthValid) {
            boolean isDieValid = Arrays.asList(die).contains(tilePlacementString.charAt(0));
            boolean isRowValid = Arrays.asList(row).contains(tilePlacementString.charAt(2));
            boolean isColValid = Arrays.asList(col).contains(tilePlacementString.charAt(3));
            boolean isOriValid = Arrays.asList(ori).contains(tilePlacementString.charAt(4));
            return (isDieValid && isRowValid && isColValid && isOriValid && isFaceValid(tilePlacementString));
        } else return false;
    }
    /**
     * Determine whether a board string is well-formed:
     * - it consists of exactly N five-character tile placements (where N = 1 .. 31);
     * - each piece placement is well-formed
     * - no more than three special tiles are included
     *
     * @param boardString a board string describing the placement of one or more pieces
     * @return true if the board string is well-formed
     */
    public static boolean isBoardStringWellFormed(String boardString) {
        // FIXME Task 3: determine whether a board string is well-formed
        //boolean noEmpty = false;
        boolean isPeaceFormatted = false;
        boolean isLengthFormatted = false;
        boolean areTilesValid = false;
        int count = 0;

        if ( boardString != "" && boardString != null){
            if (boardString.length() > 0 && boardString.length() <= 31*5 && boardString.length() % 5 == 0){
                isLengthFormatted = true;
            }

            if (isLengthFormatted){
                for (int j = 0; j < boardString.length() - 4; j+=5){
                    if (isTilePlacementWellFormed(boardString.substring(j, j+5))){
                        isPeaceFormatted = true;
                    }
                }
            }

            for (int i = 0; i < boardString.length(); i+=5){
                if (boardString.charAt(i) == 'S'){
                    count++;
                }
            }
            if (count <= 3){
                areTilesValid = true;
            }
            return (isLengthFormatted && isPeaceFormatted && areTilesValid);
        }else{
            return false;
        }

        /*
        boolean isLengthFormatted; // Check if the length is formatted to N five-character sets
        boolean isPieceFormatted; // Check if each set is well-formatted
        boolean areTilesValid; // Check to make sure there are no more than 3 sets with special tiles.
        return false;
        */
    }


    /**
     * Determine whether the provided placements are neighbours connected by at least one validly connecting edge.
     * For example,
     * - areConnectedNeighbours("A3C10", "A3C23") would return true as these tiles are connected by a highway edge;
     * - areConnectedNeighbours("A3C23", "B1B20") would return false as these neighbouring tiles are disconnected;
     * - areConnectedNeighbours("A0B30", "A3B23") would return false as these neighbouring tiles have an
     * invalid connection between highway and railway; and
     * areConnectedNeighbours("A0B30", "A3C23") would return false as these tiles are not neighbours.
     *
     * @return true if the placements are connected neighbours
     */
    //construct a method to replace the similar tiles.
    public static String replace(String a){
        String tile = a.substring(0,2);
        String ori = a.substring(2);
        StringBuilder replace = new StringBuilder(a);
        if (tile.equals("S0")||tile.equals("S1")||tile.equals("B0")){
            if (ori.equals("7")){replace.setCharAt(2, '3');}
            if (ori.equals("6")){replace.setCharAt(2, '2');}
            if (ori.equals("5")){replace.setCharAt(2,'1');}
            if (ori.equals("4")){replace.setCharAt(2,'0');}
        }else if (tile.equals("S2")||tile.equals("S3")){
            replace.setCharAt(2,'0');
        }else if(tile.equals("S4")||tile.equals("A0")||tile.equals("A5")){
            if (ori.equals("7")){replace.setCharAt(2,'0');}
            if (ori.equals("6")){replace.setCharAt(2,'3');}
            if (ori.equals("5")){replace.setCharAt(2,'2');}
            if (ori.equals("4")){replace.setCharAt(2,'1');}
        }else if(tile.equals("S5")||tile.equals("A1")||tile.equals("A4")||tile.equals("B2")){
            if (ori.equals("2")){replace.setCharAt(2,'0');}
            if (ori.equals("3")){replace.setCharAt(2,'1');}
            if (ori.equals("4")){replace.setCharAt(2,'0');}
            if (ori.equals("5")){replace.setCharAt(2,'1');}
            if (ori.equals("6")){replace.setCharAt(2,'0');}
            if (ori.equals("7")){replace.setCharAt(2,'1');}
        }else if(tile.equals("A2")||tile.equals("A3")){
            if (ori.equals("7")){replace.setCharAt(2,'1');}
            if (ori.equals("6")){replace.setCharAt(2,'0');}
            if (ori.equals("5")){replace.setCharAt(2,'3');}
            if (ori.equals("4")){replace.setCharAt(2,'2');}
        }
        return replace.toString();
    }
    //construct a method to determine the position of A and B
    public static String position(String a, String b){
        String position = "Not connected";
        if (a.charAt(0) >= 'A'-1 && b.charAt(0) >= 'A'-1 && a.charAt(0) <= 'G'+1 && b.charAt(0) <= 'G'+1 &&
                a.charAt(1) >= '0'-1 && b.charAt(1) >= '0'-1 && a.charAt(1) <= '6'+1 && b.charAt(1) <= '6'+1){
            if (a.charAt(0)==b.charAt(0)){
                if (a.charAt(1) == b.charAt(1) + 1){
                    position = "A is in the right side of B";
                }else if (a.charAt(1) == b.charAt(1) - 1){
                    position = "A is in the left side of B";
                }
            }else if (a.charAt(1) == b.charAt(1)){
                if (a.charAt(0) == b.charAt(0) + 1){
                    position = "A is in the downside of B";
                }else if(a.charAt(0) == b.charAt(0) - 1){
                    position = "A is in the upside of B";
                }
        }
    } return position;
    }
    public static boolean areConnectedNeighbours(String tilePlacementStringA, String tilePlacementStringB) {
        // FIXME Task 5: determine whether neighbouring placements are connected
        boolean isPositionConnected = false;
        boolean areOriConnected = false;

        //create several String Array to group different types of tiles
        String[] upHighway =
                {"S00", "S01", "S03", "S10", "S20", "S40", "S41", "S50", "A30", "A32", "A33", "A40", "A50", "A51", "B00", "B10", "B14", "B20"};
        String[] downHighway =
                {"S01", "S02", "S03", "S12", "S20", "S42", "S43", "S50", "A30", "A31", "A32", "A40", "A52", "A53", "B02", "B12", "B16", "B20"};
        String[] upRailway =
                {"S02", "S11", "S12", "S13", "S30", "S42", "S43", "S51", "A00", "A01", "A10", "A20", "A22", "A23", "B02", "B13", "B15", "B21"};
        String[] downRailway =
                {"S00", "S10", "S11", "S13", "S30", "S40", "S41", "S51", "A02", "A03", "A10", "A20", "A21", "A22", "B00", "B11", "B17", "B21"};
        String[] rightHighway =
                {"S00", "S01", "S02", "S11", "S20", "S41", "S42", "S51", "A30", "A31", "A33", "A41", "A51", "A52", "B01", "B11", "B15", "B21"};
        String[] leftHighway =
                {"S00", "S02", "S03", "S13", "S20", "S40", "S43", "S51", "A31", "A32", "A33", "A41", "A50", "A53", "B03", "B13", "B17", "B21"};
        String[] rightRailway =
                {"S03", "S10", "S12", "S13", "S30", "S40", "S43", "S50", "A01", "A02", "A11", "A20", "A21", "A23", "B03", "B10", "B16", "B20"};
        String[] leftRailway =
                {"S01", "S10", "S11", "S12", "S30", "S41", "S42", "S50", "A00", "A03", "A11", "A21", "A22", "A23", "B01", "B12", "B14", "B20"};

        String oriTileA = tilePlacementStringA.substring(0, 2) + tilePlacementStringA.substring(4);
        String oriTileB = tilePlacementStringB.substring(0, 2) + tilePlacementStringB.substring(4);
        String oriTileNA = replace(oriTileA);
        String oriTileNB = replace(oriTileB);
        String positionA = tilePlacementStringA.substring(2, 4);
        String positionB = tilePlacementStringB.substring(2, 4);
        //determine whether two tiles are connected successfully
        if (!position(positionA, positionB).equals("Not connected")){
            isPositionConnected = true;
            if (position(positionA, positionB).equals("A is in the right side of B")) {
                for (String a: leftHighway) {
                    for (String b: rightHighway){
                        if(a.equals(oriTileNA)&&b.equals(oriTileNB)){
                            areOriConnected = true;
                        }}}
                for (String a: leftRailway){
                    for (String b: rightRailway){
                        if (a.equals(oriTileNA)&&b.equals(oriTileNB)){
                            areOriConnected = true;
                }}}
            }else if (position(positionA, positionB).equals("A is in the left side of B")){
                for (String a: leftRailway){
                    for (String b: rightRailway){
                        if (a.equals(oriTileNB)&&b.equals(oriTileNA)){
                            areOriConnected = true;
                        }
                    }
                }
                for (String a: leftHighway){
                    for (String b: rightHighway){
                        if (a.equals(oriTileNB)&&b.equals(oriTileNA)){
                            areOriConnected = true;
                        }
                    }
                }
            } else if (position(positionA, positionB).equals("A is in the downside of B")) {
                for (String a: upHighway){
                    for (String b: downHighway){
                        if (a.equals(oriTileNA)&&b.equals(oriTileNB)){
                            areOriConnected = true;
                        }
                    }
                }
                for (String a: upRailway){
                    for (String b: downRailway){
                        if (a.equals(oriTileNA)&&b.equals(oriTileNB)){
                            areOriConnected = true;
                        }
                    }
                }
            } else if (position(positionA, positionB).equals("A is in the upside of B")) {
                for (String a: upHighway){
                    for (String b: downHighway){
                        if (a.equals(oriTileNB)&&b.equals(oriTileNA)){
                            areOriConnected = true;
                        }
                    }
                }
                for (String a: upRailway){
                    for (String b: downRailway){
                        if (a.equals(oriTileNB)&&b.equals(oriTileNA)){
                            areOriConnected = true;
                        }
                    }
                }
        } }
        return isPositionConnected && areOriConnected;
    }

        //return false;

    /**
     * Given a well-formed board string representing an ordered list of placements,
     * determine whether the board string is valid.
     * A board string is valid if each tile placement is legal with respect to all previous tile
     * placements in the string, according to the rules for legal placements:
     * - A tile must be placed such that at least one edge connects to either an exit or a pre-existing route.
     *   Such a connection is called a valid connection.
     * - Tiles may not be placed such that a highway edge connects to a railway edge;
     *   this is referred to as an invalid connection.
     *   Highways and railways may only join at station tiles.
     * - A tile may have one or more edges touching a blank edge of another tile;
     *   this is referred to as disconnected, but the placement is still legal.
     *
     * @param boardString a board string representing some placement sequence
     * @return true if placement sequence is valid
     */
    public static boolean isValidPlacementSequence(String boardString) {
        // FIXME Task 6: determine whether the given placement sequence is valid
        List<String> boards=new ArrayList<>();
        for (int i = 0 ; i < boardString.length(); i=i+5) {
          boards.add(boardString.substring(i,i+5));
        }
        String[] roots={"S2@10","S2@50","S3@30","S3B/0","S3B70","S2D/0","S2D70","S3F/0","S3F70","S2H10","S3H30","S2H50"};
        Set<String> in = new HashSet<>(Arrays.asList(roots));

        for (String board:boards){
          if (((board.charAt(2)=='A'||board.charAt(2)=='G')&&(board.charAt(3)=='1'||board.charAt(3)=='3'||board.charAt(3)=='5'))
            ||((board.charAt(3)=='0'||board.charAt(3)=='6')&&(board.charAt(2)=='B'||board.charAt(2)=='D'||board.charAt(2)=='F'))
          ){
            boolean flage=false;
            for (String i :in){
              if (areConnectedNeighbours(board,i)){
                flage=true;
              }
            }
            if (!flage){
              return false;
            }
          }
        }
        int pre=boards.size();
        int now=-1;
        while (pre!=now){
          pre=boards.size();
          List<String> rm=new ArrayList<>();
          for (String board:boards){
            for (String s: in){
              if (areConnectedNeighbours(s,board)) {
                rm.add(board);
              }
            }
            in.addAll(rm);
          }
          boards.removeAll(rm);
          now=boards.size();
        }
        if (boards.size()!=0){
          return false;
        }
        return true;
    }

    /**
     * Generate a random dice roll as a string of eight characters.
     * Dice A should be rolled three times, dice B should be rolled once.
     * Die A has faces numbered 0-5.
     * Die B has faces numbered 0-2.
     * Each die roll is composed of a character 'A' or 'B' representing the dice,
     * followed by a digit character representing the face.
     *
     * @return a String representing the die roll e.g. A0A4A3B2
     */
    public static String generateDiceRoll() {
        // FIXME Task 7: generate a dice roll
        /*
        Random random = new Random(); // Creating the generator.
        Integer faceA = (random.nextInt(6)); // The generator for die A
        Integer faceB = (random.nextInt(3)); // The generator for die B
        int counta; // Counting how many times die A has been rolled, making sure counta is equal to 3 at the end.
        int countb; // Counting how many times die B has been rolled, making sure counta is equal to 1 at the end.
        */
        return "";
    }

    /**
     * Given the current state of a game board, output an integer representing the sum of all the following factors
     * that contribute to the player's final score.
     * <p>
     * * Number of exits mapped
     * * Number of centre tiles used
     * * Number of dead ends in the network
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for score *not* considering longest rail/highway
     */
    public static int getBasicScore(String boardString) {
        // FIXME Task 8: compute the basic score
        /*
        int exitScores; // Calculate the exit scores in current board.
        int centreTiles; // Number of centre tiles used, also the score should be awarded.
        int deadEnds; // Number of dead ends exists in the game state, the score should be deducted from the game.
        // return (exitScores + centreTiles - deadEnds)
        */
        return -1;
    }

    /**
     * Given a valid boardString and a dice roll for the round,
     * return a String representing an ordered sequence of valid piece placements for the round.
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @param diceRoll a String representing a dice roll for the round
     * @return a String representing an ordered sequence of valid piece placements for the current round
     * @see RailroadInk#generateDiceRoll()
     */
    public static String generateMove(String boardString, String diceRoll) {
        // FIXME Task 10: generate a valid move
        /*
        Random random = new Random(); // Creating the generator.
        boolean formed = isBoardStringWellFormed(boardString); // Check if the boardString input is formed.
        String [] placements; // An array of valid placement for the round.
        String placementsStr; // The string of valid placements.
        */
        return null;
    }

    /**
     * Given the current state of a game board, output an integer representing the sum of all the factors contributing
     * to `getBasicScore`, as well as those attributed to:
     * <p>
     * * Longest railroad
     * * Longest highway
     *
     * @param boardString a board string representing a completed game
     * @return integer (positive or negative) for final score (not counting expansion packs)
     */
    public static int getAdvancedScore(String boardString) {
        // FIXME Task 12: compute the total score including bonus points
        /*
        int longestRailroad; //longestRailroad parameter
        boolean longestRailroadScore; //calculate the bonus score for the longest Railroad
        int longestHighway; //longestHighway parameter
        boolean longestHighwayScore; //calculate the bonus score for the longest highway
        int finalScore; //final scores parameter
        */
        return -1;
    }
}

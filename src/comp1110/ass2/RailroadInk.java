package comp1110.ass2;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.awt.font.FontRenderContext;
import java.util.*;

public class RailroadInk {
    public static boolean isFaceValid(String tilePlacementString) {
        Character [] faceB = {'0', '1', '2'};
        Character [] faceAS = {'0', '1', '2', '3', '4', '5'};
        if (tilePlacementString.charAt(0) == 'B'){
            return (Arrays.asList(faceB).contains(tilePlacementString.charAt(1))); // Specifying different cases for different dies
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
        if (isLengthValid) { // Defining booleans for the requirements
            boolean isDieValid = Arrays.asList(die).contains(tilePlacementString.charAt(0)); // See if the element matches the ones in array using ArrayList
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
public static boolean areConnectedNeighbours(String tilePlacementStringA, String tilePlacementStringB) {
        // FIXME Task 5: determine whether neighbouring placements are connected
        Placement p = new Placement();
        String A = p.replace(tilePlacementStringA);
        String B = p.replace(tilePlacementStringB);
        String position = Board.neighbored(tilePlacementStringA, tilePlacementStringB);
        if (!position.equals("Not Connected")) {
            if (position.equals("A is in the right side of B")) {
                return A.charAt(3) == B.charAt(1) && A.charAt(3) != '0';
            }else if (position.equals("A is in the left side of B")){
                return A.charAt(1) == B.charAt(3) && A.charAt(1) != '0';
            }else if(position.equals("A is in the downside of B")){
                return A.charAt(0) == B.charAt(2) && A.charAt(0) != '0';
            }else if (position.equals("A is in the upside of B")){
                return A.charAt(2) == B.charAt(0) && A.charAt(2) != '0';
            }else{
                return false;
            }
        }else{
            return false;}
    }


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
        Random random = new Random();
        //choose the position for B+number
        int p = random.nextInt(4);
        String[] c = new String[4];
        //to place the random die roll in position
        for (int i = 0; i<=3; i++){
            if (i == p){
                c[i] ="B" + random.nextInt(3);
            }else{
                c[i] = "A" + random.nextInt(6);
            }
        }
        //convert a String array into a String
        String a = "";
        for (String i: c){
             a += i;
        }
        return a;
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

        int score = centreTiles + getExitScore(boardString) - getDeadEndScore(boardString);
        return score;
    }

    /**
     * @param boardString a board string representing a completed game
     * @return integer (positive) for exit score
     */
    public static int getExitScore(String boardString) {
        int exits = 0;
        int exit = 0;
        String [] exitHwyTop = {"A2", "A6"};
        String [] exitHwyBtm = {"G2", "G6"};
        String [] exitHwyLft = {"D1"};
        String [] exitHwyRgt = {"D7"};
        String [] exitRilTop = {"A4"};
        String [] exitRilBtm = {"G4"};
        String [] exitRilLft = {"B1", "F1"};
        String [] exitRilRgt = {"B7", "F7"};
        Placement p = new Placement();
        for (int i = 0; i<boardString.length(); i=i+5) {
            String plm = boardString.substring(i,i+5);
            boolean isTopHwyExit = Arrays.asList(exitHwyTop).contains(plm.substring(2,4));
            boolean isBtmHwyExit = Arrays.asList(exitHwyBtm).contains(plm.substring(2,4));
            boolean isLftHwyExit = Arrays.asList(exitHwyLft).contains(plm.substring(2,4));
            boolean isRgtHwyExit = Arrays.asList(exitHwyRgt).contains(plm.substring(2,4));
            boolean isTopRilExit = Arrays.asList(exitRilTop).contains(plm.substring(2,4));
            boolean isBtmRilExit = Arrays.asList(exitRilBtm).contains(plm.substring(2,4));
            boolean isLftRilExit = Arrays.asList(exitRilLft).contains(plm.substring(2,4));
            boolean isRgtRilExit = Arrays.asList(exitRilRgt).contains(plm.substring(2,4));
            String plmE = p.replace(plm);
            boolean isValidTopHwyExit = plmE.charAt(0) == 'H';
            boolean isValidBtmHwyExit = plmE.charAt(2) == 'H';
            boolean isValidLftHwyExit = plmE.charAt(3) == 'H';
            boolean isValidRgtHwyExit = plmE.charAt(1) == 'H';
            boolean isValidTopRilExit = plmE.charAt(0) == 'R';
            boolean isValidBtmRilExit = plmE.charAt(2) == 'R';
            boolean isValidLftRilExit = plmE.charAt(3) == 'R';
            boolean isValidRgtRilExit = plmE.charAt(1) == 'R';
            if (isTopHwyExit && isValidTopHwyExit) {
                exits++;
            }
            if (isBtmHwyExit && isValidBtmHwyExit) {
                exits++;
            }
            if (isLftHwyExit && isValidLftHwyExit) {
                exits++;
            }
            if (isRgtHwyExit && isValidRgtHwyExit) {
                exits++;
            }
            if (isTopRilExit && isValidTopRilExit) {
                exits++;
            }
            if (isBtmRilExit && isValidBtmRilExit) {
                exits++;
            }
            if (isLftRilExit && isValidLftRilExit) {
                exits++;
            }
            if (isRgtRilExit && isValidRgtRilExit) {
                exits++;
            }
        }
        for (int i = 1;i<exits;i++) {
            exit = exit + 4;
        }
        if (exits == 12) {
            exit++;
        }
        return exit;
    }

    public static int getDeadEndScore(String boardString) {
        int deadEnds = 0;
        Placement p = new Placement();
        boolean onEdge = false;
        for (int i = 0; i<boardString.length(); i=i+5) {
            String plm = boardString.substring(i, i + 5);
            boolean onLftEdge = plm.charAt(3) == '1';
            boolean onRgtEdge = plm.charAt(3) == '7';
            boolean onTopEdge = plm.charAt(2) == 'A';
            boolean onBtmEdge = plm.charAt(2) == 'G';
            boolean isA1 = plm.substring(2, 4).equals("A1");
            boolean isA7 = plm.substring(2, 4).equals("A7");
            boolean isG1 = plm.substring(2, 4).equals("G1");
            boolean isG7 = plm.substring(2, 4).equals("G7");
            String plmE = p.replace(plm);
        }
        return deadEnds;
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

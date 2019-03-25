package comp1110.ass2;

import java.util.Arrays;
import java.util.Random;

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
        boolean isLengthFormatted; // Check if the length is formatted to N five-character sets
        boolean isPieceFormatted; // Check if each set is well-formatted
        boolean areTilesValid; // Check to make sure there are no more than 3 sets with special tiles.
        return false;
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
        boolean isPositionConnected; // Check if either two placements' row or column are the same, but not both.
        boolean areTypesValid; // Check if highway pieces are connected to highway pieces, not railway pieces.
        boolean areOriConnected; // Check if orientations' of both pieces can indeed be connected.
        return false;
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
        boolean isConnectToExit; //to check whether the placement connect to Exit
        boolean isExitConnectionValid; //to check whether the placement connects to Exit successfully
        boolean isConnectToRoute; //to check whether the placement connects to the route
        boolean isValidConnection; //to check whether it is a valid connection
        boolean aredisconnectedLegal; //check whether the placements are connected in the legal way.
        return false;
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
        Random random = new Random(); // Creating the generator.
        Integer faceA = (random.nextInt(6)); // The generator for die A
        Integer faceB = (random.nextInt(3)); // The generator for die B
        int counta; // Counting how many times die A has been rolled, making sure counta is equal to 3 at the end.
        int countb; // Counting how many times die B has been rolled, making sure counta is equal to 1 at the end.
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
        int exitScores; // Calculate the exit scores in current board.
        int centreTiles; // Number of centre tiles used, also the score should be awarded.
        int deadEnds; // Number of dead ends exists in the game state, the score should be deducted from the game.
        // return (exitScores + centreTiles - deadEnds)
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
        Random random = new Random(); // Creating the generator.
        boolean formed = isBoardStringWellFormed(boardString); // Check if the boardString input is formed.
        String [] placements; // An array of valid placement for the round.
        String placementsStr; // The string of valid placements.
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
        int longestRailroad; //longestRailroad parameter
        boolean longestRailroadScore; //calculate the bonus score for the longest Railroad
        int longestHighway; //longestHighway parameter
        boolean longestHighwayScore; //calculate the bonus score for the longest highway
        int finalScore; //final scores parameter
        return -1;
    }
}

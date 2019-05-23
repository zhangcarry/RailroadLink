package comp1110.ass2;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.awt.font.FontRenderContext;
import java.lang.reflect.Array;
import java.util.*;

public class RailroadInk {
    static private String[] S = {"HHRH", "HRRR", "HHHH", "RRRR", "HRRH", "HRHR"};
    static private String[] A = {"RNNR", "RNRN", "RRRN", "HHHN", "HNHN", "HNNH"};
    static private String[] B = {"HNRN", "HRNN", "HRHR"};
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

    /**
     * Developed by Carry Zhang
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

    /**
     * Developed by Qixia Lu
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

    /**
     * Developed by Qixia Lu
     */

    public static boolean areConnectedNeighbours(String tilePlacementStringA, String tilePlacementStringB) {
        // FIXME Task 5: determine whether neighbouring placements are connected
        Placement a = new Placement();
        String A = a.replace(tilePlacementStringA);
        String B = a.replace(tilePlacementStringB);
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

    /**
     * Developed by Keyu Liu
     */

    public static boolean isValidPlacementSequence(String boardString) {
        // FIXME Task 6: determine whether the given placement sequence is valid
        List<String> boards = new LinkedList<>();
        for (int i = 0; i < boardString.length(); i = i + 5) {
            boards.add(boardString.substring(i, i + 5));
        }
        String[] roots = {"S2@10", "S2@50", "S3@30", "S3B/0", "S3B70", "S2D/0", "S2D70", "S3F/0", "S3F70", "S2H10", "S3H30", "S2H50"};
        Set<String> in = new HashSet<>(Arrays.asList(roots));
        boards.addAll(Arrays.asList(roots));
        List<String> checkValid = new ArrayList<>(boards);
        //判断不合法的连接
        for (int i = 0; i < checkValid.size(); i++) {
            for (int j = i + 1; j < checkValid.size(); j++) {
                if (Math.abs(checkValid.get(i).charAt(2) - checkValid.get(j).charAt(2)) == 0 &&
                        Math.abs(checkValid.get(i).charAt(3) - checkValid.get(j).charAt(3)) == 0
                ) {
                    return false;
                }
                if (!areConnectedNeighbours(checkValid.get(i), checkValid.get(j))
                        && Math.abs(checkValid.get(i).charAt(2) - checkValid.get(j).charAt(2))
                        + Math.abs(checkValid.get(i).charAt(3) - checkValid.get(j).charAt(3)) == 1) {
                    int a2Tb2 = checkValid.get(i).charAt(2) - checkValid.get(j).charAt(2);
                    int a3Tb3 = checkValid.get(i).charAt(3) - checkValid.get(j).charAt(3);
                    int aDirection = 0;
                    if (a2Tb2 > 0) {
                        aDirection = 0;
                    } else if (a2Tb2 < 0) {
                        aDirection = 2;
                    } else if (a3Tb3 > 0) {
                        aDirection = 3;
                    } else {
                        aDirection = 1;
                    }
                    char aType = getDirectionType(checkValid.get(i), aDirection);
                    char bType = getDirectionType(checkValid.get(j), (2 + aDirection) % 4);
                    if (aType != bType && aType != 'N' && bType != 'N') {
                        return false;
                    }
                }
            }
        }
        //判断合法连接
        boards.removeAll(Arrays.asList(roots));
        int pre = boards.size();
        int now = -1;
        while (pre != now) {
            pre = boards.size();
            List<String> rm = new LinkedList<>();
            for (String board : boards) {
                for (String s : in) {
                    if (areConnectedNeighbours(s, board)) {
                        rm.add(board);
                    }
                }
                in.addAll(rm);
            }
            boards.removeAll(rm);
            now = boards.size();
        }
        if (boards.size() != 0) {
            return false;
        }
        return true;
    }
    private static char getDirectionType(String board, Integer direction) {
        Map<Character, String[]> map = new HashMap<>();
        map.put('S', S);
        map.put('A', A);
        map.put('B', B);
        String string = map.get(board.charAt(0))[Integer.parseInt(String.valueOf(board.charAt(1)))];
        if (board.charAt(4) >= '4') {
            string = String.valueOf(string.charAt(0)) + string.charAt(3) + string.charAt(2) + string.charAt(1);
        }
        return string.charAt((8 + direction - Integer.parseInt(String.valueOf(board.charAt(4)))) % 4);
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

    /**
     * Developed by Qixia Lu
     */

    public static String generateDiceRoll() {
        // FIXME Task 7: generate a dice roll
        String[] c = new String[4];
        for (int i = 0; i<= 3; i++){
            if (i == 3){
                c[i] = 'B' + String.valueOf((int)(Math.random()*3));
            }else{
                c[i] = 'A' + String.valueOf((int)(Math.random()*6));
            }
        }
        String a = "";
        for (int i = 0; i<=3; i++){
            a = a.concat(c[i]);
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

    /**
     * Developed by Qixia Lu
     */

    public static int getBasicScore (String boardString) {
        // FIXME Task 8: compute the basic score
        ArrayList<String> tiles = new ArrayList<>();
        List<List<String>> route = new LinkedList<>();
        for (int i = 0; i<=boardString.length()-5; i+=5){
            tiles.add(boardString.substring(i, i+5));
        }
        //compute the centre score
        int centreScore = 0;
        for (String i: tiles){
            if((i.charAt(2) >= 'C' && i.charAt(2) <= 'E')
                    && (i.charAt(3) >= '2' && i.charAt(3) <= '4')){
                centreScore++;
            }
        }
        //find B2 (not connect to exists) and divide it into A1 and A4
        for(int i = 0; i<tiles.size(); i++){
            if (!connectExists(tiles.get(i))){
                String BA1;
                String BA4;
                int count =0;
                if (tiles.get(i).substring(0,2).equals("B2")){
                    String b2 = tiles.get(i);
                    for (String a: tiles){
                        if (areConnectedNeighbours(a, b2)){
                            count++;
                        }
                    }
                    if (count == 4){
                        BA4 = "A4".concat(b2.substring(2));
                        if (b2.charAt(4) % 2 == 0){
                            BA1 = "A1".concat(b2.substring(2, 4)).concat("1");
                        }else{
                            BA1 = "A1".concat(b2.substring(2,4)).concat("0");
                        }
                        tiles.remove(b2);
                        tiles.add(BA1);
                        tiles.add(BA4);
                    }
                }
            }
        }
        //compute routes (start from the tile connected to exist)
        ArrayList<String> usedExists = new ArrayList<>();
        for (int i =0; i<tiles.size(); i++){
            String e = tiles.get(i);
            if (connectExists(e) && !usedExists.contains(e)){
                List<String> tile = new LinkedList<>();
                List<String> result = new LinkedList<>();
                usedExists.add(e);
                tile.add(e);
                List<String> tiles1 = new ArrayList<>(tiles);
                while(!tile.isEmpty()){
                    String t = tile.remove(0);
                    result.add(t);
                    for (int m = 0; m<tiles1.size(); m++){
                        String t1 = tiles1.get(m);
                        if (areConnectedNeighbours(t, t1)){
                            tiles1.remove(t1);
                            m--;
                            tile.add(t1);
                            if (connectExists(t1)){
                                usedExists.add(t1);
                                result.remove(t1);
                            }
                        }
                    }
                }
                route.add(result);
            }
        }
        //compute exists score
        int endScore = 0;
        int countEnds = 0;
        for (int i = 0; i< route.size(); i++){
            for (String t: route.get(i)){
                if (connectExists(t)){
                    countEnds ++;
                }
                //eliminate the situation when B2 connects to the exist
                int count = 0;
                if (t.substring(0,2).equals("B2")){
                    for (String a: tiles){
                        if (areConnectedNeighbours(t, a)){
                            count++;
                        }
                    }
                    if (count == 3){
                        countEnds --;
                    }
                }
            }
            if (countEnds == 12){
                endScore += 45;
            }else{
                endScore += countEnds*4-4;
            }
            countEnds = 0;
        }
        //compute dead ends according to whether all the sides of tile have been connected
        int badScore = 0;
        for (String t: tiles){
            char pR = t.charAt(2);
            char pL = t.charAt(3);
            int count = 0;
            if (t.charAt(0) == 'S' || t.substring(0, 2).equals("B2")){
                for (String a: tiles){
                    if (areConnectedNeighbours(t, a)){
                        count ++;
                    }
                }
                if (count!=4){
                    count = deadEnds(t, pL, pR, count);
                    badScore += count - 4;
                }
            }
            String die = t.substring(0,2);
            if (die.equals("A2") || die.equals("A3")){
                for (String a: tiles){
                    if (areConnectedNeighbours(t, a)){
                        count ++;
                    }
                }
                if (count != 3){
                    count = deadEnds(t, pL, pR, count);
                    badScore += count -3;
                }
            }
            String[] die2 = {"A0", "A1", "A4", "A5", "B0", "B1"};
            for (int j = 0; j< die2.length; j++){
                if (die.equals(die2[j])){
                    for (String a: tiles){
                        if (areConnectedNeighbours(t, a)){
                            count ++;
                        }
                    }
                    if (count != 2){
                        count = deadEnds(t, pL, pR, count);
                        badScore += count -2;
                    }
                }
            }
        }
        return centreScore + endScore + badScore;
    }
    //compute the number of sides which have connected to edge of the board
    public static int deadEnds (String t, char pL, char pR, int count){
        Placement p = new Placement();
        if (pL == '0' || pL == '6' || pR == 'A' || pR == 'G'){
            //use the replace method to find whether the tile side has touched the edge
            if (pL == '0'){
                if (p.replace(t).charAt(3) != '0'){
                    count++;
                }
            }
            if (pL == '6'){
                if (p.replace(t).charAt(1) != '0'){
                    count++;
                }
            }
            if (pR == 'A'){
                if (p.replace(t).charAt(0) != '0'){
                    count++;
                }
            }
            if (pR == 'G'){
                if (p.replace(t).charAt(2) != '0'){
                    count++;
                }
            }
        }
        return count;
    }
    //find the tiles connecting to the exists
    public static boolean connectExists(String tile){
        String[] exists = {"A1", "A3", "A5", "B0", "D0", "F0", "G1", "G3", "G5", "B6", "D6", "F6"};
        for (int i = 0; i<exists.length; i++){
            if (tile.substring(2,4).equals(exists[i])){
                return true;
            }
        }
        return false;
    }


    /**
     * Given a valid boardString and a dice roll for the round,
     * return a String representing an ordered sequence of valid piece placements for the round.
     * @param boardString a board string representing the current state of the game as at the start of the round
     * @param diceRoll a String representing a dice roll for the round
     * @return a String representing an ordered sequence of valid piece placements for the current round
     * @see RailroadInk#generateDiceRoll()
     * Developed by Keyu Liu
     */
    public static String generateMove(String boardString, String diceRoll) {
        // FIXME Task 10: generate a valid move
        //map to save exits status
        //example: {0H1H} means two exits in direction of up and right with both are highway
        String exit = "S2@10S2@50S3@30S3B/0S3B70S2D/0S2D70S3F/0S3F70S2H10S3H30S2H50" + boardString;
        Map<String, String> boardStates = new HashMap<>();
        Map<String, String> boardPosition = new HashMap<>();
        for (int i = 0; i < exit.length(); i = i + 5) {
            String board = exit.substring(i, i + 5);
            boardPosition.put(board.substring(2, 4), board);
            boardStates.put(board, "");
        }
        int[] dirY = {0, 1, 0, -1};
        int[] dirX = {-1, 0, 1, 0};
        for (int i = 0; i < exit.length(); i = i + 5) {
            String board = exit.substring(i, i + 5);
            for (int j = 0; j < 4; j++) {
                char direcX = (char) (board.charAt(2) + dirX[j]);
                char direcY = (char) (board.charAt(3) + dirY[j]);
                if (boardPosition.get("" + direcX + direcY) == null
                        && direcX <= 'G' && direcX >= 'A'
                        && direcY <= '6' && direcY >= '0'
                        && getDirectionType(board, j) != 'N'
                ) {
                    String change = boardStates.get(board) + j + getDirectionType(board, j);
                    boardStates.put(board, change);
                }
            }
        }
        StringBuilder result = new StringBuilder();
        boolean change = true;
        while (change) {
            change = false;
            Set<String> boardSet = new HashSet<>(boardStates.keySet());
            for (String board : boardSet) {
                String connect = boardStates.get(board);
                if (!"".equals(connect)) {
                    for (int j = 0; j < connect.length(); j = j + 2) {
                        String directionAndType = connect.substring(j, j + 2);
                        int[] indexToRemove = new int[8];
                        for (int i = 0; i < diceRoll.length(); i = i + 2) {
                            String dice = diceRoll.substring(i, i + 2);
                            int dir = (2 + Integer.parseInt(String.valueOf(directionAndType.charAt(0)))) % 4;
                            int oldDir = Integer.parseInt(String.valueOf(directionAndType.charAt(0)));
                            for (int d = 0; d < 8; d++) {
                                //good position and orientation
                                if (getDirectionType(dice + "A0" + d, dir) == directionAndType.charAt(1)) {
                                    String newBoard = dice + (char) (board.charAt(2) + dirX[oldDir]) + (char) (board.charAt(3) + dirY[oldDir]) + d;
                                    if (newBoard.charAt(2) <= 'G' && newBoard.charAt(2) >= 'A'
                                            && newBoard.charAt(3) <= '6' && newBoard.charAt(3) >= '0'
                                            && isValidPlacementSequence(boardString + newBoard)
                                            && boardPosition.get(newBoard.substring(2, 4)) == null) {
                                        boardString = boardString + newBoard;
                                        indexToRemove[i] = 1;
                                        change = true;
                                        boardStates.put(newBoard, "");
                                        result.append(newBoard);
                                        boardPosition.put(newBoard.substring(2, 4), newBoard);
                                        for (int k = 0; k < 4; k++) {
                                            char direcX = (char) (newBoard.charAt(2) + dirX[k]);
                                            char direcY = (char) (newBoard.charAt(3) + dirY[k]);
                                            String boardToChange = boardPosition.get("" + direcX + direcY);
                                            if (boardToChange != null) {
                                                String quekou = boardStates.get(boardToChange);
                                                int position = quekou.indexOf("" + (2 + Integer.parseInt(String.valueOf(k))) % 4);
                                                if (position != -1) {
                                                    quekou = quekou + quekou.substring(0, position) + quekou.substring(position + 2);
                                                    boardStates.put(boardToChange, quekou);
                                                }
                                            }
                                            else if (direcX <= 'G' && direcX >= 'A'
                                                    && direcY <= '6' && direcY >= '0'
                                                    && getDirectionType(newBoard, k) != 'N') {
                                                boardStates.put(newBoard, boardStates.get(newBoard) + k + getDirectionType(newBoard, k));

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for (int i = 0; i < indexToRemove.length; i++) {
                            if (indexToRemove[i] == 1) {
                                diceRoll = diceRoll.substring(0, i) + diceRoll.substring(i + 2);
                            }
                        }
                    }
                }
            }
        }
        return result.toString();
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
     * Developed by Keyu Liu
     */
    private static List<List<String>> getRoads(String boardString, String map) {
        for (int j = 0; j < boardString.length(); j = j + 5) {
            if ("B2".equals(boardString.substring(j, j + 2))) {
                String subB2 = "A4" + boardString.substring(j + 2, j + 5) + "A1" + boardString.substring(j + 2, j + 4) + (Integer.parseInt(boardString.substring(j + 4, j + 5)) + 1);
                boardString = boardString.substring(0, j) + boardString.substring(j + 5) + subB2;
                j = j - 5;
            }
        }
        String bigMap = map + boardString;
        //used exits
        List<String> usedExit = new LinkedList<>();
        //all results
        List<List<String>> results = new LinkedList<>();
        //start connecting from exit
        for (int i = 0; i < map.length(); i = i + 5) {
            String exit = map.substring(i, i + 5);
            //if not used
            if (!usedExit.contains(exit)) {
                Stack<String> line = new Stack<>();
                //
                usedExit.add(exit);
                line.push(exit);
                //map include exits
                String bigMapTemp = bigMap;
                List<String> result = new LinkedList<>();
                //keep looping until no more possible result
                while (!line.empty()) {
                    String front = line.pop();
                    result.add(front);
                    for (int j = i + 5; j < bigMapTemp.length(); j = j + 5) {
                        String board = bigMapTemp.substring(j, j + 5);
                        if (areConnectedNeighbours(front, board)) {
                            bigMapTemp = bigMapTemp.substring(0, j) + bigMapTemp.substring(j + 5);
                            j = j - 5;
                            line.push(board);
                            if (map.contains(board)) {
                                usedExit.add(board);
                            }
                        }
                    }
                }
                results.add(result);
            }
        }
        return results;
    }

    public static int getAdvancedScore(String boardString) {
        // FIXME Task 12: compute the total score including bonus points
        String map = "S2@10S2@50S3@30S3B/0S3B70S2D/0S2D70S3F/0S3F70S2H10S3H30S2H50";
        String pointsType = "S0S1S4S5B0B1";
        StringBuilder checkPoint = new StringBuilder(map);
        for (int i = 0; i < boardString.length(); i = i + 5)
            if (pointsType.contains(boardString.substring(i, i + 2)))
                checkPoint.append(boardString.substring(i, i + 5));
        List<List<String>> roads = getRoads(boardString, map);
        int[] dirY = {0, 1, 0, -1};
        int[] dirX = {-1, 0, 1, 0};
        List<Integer> maxH = new LinkedList<>();
        List<Integer> maxR = new LinkedList<>();
        maxH.add(0);
        maxR.add(0);
        Map<String, String> allMap = new HashMap<>();
        for (List<String> road : roads) {
            for (String r : road) {
                if (allMap.get(r.substring(2, 4)) == null)
                    allMap.put(r.substring(2, 4), r + "0000");
                else {
                    if ("A1".equals(r.substring(0, 2))) {
                        allMap.put(allMap.get(r.substring(2, 4)).substring(2,4) + 'H', allMap.get(r.substring(2, 4)));
                        allMap.put(allMap.get(r.substring(2, 4)).substring(2,4) + 'R', r + "0000");
                    } else {
                        allMap.put(allMap.get(r.substring(2, 4)).substring(2,4) + 'R', allMap.get(r.substring(2, 4)));
                        allMap.put(allMap.get(r.substring(2, 4)).substring(2,4) + 'H', r + "0000");
                    }
                    allMap.remove(r.substring(2, 4));
                }
            }
        }
        char type;
        char[] types = {'H', 'R'};
        for (int ti = 0; ti < types.length; ti++) {
            type = types[ti];
            for (int fi = 0; fi < checkPoint.length(); fi=fi+5) {
                int finnalMax = 0;
                for (List<String> road : roads) {
                    StringBuilder firstRoad = new StringBuilder(checkPoint.substring(fi, fi + 5) + "0000");
                    if (road.contains(firstRoad.substring(0, 5))) {
                        List<StringBuilder> allRoads = new LinkedList<>();
                        allRoads.add(firstRoad);
                        boolean change = true;
                        int totalMax = 0;
                        while (change) {
                            change = false;
                            List<StringBuilder> allRoadsOld = new LinkedList<>(allRoads);
                            for (StringBuilder oneRoad : allRoadsOld) {
                                String current = oneRoad.substring(oneRoad.length() - 9);
                                boolean needNewRoad = false;
                                for (int i = 0; i < 4; i++) {
                                    char direcX = (char) (current.charAt(2) + dirX[i]);
                                    char direcY = (char) (current.charAt(3) + dirY[i]);
                                    String connectState = allMap.get("" + direcX + direcY);
                                    if (connectState == null) {
                                        connectState = allMap.get("" + direcX + direcY + type);
                                    }
                                    if (connectState != null) {
                                        int alreadyHasIt = oneRoad.toString().indexOf(connectState.substring(0, 5));
                                        if (alreadyHasIt > 0) {
                                            connectState = oneRoad.substring(alreadyHasIt, alreadyHasIt + 9);
                                        }
                                    }
                                    if (connectState != null
                                            && areConnectedNeighbours(connectState.substring(0, 5), current.substring(0, 5))
                                            && current.charAt(5 + i) == '0'
                                            && connectState.charAt(5 + (2 + i) % 4) == '0'
                                            && getDirectionType(current.substring(0, 5), i) == type
                                    ) {
                                        change = true;
                                        String newCurrent = current.substring(0, 5 + i) + 1 + current.substring(6 + i);
                                        String newConnect = connectState.substring(0, 5 + (2 + i) % 4) + 1 + connectState.substring(6 + (2 + i) % 4);
                                        current = newCurrent;
                                        if (!needNewRoad) {
                                            oneRoad.delete(oneRoad.length() - 9, oneRoad.length());
                                            needNewRoad = true;
                                            oneRoad.append(newCurrent);
                                            oneRoad.append(newConnect);
                                        } else {
                                            StringBuilder newRoad = new StringBuilder(oneRoad);
                                            newRoad.delete(oneRoad.length() - 18, oneRoad.length());
                                            newRoad.append(newCurrent);
                                            newRoad.append(newConnect);
                                            allRoads.add(newRoad);
                                        }
                                    }
                                }
                                int maxLength = 0;
                                for (StringBuilder stringBuilder : allRoads) {
                                    int currentRoadLength = 0;
                                    for (int i = 0; i < stringBuilder.length(); i = i + 9)
                                        if (!map.contains(stringBuilder.subSequence(i, i + 5)))
                                            currentRoadLength++;
                                    if (maxLength < currentRoadLength)
                                        maxLength = currentRoadLength;
                                }
                                if (totalMax < maxLength) {
                                    totalMax = maxLength;
                                }
                            }
                        }
                        if (totalMax > finnalMax)
                            finnalMax = totalMax;
                        if (type == 'H') maxH.add(finnalMax);
                        else maxR.add(finnalMax);
                    }
                }
            }
        }
        return getBasicScore(boardString)+Collections.max(maxH)+Collections.max(maxR);
    }
}


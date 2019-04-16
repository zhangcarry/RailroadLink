package comp1110.ass2;

public class Board {
    //find the position of two tiles
    public String neighbored(String a, String b){
        String position = "Not connected";
        if (a.charAt(2) >= 'A'-1 && b.charAt(2) >= 'A'-1 && a.charAt(2) <= 'G'+1 && b.charAt(2) <= 'G'+1 &&
                a.charAt(3) >= '0'-1 && b.charAt(3) >= '0'-1 && a.charAt(3) <= '6'+1 && b.charAt(3) <= '6'+1){
            if (a.charAt(2)==b.charAt(2)){
                if (a.charAt(3) == b.charAt(3) + 1){
                    position = "A is in the right side of B";
                }else if (a.charAt(3) == b.charAt(3) - 1){
                    position = "A is in the left side of B";
                }
            }else if (a.charAt(3) == b.charAt(3)){
                if (a.charAt(2) == b.charAt(2) + 1){
                    position = "A is in the downside of B";
                }else if(a.charAt(2) == b.charAt(2) - 1){
                    position = "A is in the upside of B";
                }
            }
        }
        return position;
    }
}




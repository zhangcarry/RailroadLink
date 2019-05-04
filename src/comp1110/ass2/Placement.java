package comp1110.ass2;

import java.util.HashMap;

public class Placement {
    //name each tile
    public HashMap<String, String> map = new HashMap<>();
    Placement() {
        map.put("S0", "HHRH");
        map.put("S1", "HRRR");
        map.put("S2", "HHHH");
        map.put("S3", "RRRR");
        map.put("S4", "HRRH");
        map.put("S5", "HRHR");
        map.put("A0", "R00R");
        map.put("A1", "R0R0");
        map.put("A2", "RRR0");
        map.put("A3", "HHH0");
        map.put("A4", "H0H0");
        map.put("A5", "H00H");
        map.put("B0", "H0R0");
        map.put("B1", "HR00");
        map.put("B2", "HRHR");
        map.put("HE", "H000");
        map.put("RE", "R000");
    }

    //rotate tile
    public String replace(String a){
        String State = map.get(a.substring(0,2));
        StringBuilder replace = new StringBuilder(State);
        char indexUp = State.charAt(0);
        char indexRight = State.charAt(1);
        char indexDown = State.charAt(2);
        char indexLeft = State.charAt(3);
        switch (a.charAt(4)){
            case '1':
                replace.setCharAt(0, indexLeft);//0-3
                replace.setCharAt(1, indexUp);//1-0
                replace.setCharAt(2, indexRight);//2-1
                replace.setCharAt(3, indexDown);//3-2
                break;
            case '2':
                replace.setCharAt(0, indexDown); //0-2
                replace.setCharAt(1, indexLeft);//1-3
                replace.setCharAt(2, indexUp);//2-0
                replace.setCharAt(3, indexRight);//3-1
                break;
            case '3':
                replace.setCharAt(0, indexRight);//0-1
                replace.setCharAt(1, indexDown);//1-2
                replace.setCharAt(2, indexLeft);//2-3
                replace.setCharAt(3, indexUp);//3-0
                break;
            case '4':
                replace.setCharAt(1, indexLeft);
                replace.setCharAt(3, indexRight);
                break;
            case '5':
                replace.setCharAt(0, indexRight); // 0-1
                replace.setCharAt(1, indexUp); //1-0
                replace.setCharAt(2, indexLeft);//2-3
                replace.setCharAt(3, indexDown);//3-2
                break;
            case '6':
                replace.setCharAt(0, indexDown); // 0-2
                replace.setCharAt(2, indexUp);
                break;
            case '7':
                replace.setCharAt(0, indexLeft); //0 - 3
                replace.setCharAt(1, indexDown); //1-2
                replace.setCharAt(2, indexRight);
                replace.setCharAt(3, indexUp);
                break;
        }
        return replace.toString();
    }
}
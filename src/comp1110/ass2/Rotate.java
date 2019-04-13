package comp1110.ass2;

public class Rotate {
    private String State;

    public String rotate(String a, String State){
        StringBuilder replace = new StringBuilder(State);
        char indexUp = State.charAt(0);
        char indexRight = State.charAt(1);
        char indexDown = State.charAt(2);
        char indexLeft = State.charAt(3);
        switch (a.charAt(4)){
            case '1':
                replace.setCharAt(0, indexLeft);
                replace.setCharAt(1, indexUp);
                replace.setCharAt(2, indexRight);
                replace.setCharAt(3, indexDown);
                break;
            case '2':
                replace.setCharAt(0, indexDown);
                replace.setCharAt(1, indexLeft);
                replace.setCharAt(2, indexUp);
                replace.setCharAt(3, indexRight);
                break;
            case '3':
                replace.setCharAt(0, indexRight);
                replace.setCharAt(1, indexDown);
                replace.setCharAt(2, indexLeft);
                replace.setCharAt(3, indexUp);
                break;
            case '4':
                replace.setCharAt(1, indexLeft);
                replace.setCharAt(3, indexRight);
                break;
            case '5':
                replace.setCharAt(0, indexRight);
                replace.setCharAt(1, indexUp);
                replace.setCharAt(2, indexLeft);
                replace.setCharAt(3, indexDown);
            case '6':
                replace.setCharAt(0, indexDown);
                replace.setCharAt(2, indexUp);
                break;
            case '7':
                replace.setCharAt(0, indexLeft);
                replace.setCharAt(1, indexDown);
                replace.setCharAt(2, indexRight);
                replace.setCharAt(3, indexUp);
                break;
        }
        return replace.toString();
    }
}

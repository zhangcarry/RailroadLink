package comp1110.ass2;

public class test {
    public static void main(String[] args) {
        RailroadInk r = new RailroadInk();
        String b = "A3D61, A3D53, B0C52, A0B52, A2B63, A4D41, B0E60, A0F61, A3D31, A3D23, A2G30, B0F34, A3E32, A1B01, B2B10, A1B21, A0A63, A4D01, A1G41, B0G12, S2D10, A4C10, B2A10, A2B33, A1A30, S4E11, A4E21, A3C21, A3C31";
        String c = b.replace(", ", "");
        System.out.println(c);
        boolean d = RailroadInk.isValidPlacementSequence(c);
        System.out.println(d);
    }

}

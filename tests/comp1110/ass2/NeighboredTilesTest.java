package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

public class NeighboredTilesTest {

    @Rule
    public Timeout globalTimeout = Timeout.millis(5000);

    @Test
    public void testValidLine(){
        for (String[] vR: validLineR){
            String i = Board.neighbored(vR[0], vR[1]);
            assertTrue("Board.Neighbored(" + vR[0] + ", " + vR[1] + ") expected " + "A is in the right side of B" + " but returned " + i,
                    i.equals("A is in the right side of B"));
        }
        for (String[] vL: validLineL){
            String i = Board.neighbored(vL[0], vL[1]);
            assertTrue("Board.Neighbored(" + vL[0] + ", " + vL[1] + ") expected " + "A is in the left side of B" + " but returned " + i,
                    i.equals("A is in the left side of B"));
        }
    }

    @Test
    public void testValidRow(){
        for (String[] vD: validRowD){
            String i = Board.neighbored(vD[0], vD[1]);
            assertTrue("Board.Neighbored(" + vD[0] + ", " + vD[1] + ") expected " + "A is in the downside of B" + " but returned " + i,
                    i.equals("A is in the downside of B"));
        }
        for (String[] vU: validRowU){
            String i = Board.neighbored(vU[0], vU[1]);
            assertTrue("Board.Neighbored(" + vU[0] + ", " + vU[1] + ") expected " + "A is in the upside of B" + " but returned " + i,
                    i.equals("A is in the upside of B"));
        }
    }

    @Test
    public void testDisconnected(){
        for (String[] dC: invalid){
            String i = Board.neighbored(dC[0], dC[1]);
            assertTrue("Board.Neighbored(" + dC[0] + ", " + dC[1] + ") expected " + "Not connected" + " but returned " + i,
                    i.equals("Not connected"));
        }
    }


    private static final String[][] validLineR = {
            {"A3B13", "B0B01"},
            {"S3C51", "S4C43"},
            {"B1G23", "A3G10"},
            {"A3A12", "A5A02"}
    };
    private static final String[][] validLineL = {
            {"A3B03", "B0B11"},
            {"S3C41", "S4C53"},
            {"B1G13", "A3G20"},
            {"A3A02", "A5A12"}
    };
    private static final String[][] validRowU = {
            {"A4A10", "A3B10"},
            {"S1B37", "B0C32"},
            {"S2D40", "S5E46"},
            {"S5E46", "A4F42"}
    };
    private static final String[][] validRowD = {
            {"A4B10", "A3A10"},
            {"S1C37", "B0B32"},
            {"S2E40", "S5D46"},
            {"S5F46", "A4E42"}
    };
    private final String[][] invalid = {
            {"A4A10", "A3B20"},
            {"A4A00", "A3B13"},
            {"A3C13", "A4B21"},
            {"A3B13", "B0A01"}
    };
}


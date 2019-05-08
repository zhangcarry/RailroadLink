//package comp1110.ass2;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.Timeout;
//
//import static org.junit.Assert.assertTrue;
//
//public class GetCentreScoreTest {
//
//    @Rule
//    public Timeout globalTimeout = Timeout.millis(2000);
//
//    @Test
//    public void testNoCentreTiles() {
//        int num = RailroadInk.getCentreScore("A1A30A0B30A5A11B1B20S4A23");
//        assertTrue("Expected centre tiles score of 0, but method returned " + num, num == 0);
//    }
//
//    @Test
//    public void testLongString() {
//        int num = RailroadInk.getCentreScore("A0B22B1A61A4D11A4G10B1G44A2G30A3C01A3C12B0B31A1B01A4B50B0C50A2F32A0E32A0E40A4D31B1D47A1B11B0D61A2D50A3G50A4G61");
//        assertTrue("Expected centre tiles score of 6, but method returned " + num, num == 6);
//    }
//}

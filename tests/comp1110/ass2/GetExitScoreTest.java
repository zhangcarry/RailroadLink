package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;


public class GetExitScoreTest {


    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testNoExit() {
            int num = RailroadInk.getExitScore("A3C10A3C23");
            assertTrue("Expected no exit score, but method returned " + num, num == 0);
    }

    @Test
    public void testSingleExit() {
        int num = RailroadInk.getExitScore("A3A20");
        assertTrue("Expected no exit score, but method returned " + num, num == 0);
    }

    @Test
    public void testTwoExits() {
        int num = RailroadInk.getExitScore("A4A20B0A42");
        assertTrue("Expected exit score of 4, but method returned " + num, num == 4);
    }


}

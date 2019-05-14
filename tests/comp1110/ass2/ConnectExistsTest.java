
package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Developed by Qixia Lu
 */

public class ConnectExistsTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void testConnected(){
        for (String a: connected){
            boolean test = RailroadInk.connectExists(a);
            assertTrue(a + " tile has connected to exist, but got " + test, test);
        }
    }

    @Test
    public void testUnConnected(){
        for (String a: unConnected){
            boolean test = RailroadInk.connectExists(a);
            assertFalse(a + " tile hasn't connected to exist, but got " + test, test);
        }
    }

    private static String[] connected =
            {"A3A10", "A1A30", "A3A50", "A1B01", "A4D01", "A1F01", "A3G10", "A1G30", "A3G50", "A1B61", "A4D61", "A1F61"};
    private static String[] unConnected =
            {"A3A20", "A1A41", "A5A61", "A2B12", "A4D21", "S1F11"};
}

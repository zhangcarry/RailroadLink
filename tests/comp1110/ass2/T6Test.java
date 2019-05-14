package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertTrue;

/**
 * Developed by Qixia Lu
 */

public class T6Test {
    @Rule
    public Timeout globalTimeout = Timeout.millis(2000);

    @Test
    public void isConnectionValid() {
        for (String[] invalid : VALIDPIECES) {
            testExpected(invalid[0], invalid[1], true);
        }
    }

    @Test
    public void isConnectionInvalid() {
        testTrivialCorrect();
        for (String[] invalid : INVALIDPIECES) {
            testExpected(invalid[0], invalid[1], false);
        }
    }

    @Test
    public void isConnectionDisconnected() {
        testTrivialCorrect();
        for (String[] invalid : DISCONNECTEDPIECES) {
            testExpected(invalid[0], invalid[1], false);
        }
    }

    @Test
    public void isNotNeighbours() {
        testTrivialCorrect();
        for (String[] invalid : NOT_NEIGHBOURSPIECES) {
            testExpected(invalid[0], invalid[1], false);
        }
    }

    static final String[][] VALIDPIECES = {
            {"B1B11", "A0C11"},
            {"A0C11", "A0C20"},
			{"A5B12", "B1C10"},
			{"A0C23", "A0D21"}
    };

    static final String[][] INVALIDPIECES = {
            {"A0B30", "A3B23"}
    };

    static final String[][] DISCONNECTEDPIECES = {
            {"A4E41", "B0D32"},
			{"A3C23", "B1B20"}
    };

    static final String[][] NOT_NEIGHBOURSPIECES = {
            {"A0B30", "A3C23"}
    };

    private void testTrivialCorrect() {
        testExpected("A3C10", "A3C23", true);
    }

    private void testExpected(String p1, String p2, boolean predicte) {
        boolean result = RailroadInk.areConnectedNeighbours(p1, p2);
        assertTrue("RailroadInk.areConnectedNeighbours(" + p1 + ", " + p2 + ") predicted " + predicte + " but responsed " + result, predicte == result);

    }
}

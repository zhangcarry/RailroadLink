package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static junit.framework.TestCase.assertTrue;

public class ReplaceTilesTest {
    Placement a = new Placement();
    @Rule
    public Timeout globalTimeout = Timeout.millis(500);
    /**
     * testEmpty; testGood;
     * testBad;
     */
    @Test
    public void test0(){
        for(String i: r0){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected String remain same", temp.equals(temp1));
        }
    }
    @Test
    public void test90 (){
        for (String i: r90){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            for(int j = 0; j < 4; j++){
                int b;
                if (j<1){
                    b = j+3;
                }else{
                    b = j-1;
                }
                char tempc = temp.charAt(j);
                char ic = temp1.charAt(b);
                assertTrue("Expected rotate 90 degrees", tempc == ic);
            }
        }
    }
    @Test
    public void test180(){
        for (String i: r180){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            for(int j = 0; j<4; j++){
                char ic = temp1.charAt(j);
                int b;
                if (j<2){
                    b = j +2;
                }else{
                    b = j -2;
                }
                char tempc = temp.charAt(b);
                assertTrue("Expected rotate 180 degrees", tempc == ic);
            }
        }
    }
    @Test
    public void test270(){
        for (String i: r270){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            for(int j = 0; j<4; j++){
                int b;
                if (j<3){
                    b = j+1;
                }else{
                    b = j-3;
                }
                char tempc = temp.charAt(j);
                char ic = temp1.charAt(b);
                assertTrue("Expected rotate 270 degrees", tempc == ic);
            }
        }
    }
    @Test
    public void testc0(){
        for (String i: c0){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected left equals right side", temp.charAt(1) == temp1.charAt(3));
        }
    }
    @Test
    public void testc90(){
        for (String i: c90){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals right side", temp.charAt(0) == temp1.charAt(1));
            assertTrue("Expected down equals left side", temp.charAt(2) == temp1.charAt(3));
        }
    }
    @Test
    public void testc180(){
        for (String i: c180){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals down side", temp.charAt(0) == temp1.charAt(2));
        }
    }
    @Test
    public void testc270(){
        for (String i: c270){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals left side", temp.charAt(0) == temp1.charAt(3));
            assertTrue("Expected down equals right side", temp.charAt(1) == temp1.charAt(2));
        }
    }

    static final String[] r0 = {
            "A4A10", "B2G50", "S3F40"
    };
    static final String[] r90 = {
            "A4B21", "B0A01", "S0C51"
    };
    static final String[] r180 = {
            "B0C32", "A4F42", "A0F32"
    };
    static final String[] r270 = {
            "A3B13", "B1C33", "S0E33"
    };
    static final String[] c0 = {
            "A5D34", "B1E44", "S4C34"
    };
    static final String[] c90 = {
            "S0B35", "B1G25", "A5A05"
    };
    static final String[] c180 = {
            "A5A06", "B1B06", "S4A06"
    };
    static final String[] c270 = {
            "S1B57", "B0E47", "A4B27"
    };
}

package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ReplaceTilesTest {
    Placement a = new Placement();
    //generate a new die
    String[] die = {"A" + (int)(Math.random()*6), "B" + (int)(Math.random()*3), "S" + (int)(Math.random()*6)};
    String[] row = {"A", "B", "C", "D", "E", "F", "G"};


    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    @Test
    public void test0(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "0";
        //for (String i: r0){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertEquals(temp, temp1);
        //}
    }
    @Test
    public void test90 (){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "1";
        //for (String i: r90){
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
                assertTrue("Expected rotate 90 degrees, but got" + tempc, tempc == ic);
            }
        //}
    }
    @Test
    public void test180(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "2";
        //for (String i: r180){
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
                assertTrue("Expected rotate 180 degrees, but got" + tempc, tempc == ic);
            }
        //}
    }
    @Test
    public void test270(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "3";
        //for (String i: r270){
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
                assertTrue("Expected rotate 270 degrees, but got" + tempc, tempc == ic);
            }
        //}
    }
    @Test
    public void testc0(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "4";
        //for (String i: c0){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected left equals right side, but got" + temp.charAt(1) + ", not equal to " + temp1.charAt(3),
                    temp.charAt(1) == temp1.charAt(3));
        //}
    }
    @Test
    public void testc90(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "5";
        //for (String i: c90){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals right side, but got" + temp.charAt(0) + ", not equal to " + temp1.charAt(1),
                    temp.charAt(0) == temp1.charAt(1));
            assertTrue("Expected down equals left side, but got" + temp.charAt(2) +  ", not equal to " + temp1.charAt(1),
                    temp.charAt(2) == temp1.charAt(3));
        //}
    }
    @Test
    public void testc180(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "6";
        //for (String i: c180){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals down side, but got" + temp.charAt(0) +"not equal to" + temp1.charAt(2),
                    temp.charAt(0) == temp1.charAt(2));
        //}
    }
    @Test
    public void testc270(){
        String i = die[(int)(Math.random()*3)] + row[(int)(Math.random()*7)] + (int)(Math.random()*7) + "7";
        //for (String i: c270){
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals left side, but got" + temp.charAt(0) + ", not equal to " + temp1.charAt(3),
                    temp.charAt(0) == temp1.charAt(3));
            assertTrue("Expected down equals right side, but got" + temp.charAt(1) + ", not equal to " + temp1.charAt(2),
                    temp.charAt(1) == temp1.charAt(2));
        //}
    }

//    static final String[] r0 = {
//            "A4A10", "B2G50", "S3F40"
//    };
//    static final String[] r90 = {
//            "A4B21", "B0A01", "S0C51"
//    };
//    static final String[] r180 = {
//            "B0C32", "A4F42", "A0F32"
//    };
//    static final String[] r270 = {
//            "A3B13", "B1C33", "S0E33"
//    };
//    static final String[] c0 = {
//            "A5D34", "B1E44", "S4C34"
//    };
//    static final String[] c90 = {
//            "S0B35", "B1G25", "A5A05"
//    };
//    static final String[] c180 = {
//            "A5A06", "B1B06", "S4A06"
//    };
//    static final String[] c270 = {
//            "S1B57", "B0E47", "A4B27"
//    };
}
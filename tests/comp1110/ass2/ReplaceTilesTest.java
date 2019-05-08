package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ReplaceTilesTest {
    Placement a = new Placement();
    //different types of dies:
    String[] dies = {"S0", "S1", "S2", "A0", "A1", "A2", "A3", "A4", "A5", "B0", "B1", "B2"};


    @Rule
    public Timeout globalTimeout = Timeout.millis(500);

    @Test
    public void test0(){
        for (String d: dies){
            //to create a valid tile with 0 rotation.
            String i = d +"A1"+"0";
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertEquals(temp, temp1);
        }
    }
    @Test
    public void test90 (){
        for (String d: dies){
            String i = d + "A1" +"1";
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
                assertTrue("Expected rotate 90 degrees, but got" + tempc + " not equals to " + ic,
                        tempc == ic);
            }
        }
    }
    @Test
    public void test180(){
        for (String d: dies){
            String i = d + "A1" +"2";
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
        }
    }
    @Test
    public void test270(){
        for (String d: dies){
            String i = d + "A1" + "3";
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
        }
    }
    @Test
    public void testc0(){
        for (String d: dies){
            String i = d + "A1" + "4";
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected left equals right side, but got" + temp.charAt(1) + ", not equal to " + temp1.charAt(3),
                    temp.charAt(1) == temp1.charAt(3));
        }
    }
    @Test
    public void testc90(){
        for (String d: dies){
            String i = d + "A1" + "5";
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals right side, but got" + temp.charAt(0) + ", not equal to " + temp1.charAt(1),
                    temp.charAt(0) == temp1.charAt(1));
            assertTrue("Expected down equals left side, but got" + temp.charAt(2) +  ", not equal to " + temp1.charAt(1),
                    temp.charAt(2) == temp1.charAt(3));
        }
    }
    @Test
    public void testc180(){
        for (String d: dies){
            String i = d + "A1" + "6";
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals down side, but got" + temp.charAt(0) +"not equal to" + temp1.charAt(2),
                    temp.charAt(0) == temp1.charAt(2));
        }
    }
    @Test
    public void testc270(){
        for (String d: dies){
            String i = d + "A1" + "7";
            String index = i.substring(0, 4).concat("0");
            String temp1 = a.replace(index);
            String temp = a.replace(i);
            assertTrue("Expected up equals left side, but got" + temp.charAt(0) + ", not equal to " + temp1.charAt(3),
                    temp.charAt(0) == temp1.charAt(3));
            assertTrue("Expected down equals right side, but got" + temp.charAt(1) + ", not equal to " + temp1.charAt(2),
                    temp.charAt(1) == temp1.charAt(2));
        }
    }
}
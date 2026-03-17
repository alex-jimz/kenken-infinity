package LogicTier.Verifier;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.*;
import static org.junit.Assert.assertFalse;
import LogicTier.TestRunner.TestRunner;

public class VerifierTest{

    public static void main(String[] args) {
        TestRunner.runTestClass(VerifierTest.class);
    }

    @Test
    public void testVerify() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("1 1 2 1 1 1 2");
        testInput.add("1 1 2 2 1 2 2");


        boolean result = verifier.verify(testInput);
        assertTrue("Expected true for valid input", result);
    }
    @Test
    public void testCorrectInputwithBrackets() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("1 1 2 1 1 [1] 1 2 [5]");
        testInput.add("1 1 2 2 1 [4] 2 2 [7]");

        boolean result = verifier.verify(testInput);
        assertTrue("Expected true for valid input", result);
    }

    @Test
    public void testIncorrectInputWithBrackets() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("1 [2] 1 2 1 1 1 2 [5]");
        testInput.add("1 1 2 2 [2] 1 [4] 2 2 [7]");
        boolean result = verifier.verify(testInput);
        assertFalse("Expected false for invalid input", result);
    }

    @Test
    public void testVerifyInvalidInput() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");

        testInput.add("1 1 2 1 1 1 2");
        testInput.add("1 1 2 2 1 2a 2");  // Invalid input: 'a' is not a number

        boolean result = verifier.verify(testInput);
    
        assertFalse("Expected false for invalid input", result);
    }

    @Test
    public void testVerifyInvalidHeader() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("24 2 3 2 4");
        testInput.add("1 1 2 1 1 1 2");
        testInput.add("1 1 2 2 1 2 2");
        
        boolean result = verifier.verify(testInput);
        assertFalse("Expected false for invalid header", result);
    }

    @Test
    public void testVerifyWrongRegNumber() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 4");
        testInput.add("1 1 2 1 1 1 2");
        testInput.add("1 1 2 2 1 2 2");

        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false for wrong number of regions", result);
        
    }

    @Test
    public void testVerifyInvalidCellNumberInRegion() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("3 2");
        testInput.add("1 1 5 1 1 1 2 1 3 2 1 2 2 2 3");
        testInput.add("1 1 3 3 1 3 2 3 3");


        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false for region 1 should only have 5 cells not 6", result);
    }

    @Test
    public void testVerifyOutOfBoundsCoordinate() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("1 1 2 1 1 12 2");
        testInput.add("1 1 2 2 13 2 2");

        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false out of bounds cell coordinate", result);
        
    }

    @Test
    public void testVerifyRegionNotConnex() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 4");
        testInput.add("1 1 2 1 1 2 2");
        testInput.add("1 1 2 2 1 1 2");

        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false because netiher region 1 or 2 is connex", result);
        
    }

    @Test
    public void testVerifyRepeatedCoordinate() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("1 1 2 1 1 1 2");
        testInput.add("1 1 2 2 1 1 2");

        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false because cell 1,2 is repeated in region 1 and 2", result);
    }

    @Test
    public void testVerifyOperationNotValid(){
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("kenken 1 2 1 1 1 2");
        testInput.add("1 1 2 2 1 2 2");

        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false because kenken is not a valid operation Id", result);
    }

    @Test
    public void testVerifyNotAllCellsCovered() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("2 2");
        testInput.add("1 1 1 1 1");
        testInput.add("1 1 2 2 1 2 2");

        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false because not all cells are covered", result);
    }

    @Test   
    public void testDivisionNumberOfCellsIncorrect() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("3 2");
        testInput.add("4 1 6 1 1 1 2 1 3 2 1 2 2 2 3");
        testInput.add("1 1 3 3 1 3 2 3 3");


        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false because the max number of cells in a division region is 2", result);



        Vector<String> testInput2 = new Vector<>();
        testInput2.add("2 2");
        testInput2.add("4 1 2 1 1 1 2");
        testInput2.add("1 1 2 2 1 2 2");

        boolean result2 = verifier.verify(testInput2);
        assertTrue( "Expected true because the division region is size 2 ", result2);
    }

    @Test
    public void testSubstractionNumberOfCells() {
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("3 2");
        testInput.add("2 1 6 1 1 1 2 1 3 2 1 2 2 2 3");
        testInput.add("1 1 3 3 1 3 2 3 3");


        boolean result = verifier.verify(testInput);
        assertFalse( "Expected false because the max number of cells in a division region is 2", result);


        Vector<String> testInput2 = new Vector<>();
        testInput2.add("2 2");
        testInput2.add("2 1 2 1 1 1 2");
        testInput2.add("1 1 2 2 1 2 2");

        boolean result2 = verifier.verify(testInput2);
        assertTrue( "Expected true because the substraction region is size 2 ", result2);
    }

    @Test 

    public void InputTest1x1() {
    
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("1 1");
        testInput.add("0 1 1 1 1");
        boolean result = verifier.verify(testInput);
        assertTrue("Expected true for valid input", result);

    }

    @Test 
     public void InputTest6x6(){
        Verifier verifier = new Verifier();

        Vector<String> testInput = new Vector<>();
        testInput.add("6 15");
        testInput.add("1 11 2 1 1 2 1");
        testInput.add("4 2 2 1 2 1 3");
        testInput.add("3 20 2 1 4 2 4");
        testInput.add("3 6 4 1 5 1 6 2 6 3 6");
        testInput.add("2 3 2 2 2 2 3");
        testInput.add("4 3 2 2 5 3 5");
        testInput.add("3 240 4 3 1 3 2 4 1 4 2");
        testInput.add("3 6 2 3 3 3 4");
        testInput.add("3 6 2 4 3 5 3");
        testInput.add("1 7 3 4 4 5 4 5 5");
        testInput.add("3 30 2 4 5 4 6");
        testInput.add("3 6 2 5 1 5 2");
        testInput.add("1 9 2 5 6 6 6");
        testInput.add("1 8 3 6 1 6 2 6 3");
        testInput.add("4 2 2 6 4 6 5");

        boolean result = verifier.verify(testInput);

        assertTrue("Expected true for valid input", result);


     }

     @Test
     public void InputTest6x6wHint(){

        Verifier verifier = new Verifier();
        Vector<String> testInput = new Vector<>();
        testInput.add("6 14");
        testInput.add("2 5 2 1 1 2 1 [5]");
        testInput.add("1 6 3 1 2 1 3 [7] 2 2");
        testInput.add("3 24 5 1 4 1 5 2 5 2 6 1 6");
        testInput.add("2 1 2 2 3 2 4 [3]");
        testInput.add("1 9 3 3 1 4 1 5 1");
        testInput.add("1 15 3 3 2 [2] 4 2 5 2");
        testInput.add("1 12 4 3 3 3 4 4 3 4 4");
        testInput.add("2 1 2 3 5 3 6");
        testInput.add("2 5 2 4 5 4 6");
        testInput.add("2 2 2 5 3 5 4");
        testInput.add("0 5 1 6 1");
        testInput.add("2 4 2 6 2 6 3");
        testInput.add("1 10 3 5 5 6 4 6 5");
        testInput.add("4 2 2 5 6 6 6");

        boolean result = verifier.verify(testInput);

        assertTrue("Expected true for valid input", result);



     }
     @Test
     public void InputTest3x3(){

        Verifier verifier = new Verifier();
        Vector<String> testInput = new Vector<>();
        testInput.add("3 5");
        testInput.add("1 3 2 1 1 1 2");
        testInput.add("1 3 2 2 3 3 3");
        testInput.add("1 7 3 2 1 2 2 3 2");
        testInput.add("0 3 1 1 3");
        testInput.add("0 2 1 3 1");

        boolean result = verifier.verify(testInput);

        assertTrue("Expected true for valid input", result);

     }
    
        @Test
        public void InputTest9x9() {
            Verifier verifier = new Verifier();
            Vector<String> testInput = new Vector<>();
            testInput.add("9 35");
            testInput.add("1 23 4 1 1 2 1 3 1 4 1");
            testInput.add("3 210 4 1 2 1 3 2 2 2 3");
            testInput.add("4 4 2 1 4 2 4");
            testInput.add("4 3 2 1 5 2 5");
            testInput.add("2 2 2 1 6 1 7");
            testInput.add("2 8 2 1 8 1 9");
            testInput.add("2 1 2 2 6 2 7");
            testInput.add("4 4 2 2 8 2 9");
            testInput.add("0 3 1 3 2");
            testInput.add("2 2 2 3 3 4 3");
            testInput.add("2 5 2 3 4 4 4");
            testInput.add("1 3 2 3 5 4 5");
            testInput.add("1 21 4 3 6 3 7 3 8 3 9");
            testInput.add("4 2 2 4 2 5 2");
            testInput.add("3 126 3 4 6 4 7 5 7");
            testInput.add("2 5 2 4 8 4 9");
            testInput.add("2 8 2 5 1 6 1");
            testInput.add("2 3 2 5 3 5 4");
            testInput.add("2 7 2 5 5 5 6");
            testInput.add("2 2 2 5 8 6 8");
            testInput.add("4 3 2 5 9 6 9");
            testInput.add("1 17 2 6 2 6 3");
            testInput.add("3 150 3 6 4 7 4 7 5");
            testInput.add("3 24 3 6 5 6 6 6 7");
            testInput.add("3 252 4 7 1 8 1 9 1 9 2");
            testInput.add("1 3 2 7 2 7 3");
            testInput.add("3 18 2 7 6 8 6");
            testInput.add("1 15 3 7 7 7 8 7 9");
            testInput.add("2 3 2 8 2 8 3");
            testInput.add("2 1 2 8 4 8 5");
            testInput.add("1 17 2 8 7 8 8");
            testInput.add("2 8 2 9 3 9 4");
            testInput.add("0 7 1 9 5");
            testInput.add("1 13 2 9 6 9 7");
            testInput.add("1 12 3 8 9 9 8 9 9");
            
            boolean result = verifier.verify(testInput);
            assertTrue("Expected true for valid input", result);
        }

        

            @Test
            public void testInput9x9withHints() {
            Verifier verifier = new Verifier();
            Vector<String> testInput = new Vector<>();
            testInput.add("9 35");
            testInput.add("3 432 4 1 1 2 1 3 1 3 2 [3]");
            testInput.add("4 4 2 1 2 1 3");
            testInput.add("0 2 1 1 4");
            testInput.add("1 11 2 1 5 [9] 1 6");
            testInput.add("1 19 3 1 7 2 7 2 6");
            testInput.add("2 2 2 1 8 2 8");
            testInput.add("2 1 2 1 9 2 9");
            testInput.add("2 1 2 2 2 2 3");
            testInput.add("4 3 2 2 4 2 5");
            testInput.add("2 7 2 3 3 3 4");
            testInput.add("2 3 2 3 5 3 6");
            testInput.add("3 210 3 3 7 3 8 3 9");
            testInput.add("3 12 3 4 1 4 2 5 2");
            testInput.add("3 12 2 4 3 4 4");
            testInput.add("2 3 2 4 5 4 6");
            testInput.add("3 504 3 4 7 4 8 4 9");
            testInput.add("2 1 2 5 1 6 1");
            testInput.add("1 21 3 5 3 6 3 6 2");
            testInput.add("1 6 2 5 4 6 4");
            testInput.add("2 2 2 5 5 5 6");
            testInput.add("2 1 2 5 7 5 8");
            testInput.add("2 8 2 5 9 6 9");
            testInput.add("1 13 2 6 5 7 5");
            testInput.add("2 2 2 6 6 7 6");
            testInput.add("1 11 2 6 7 6 8");
            testInput.add("2 1 2 7 1 8 1");
            testInput.add("1 17 3 7 2 7 3 8 2");
            testInput.add("1 24 4 7 4 8 3 8 4 8 5");
            testInput.add("4 2 2 7 7 7 8");
            testInput.add("2 6 2 7 9 8 9");
            testInput.add("1 19 3 8 6 9 6 9 7");
            testInput.add("1 9 3 8 7 8 8 9 8");
            testInput.add("3 40 3 9 1 9 2 9 3");
            testInput.add("2 2 2 9 4 9 5");
            testInput.add("0 3 1 9 9");

            boolean result = verifier.verify(testInput);
            assertTrue("Expected true for valid input", result);
            }

            @Test
            public void testInputWMaxOper() {
                Verifier verifier = new Verifier();
                Vector<String> testInput = new Vector<>();
                testInput.add("3 3");
                testInput.add("6 2 3 1 1 1 2 2 1");
                testInput.add("6 3 5 1 3 2 2 2 3 3 1 3 2");
                testInput.add("0 2 1 3 3");
                boolean result = verifier.verify(testInput);
                assertTrue("Expected true for valid input", result);
            }
            @Test
            public void voidInput(){
                Verifier verifier = new Verifier();
                Vector<String> testInput = new Vector<>();
                boolean result = verifier.verify(testInput);
                assertFalse("Expected true for valid input", result);
            }
            @Test
            public void ValidInputButMultipleSol3x3(){
                    Verifier verifier = new Verifier();
                    Vector<String> testInput = new Vector<>();
                    testInput.add("3 5");
                    testInput.add("0 3 1 1 1");
                    testInput.add("1 3 2 1 2 1 3");
                    testInput.add("4 2 2 2 1 3 1");
                    testInput.add("3 6 2 2 2 2 3");
                    testInput.add("2 2 2 3 2 3 3");
                    boolean result = verifier.verify(testInput);
                    assertTrue("Expected true for valid input", result);
            }
            @Test
            public void testMeanOper(){
                

            }


  }


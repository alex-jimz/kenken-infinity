package LogicTier.Coordinates;

import static org.junit.Assert.*;

import org.junit.*;
import LogicTier.TestRunner.TestRunner;

public class CoordinatesTest {
    @Test 
    public void testCreator() {
        Coordinates a = new Coordinates(0, 0);
        assertEquals(0, a.getX());
        assertEquals(0, a.getY());
        a.setX(5);
        a.setY(7);
        assertEquals(5, a.getX());
        assertEquals(7, a.getY());
    }

    @Test 
    public void testCreatorTwice() {
        Coordinates a = new Coordinates(0, 0);
        Coordinates b = new Coordinates(1, 3);
        assertEquals(0, a.getX());
        assertEquals(0, a.getY());
        a.setX(5);
        a.setY(7);
        assertEquals(5, a.getX());
        assertEquals(7, a.getY());
    }
    public static void main(String[] args) {
        TestRunner.runTestClass(CoordinatesTest.class);
    }
}

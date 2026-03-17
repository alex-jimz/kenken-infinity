package LogicTier.Region;

import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;

import LogicTier.Coordinates.Coordinates;
import LogicTier.Operation.Operation;
import LogicTier.TestRunner.TestRunner;

public class RegionTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(RegionTest.class);
    }

    @Test 
    public void testEmptyRegion() {
        Region r = new Region(0, 0, new Vector<Integer>(0));
        Operation op = r.getOperation();
        int target = r.getTarget();
        assertEquals(op, Operation.getOperationById(0));
        assertEquals(target, 0);

        //Test creator with parameters
        
    }

   @Test
   public void testValidRegion() {
        Vector<Integer> aux = new Vector<Integer>();
        aux.add(0);
        aux.add(0);

        Region r = new Region(2, 5, aux);
        Operation op = r.getOperation();
        int target = r.getTarget();

        Coordinates a = new Coordinates(0,0);
        Vector<Coordinates> coords = new Vector<Coordinates>();
        coords.add(a);

        assertEquals(Operation.getOperationById(2), op);
        assertEquals(5, target);

        Vector<Coordinates> cells = r.getCells();
        assertEquals(cells.size(), coords.size());
        for (int i = 0; i < cells.size(); ++i) {
            assertEquals(cells.get(i).getX(), coords.get(i).getX());
            assertEquals(cells.get(i).getY(), coords.get(i).getY());
        }
   }

   @Test
   public void testInvalidRegion() {
    try {
        Vector<Integer> aux = new Vector<Integer>();
        aux.add(0);
        aux.add(0);
        aux.add(1);
        Region r = new Region(1,1,aux);
        fail("Expected exception was not thrown");
    }
    catch (IllegalArgumentException e) {
        assertEquals("Vector length is not even.", e.getMessage());
    }
   }
}

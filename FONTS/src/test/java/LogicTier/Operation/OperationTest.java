package LogicTier.Operation;

import static org.junit.Assert.*;
import org.junit.*;

import LogicTier.GOperation.Addition;
import LogicTier.GOperation.ArithmeticMean;
import LogicTier.GOperation.Division;
import LogicTier.GOperation.Identity;
import LogicTier.GOperation.Max;
import LogicTier.GOperation.Product;
import LogicTier.GOperation.Subtraction;
import LogicTier.TestRunner.TestRunner;

public class OperationTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(OperationTest.class);
    }

    @Test
    public void testOperationRetrievalById() {
        assertEquals(0, Operation.getOperationById(0).getOperationId());
        assertEquals(1, Operation.getOperationById(1).getOperationId());
        assertEquals(2, Operation.getOperationById(2).getOperationId());
        assertEquals(3, Operation.getOperationById(3).getOperationId());
        assertEquals(4, Operation.getOperationById(4).getOperationId());
        assertEquals(5, Operation.getOperationById(5).getOperationId());
        assertEquals(6, Operation.getOperationById(6).getOperationId());
    }

    @Test
    public void testOperationIdRetrievalByClassName() {
        assertEquals(0, new Identity().getOperationId());
        assertEquals(1, new Addition().getOperationId());
        assertEquals(2, new Subtraction().getOperationId());
        assertEquals(3, new Product().getOperationId());
        assertEquals(4, new Division().getOperationId());
        assertEquals(5, new ArithmeticMean().getOperationId());
        assertEquals(6, new Max().getOperationId());
    }

    @Test
    public void testGetOperationById() {
        assertTrue(Operation.getOperationById(0) instanceof Identity);
        assertTrue(Operation.getOperationById(1) instanceof Addition);
        assertTrue(Operation.getOperationById(2) instanceof Subtraction);
        assertTrue(Operation.getOperationById(3) instanceof Product);
        assertTrue(Operation.getOperationById(4) instanceof Division);
        assertTrue(Operation.getOperationById(5) instanceof ArithmeticMean);
        assertTrue(Operation.getOperationById(6) instanceof Max);
    }

    @Test
    public void testCorrectNumOperandsById() {
        assertTrue(Operation.correctNumOperandsById(5, 7));
        assertTrue(Operation.correctNumOperandsById(6, 34));
        assertTrue(Operation.correctNumOperandsById(2, 2));
        assertFalse(Operation.correctNumOperandsById(1, 1));
        assertFalse(Operation.correctNumOperandsById(4, 3));
    }

    @Test
    public void testGetOperationByIdSameInstance() {
        Operation op1 = Operation.getOperationById(0);
        Operation op2 = Operation.getOperationById(0);
        assertSame(op1, op2);
    }
}
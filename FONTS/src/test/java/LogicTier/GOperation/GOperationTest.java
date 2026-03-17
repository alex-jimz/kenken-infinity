package LogicTier.GOperation;

import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import LogicTier.Operation.Operation;
import LogicTier.TestRunner.TestRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({ GOperationTest.IdentityTest.class, GOperationTest.AdditionTest.class,
     GOperationTest.SubtractionTest.class, GOperationTest.ProductTest.class,
     GOperationTest.DivisionTest.class, GOperationTest.ArithmeticMeanTest.class,
     GOperationTest.MaxTest.class })
public class GOperationTest {

    public static void main(String[] args) {
        TestRunner.runTestClass(GOperationTest.class);
    }

    public static class IdentityTest {
        @Test
        public void testIdentityOperation_PositiveInteger() {
            Identity identity = new Identity();
            assertEquals(5, identity.operate(new Vector<>(Arrays.asList(5))));
        }

        @Test
        public void testIdentityOperation_Zero() {
            Identity identity = new Identity();
            assertEquals(0, identity.operate(new Vector<>(Arrays.asList(0))));
        }

        @Test
        public void testIdentityOperation_NegativeInteger() {
            Identity identity = new Identity();
            assertEquals(-10, identity.operate(new Vector<>(Arrays.asList(-10))));
        }

        @Test
        public void testIdentityOperation_LargeInteger() {
            Identity identity = new Identity();
            assertEquals(Integer.MAX_VALUE, identity.operate(new Vector<>(Arrays.asList(Integer.MAX_VALUE))));
        }

        @Test
        public void testIdentityOperation_IncorrectNumberOfOperands() {
            Identity identity = new Identity();
            try {
                identity.operate(new Vector<>(Arrays.asList(-10, 2, 3)));
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }

        @Test
        public void testCorrectNumOperandsIdentity() {
            Operation op = new Identity();
            assertTrue(op.correctNumOperands(1));
            assertFalse(op.correctNumOperands(0));
            assertFalse(op.correctNumOperands(2));
            assertFalse(op.correctNumOperands(17));
        }
    }

    public static class AdditionTest {
        @Test
        public void testAdditionOperation_PositiveIntegers() {
            Addition addition = new Addition();
            assertEquals(10, addition.operate(new Vector<>(Arrays.asList(5, 5))));
        }

        @Test
        public void testAdditionOperation_Zero() {
            Addition addition = new Addition();
            assertEquals(5, addition.operate(new Vector<>(Arrays.asList(5, 0))));
        }

        @Test
        public void testAdditionOperation_NegativeIntegers() {
            Addition addition = new Addition();
            assertEquals(-10, addition.operate(new Vector<>(Arrays.asList(-5, -5))));
        }

        @Test
        public void testAdditionOperation_MultipleOperands() {
            Addition addition = new Addition();
            assertEquals(23, addition.operate(new Vector<>(Arrays.asList(5, 10, 5, 2, 1))));
        }

        @Test
        public void testAdditionOperation_IncorrectNumberOfOperands() {
            Addition addition = new Addition();
            try {
                addition.operate(new Vector<>(Arrays.asList(5))); // Only one operand
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }
    }

    public static class SubtractionTest {
        @Test
        public void testSubtractionOperation_PositiveIntegers() {
            Subtraction subtraction = new Subtraction();
            assertEquals(3, subtraction.operate(new Vector<>(Arrays.asList(5, 2))));
        }

        @Test
        public void testSubtractionOperation_Zero() {
            Subtraction subtraction = new Subtraction();
            assertEquals(5, subtraction.operate(new Vector<>(Arrays.asList(5, 0))));
        }

        @Test
        public void testSubtractionOperation_NegativeIntegers() {
            Subtraction subtraction = new Subtraction();
            assertEquals(10, subtraction.operate(new Vector<>(Arrays.asList(-5, 5))));
        }

        @Test
        public void testSubtractionOperation_LargeNumbers() {
            Subtraction subtraction = new Subtraction();
            assertEquals(2147483646, subtraction.operate(new Vector<>(Arrays.asList(Integer.MAX_VALUE, 1))));
        }

        @Test
        public void testSubtractionOperation_IncorrectNumberOfOperands() {
            Subtraction subtraction = new Subtraction();
            try {
                subtraction.operate(new Vector<>(Arrays.asList(5, 2, 1, -2)));
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }

        @Test
        public void testCorrectNumOperandsSubtraction() {
            Operation op = new Subtraction();
            assertTrue(op.correctNumOperands(2));
            assertFalse(op.correctNumOperands(0));
            assertFalse(op.correctNumOperands(1));
            assertFalse(op.correctNumOperands(3));
        }
    }

    public static class ProductTest {
        @Test
        public void testProductOperation_PositiveIntegers() {
            Product product = new Product();
            assertEquals(24, product.operate(new Vector<>(Arrays.asList(2, 3, 4))));
        }

        @Test
        public void testProductOperation_Zero() {
            Product product = new Product();
            assertEquals(0, product.operate(new Vector<>(Arrays.asList(0, 5, 2))));
        }

        @Test
        public void testProductOperation_NegativeIntegers() {
            Product product = new Product();
            assertEquals(-15, product.operate(new Vector<>(Arrays.asList(-3, 5))));
        }

        @Test
        public void testProductOperation_LargeNumbers() {
            Product product = new Product();
            assertEquals(2147483647, product.operate(new Vector<>(Arrays.asList(Integer.MAX_VALUE, 1))));
        }

        @Test
        public void testProductOperation_IncorrectNumberOfOperands() {
            Product product = new Product();
            try {
                product.operate(new Vector<>()); // No operands
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }
    }

    public static class DivisionTest {
        @Test
        public void testDivisionOperation_PositiveIntegers() {
            Division division = new Division();
            assertEquals(3, division.operate(new Vector<>(Arrays.asList(15, 5))));
        }

        @Test
        public void testDivisionOperation_Zero() {
            Division division = new Division();
            try {
                division.operate(new Vector<>(Arrays.asList(5, 0))); // Division by zero
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }

        @Test
        public void testDivisionOperation_NegativeIntegers() {
            Division division = new Division();
            assertEquals(-2, division.operate(new Vector<>(Arrays.asList(-10, 5))));
        }

        @Test
        public void testDivisionOperation_NonExactDivision() {
            Division division = new Division();
            try {
                division.operate(new Vector<>(Arrays.asList(10, 3))); // Non-exact division
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
                assertEquals("The division is not exact", e.getMessage());
            }
        }

        @Test
        public void testDivisionOperation_IncorrectNumberOfOperands() {
            Division division = new Division();
            try {
                division.operate(new Vector<>(Arrays.asList(5, 2, 13, 2)));
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }

        @Test
        public void testCorrectNumOperandsDivision() {
            Operation op = new Division();
            assertTrue(op.correctNumOperands(2));
            assertFalse(op.correctNumOperands(0));
            assertFalse(op.correctNumOperands(1));
            assertFalse(op.correctNumOperands(3));
        }
    }

    public static class ArithmeticMeanTest {
        @Test
        public void testArithmeticMeanOperation_PositiveIntegers() {
            ArithmeticMean arithmeticMean = new ArithmeticMean();
            assertEquals(4, arithmeticMean.operate(new Vector<>(Arrays.asList(2, 3, 5, 6))));
        }

        @Test
        public void testArithmeticMeanOperation_Zero() {
            ArithmeticMean arithmeticMean = new ArithmeticMean();
            assertEquals(0, arithmeticMean.operate(new Vector<>(Arrays.asList(0, 0, 0))));
        }

        @Test
        public void testArithmeticMeanOperation_NegativeIntegers() {
            ArithmeticMean arithmeticMean = new ArithmeticMean();
            assertEquals(-2, arithmeticMean.operate(new Vector<>(Arrays.asList(-5, -2, 1))));
        }

        @Test
        public void testArithmeticMeanOperation_LargeNumbers() {
            ArithmeticMean arithmeticMean = new ArithmeticMean();
            assertEquals(Integer.MAX_VALUE/2, arithmeticMean.operate(new Vector<>(Arrays.asList(Integer.MAX_VALUE/2, Integer.MAX_VALUE/2))));
        }

        @Test
        public void testArithmeticMeanOperation_IncorrectNumberOfOperands() {
            ArithmeticMean arithmeticMean = new ArithmeticMean();
            try {
                arithmeticMean.operate(new Vector<>()); // No operands
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }
    }

    public static class MaxTest {
        @Test
        public void testMaxOperation_PositiveIntegers() {
            Max max = new Max();
            assertEquals(8, max.operate(new Vector<>(Arrays.asList(3, 8, 2, 5))));
        }

        @Test
        public void testMaxOperation_Zero() {
            Max max = new Max();
            assertEquals(0, max.operate(new Vector<>(Arrays.asList(0, 0, -2))));
        }

        @Test
        public void testMaxOperation_NegativeIntegers() {
            Max max = new Max();
            assertEquals(-2, max.operate(new Vector<>(Arrays.asList(-5, -2, -3))));
        }

        @Test
        public void testMaxOperation_LargeNumbers() {
            Max max = new Max();
            assertEquals(Integer.MAX_VALUE, max.operate(new Vector<>(Arrays.asList(Integer.MAX_VALUE, Integer.MIN_VALUE))));
        }

        @Test
        public void testMaxOperation_IncorrectNumberOfOperands() {
            Max max = new Max();
            try {
                max.operate(new Vector<>()); // No operands
                fail("Expected IllegalArgumentException");
            } catch (IllegalArgumentException e) {
                // Success
            }
        }
    }
}
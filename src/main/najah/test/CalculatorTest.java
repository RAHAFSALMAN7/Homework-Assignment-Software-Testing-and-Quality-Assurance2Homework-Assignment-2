package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import main.najah.code.Calculator;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import java.util.concurrent.TimeUnit;

@DisplayName("Calculator Tests")
@Execution(ExecutionMode.CONCURRENT)  // Parallel execution enabled
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)  // Ensure order of tests
public class CalculatorTest {

    private Calculator calc;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Setup for all tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Cleanup after all tests");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Test setup complete");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test teardown complete");
    }

    @Test
    @Order(1)
    @DisplayName("Test Add Method with Valid Input")
    void testAddValidInput() {
        int result = calc.add(1, 2, 3);
        assertAll("Add method with valid input",
            () -> assertEquals(6, result, "The sum should be 6"),
            () -> assertTrue(result > 0, "The result should be positive")
        );
    }

    @Test
    @Order(2)
    @DisplayName("Test Add Method with Invalid Input (No Numbers)")
    void testAddInvalidInput() {
        int result = calc.add();
        assertEquals(0, result, "The sum should be 0 when no input is provided");
    }

    @ParameterizedTest
    @Order(3)
    @CsvSource({
        "1, 2, 3, 6",
        "5, 5, 5, 15",
        "-1, -2, -3, -6",
        "0, 0, 0, 0", // Added test case for 0 values
        "Integer.MAX_VALUE, -1, 1, Integer.MAX_VALUE" // Added edge case for max integer value
    })
    @DisplayName("Test Add Method with Multiple Inputs")
    void testAddMultipleInputs(int a, int b, int c, int expected) {
        int result = calc.add(a, b, c);
        assertEquals(expected, result, "The sum should match the expected value");
    }

    @Test
    @Order(4)
    @DisplayName("Test Divide Method with Valid Input")
    void testDivideValidInput() {
        int result = calc.divide(6, 2);
        assertAll("Divide method with valid input",
            () -> assertEquals(3, result, "6 divided by 2 should be 3"),
            () -> assertTrue(result > 0, "The result should be positive")
        );
    }

    @Test
    @Order(5)
    @DisplayName("Test Divide Method with Zero Divisor")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> calc.divide(6, 0),
            "Should throw ArithmeticException when dividing by zero");
    }

    @Test
    @Order(6)
    @DisplayName("Test Divide Method with Negative Divisor")
    void testDivideNegativeDivisor() {
        int result = calc.divide(6, -2);
        assertEquals(-3, result, "6 divided by -2 should be -3");
    }

    @Test
    @Order(7)
    @DisplayName("Test Divide Method with Negative Dividend")
    void testDivideNegativeDividend() {
        int result = calc.divide(-6, 2);
        assertEquals(-3, result, "Dividing -6 by 2 should be -3");
    }

    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    @Test
    @Order(8)
    @DisplayName("Test Factorial Method with Large Input")
    void testFactorialTimeout() {
        long result = calc.factorial(20); // Factorial of 20 is large enough to test
        assertEquals(2432902008176640000L, result, "Factorial of 20 should be correct");
    }

    @Test
    @Order(9)
    @DisplayName("Test Factorial Method with Negative Input")
    void testFactorialNegativeInput() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1),
            "Should throw IllegalArgumentException for negative input");
    }
    @Test
    @Order(10)
    @DisplayName("Test Factorial Method with Zero Input")
    void testFactorialZero() {
        long result = calc.factorial(0);  
        assertEquals(1, result, "Factorial of 0 should be 1");
    }


    @Test
    @Order(12)
    @DisplayName("Test Add Method with Large Numbers")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testAddLargeNumbers() {
        int result = calc.add(1000000, 2000000, 3000000);
        assertEquals(6000000, result, "The sum of large numbers should be correct");
    }

    @Test
    @Order(13)
    @DisplayName("Test Divide Method with Large Numbers")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testDivideLargeNumbers() {
        int result = calc.divide(1000000, 500000);
        assertEquals(2, result, "The result of dividing large numbers should be correct");
    }

    @Test
    @Order(14)
    @DisplayName("Test Multiply Method with Zero")
    void testMultiplyByZero() {
        int result = calc.multiply(5, 0);
        assertEquals(0, result, "Any number multiplied by 0 should result in 0");
    }

    @Test
    @Order(15)
    @DisplayName("Test Multiply Method with Negative Numbers")
    void testMultiplyNegativeNumbers() {
        int result = calc.multiply(-5, 5);
        assertEquals(-25, result, "Multiplying -5 and 5 should result in -25");
    }

    @Test
    @Order(16)
    @DisplayName("Test Multiply Method with Large Numbers")
    void testMultiplyLargeNumbers() {
        int result = calc.multiply(Integer.MAX_VALUE, 2);
        assertEquals(-2, result, "Multiplying large numbers should result in overflow and return negative");
    }

    @Test
    @Order(17)
    @DisplayName("Test Multiply Method with Edge Case for Large Numbers")
    void testMultiplyEdgeCaseLargeNumbers() {
        int result = calc.multiply(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertTrue(result < 0, "Multiplying two MAX_INT values should overflow and return a negative value");
    }

    @Test
    @Order(18)
    @DisplayName("Test Add Method with Edge Case for Large Numbers")
    void testAddEdgeCaseLargeNumbers() {
        int result = calc.add(Integer.MAX_VALUE, Integer.MAX_VALUE);
        assertEquals(2147483646, result, "The sum of two MAX_INT values should be 2147483646");
    }

    @Test
    @Order(19)
    @DisplayName("Test Factorial with Large Negative Numbers")
    void testFactorialLargeNegative() {
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-100),
            "Factorial should throw an exception for negative values");
    }

    @Test
    @Order(20)
    @DisplayName("Test Add Method with Multiple Assertions")
    void testAddMultipleAssertions() {
        int result = calc.add(2, 2, 3);
        assertAll("Multiple assertions for add method",
            () -> assertEquals(7, result, "The sum should be 7"),
            () -> assertTrue(result > 0, "The result should be positive")
        );
    }

    @Test
    @Order(21)
    @DisplayName("Test Add Method with Minimum Integer Value")
    void testAddMinIntegerValue() {
        int result = calc.add(Integer.MIN_VALUE, 1, 1);
        assertEquals(Integer.MIN_VALUE + 2, result, "The result should correctly handle Integer.MIN_VALUE");
    }

    @Test
    @Order(22)
    @DisplayName("Test Add Method with Maximum Integer Value")
    void testAddMaxIntegerValue() {
        int result = calc.add(Integer.MAX_VALUE, -1, 0);
        assertEquals(Integer.MAX_VALUE - 1, result, "The result should correctly handle Integer.MAX_VALUE");
    }
}

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
@Execution(ExecutionMode.CONCURRENT) // Enable parallel execution
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // Order the tests
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
		assertEquals(6, result, "The sum should be 6");
		assertTrue(result > 0, "The result should be positive");
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
			"-1, -2, -3, -6"
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
		assertEquals(3, result, "6 divided by 2 should be 3");
		assertTrue(result > 0, "The result should be positive");
	}

	@Test
	@Order(5)
	@DisplayName("Test Divide Method with Zero Divisor")
	void testDivideByZero() {
		assertThrows(ArithmeticException.class, () -> calc.divide(6, 0),
				"Should throw ArithmeticException when dividing by zero");
	}

	@Timeout(value = 2, unit = TimeUnit.SECONDS)
	@Test
	@Order(6)
	@DisplayName("Test Factorial Method with Large Input")
	void testFactorialTimeout() {
		int result = calc.factorial(20);
		assertEquals(2432902008176640000L, result, "Factorial of 20 should be correct");
	}

	@Test
	@Order(7)
	@DisplayName("Test Factorial Method with Negative Input")
	void testFactorialNegativeInput() {
		assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1),
				"Should throw IllegalArgumentException for negative input");
	}

	@Test
	@Order(8)
	@DisplayName("Test Factorial Method with Zero Input")
	void testFactorialZero() {
		int result = calc.factorial(0);
		assertEquals(1, result, "Factorial of 0 should be 1");
	}

	@Test
	@Order(9)
	@Disabled("This test is intentionally failing to demonstrate the use of @Disabled")
	@DisplayName("Test Divide Method with Invalid Test Case (Failing)")
	void testDivideInvalidFailing() {
		int result = calc.divide(6, 0); // This test will intentionally fail.
		assertEquals(1, result, "This test will fail intentionally"); // Fail intentionally
	}

	@Test
	@Order(10)
	@DisplayName("Test Add Method with Large Numbers")
	@Timeout(value = 2, unit = TimeUnit.SECONDS)
	void testAddLargeNumbers() {
		int result = calc.add(1000000, 2000000, 3000000);
		assertEquals(6000000, result, "The sum of large numbers should be correct");
	}

	@Test
	@Order(11)
	@DisplayName("Test Divide Method with Large Numbers")
	@Timeout(value = 2, unit = TimeUnit.SECONDS)
	void testDivideLargeNumbers() {
		int result = calc.divide(1000000, 500000);
		assertEquals(2, result, "The result of dividing large numbers should be correct");
	}
}

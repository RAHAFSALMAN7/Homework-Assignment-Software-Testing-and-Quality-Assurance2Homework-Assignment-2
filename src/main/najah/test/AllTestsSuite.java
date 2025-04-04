package main.najah.test;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    CalculatorTest.class,
    ProductTest.class,
    UserServiceTest.class,
    RecipeBookTest.class
})
public class AllTestsSuite {

    // This method runs before all tests in the suite
    @BeforeAll
    public static void setupAll() {
        System.out.println("Setup before all tests");
    }

    // This method runs after all tests in the suite
    @AfterAll
    public static void teardownAll() {
        System.out.println("Teardown after all tests");
    }
}

package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import main.najah.code.Product;

import java.util.concurrent.TimeUnit;

class ProductTest {

    Product product;

    @BeforeEach
    void setUp() {
        product = new Product("Laptop", 1000);
    }

    @Test
    @DisplayName("Test valid product creation")
    void testValidProductCreation() {
        assertAll("Product",
            () -> assertNotNull(product, "Product should not be null"),
            () -> assertEquals("Laptop", product.getName(), "Product name should be 'Laptop'"),
            () -> assertEquals(1000, product.getPrice(), "Product price should be 1000")
        );
    }

    @Test
    @DisplayName("Test invalid product price")
    void testInvalidProductPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Phone", -100), 
            "Price should not be negative");
    }

    @Test
    @DisplayName("Test zero product price")
    void testZeroProductPrice() {
        Product zeroPriceProduct = new Product("Free Item", 0);
        assertEquals(0, zeroPriceProduct.getPrice(), "Product price should be 0");
    }

    @Test
    @DisplayName("Test applying discount")
    void testApplyDiscount() {
        product.applyDiscount(10);
        assertEquals(900, product.getFinalPrice(), "Final price should be 900 after applying 10% discount");
    }

    @Test
    @DisplayName("Test invalid discount")
    void testInvalidDiscount() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-5), 
            "Discount should not be negative");
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(60), 
            "Discount should not exceed 50%");
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(150), 
            "Discount should not exceed 100%");
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 20.0, 30.0, 50.0, 100.0})
    @DisplayName("Test applying parameterized discounts")
    void testParameterizedDiscount(double discount) {
        product.applyDiscount(discount);
        assertEquals(1000 * (1 - discount / 100), product.getFinalPrice(), 
            "Final price should be calculated correctly for " + discount + "% discount");
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test timeout scenario for product operations")
    void testTimeout() {
        product.applyDiscount(15);
        // Simulating an actual operation that would take time
        try {
            Thread.sleep(50); // Simulate some delay, which is realistic for many real-world operations
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test multiple assertions for product")
    void testMultipleAssertions() {
        assertAll("Product",
            () -> assertNotNull(product, "Product should not be null"),
            () -> assertEquals("Laptop", product.getName(), "Product name should be 'Laptop'"),
            () -> assertEquals(1000, product.getPrice(), "Product price should be 1000")
        );
        product.applyDiscount(20);
        assertEquals(800, product.getFinalPrice(), "Final price should be 800 after applying 20% discount");
    }

    @Test
    @DisplayName("Test product with maximum price")
    void testMaxPriceProduct() {
        Product maxPriceProduct = new Product("Luxury Laptop", Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, maxPriceProduct.getPrice(), 
            "Product price should be Integer.MAX_VALUE");
    }

    // Additional test for handling large discounts in a boundary case
    @Test
    @DisplayName("Test applying discount greater than 100%")
    void testDiscountExceedsLimit() {
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(200),
            "Discount should not exceed 100%");
    }

    // Test product creation with special characters in the name
    @Test
    @DisplayName("Test product with special characters in the name")
    void testProductWithSpecialCharacters() {
        Product specialCharProduct = new Product("Fancy&Special@Laptop", 1500);
        assertEquals("Fancy&Special@Laptop", specialCharProduct.getName(), 
            "Product name should handle special characters correctly");
        assertEquals(1500, specialCharProduct.getPrice(), 
            "Product price should be 1500");
    }
}

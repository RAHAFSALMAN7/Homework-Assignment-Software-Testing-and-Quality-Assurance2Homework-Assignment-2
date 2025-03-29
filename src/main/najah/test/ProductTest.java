package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
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
        assertNotNull(product, "Product should not be null");
        assertEquals("Laptop", product.getName(), "Product name should be 'Laptop'");
        assertEquals(1000, product.getPrice(), "Product price should be 1000");
    }

    @Test
    @DisplayName("Test invalid product price")
    void testInvalidProductPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Product("Phone", -100), "Price should not be negative");
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
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(-5), "Discount should not be negative");
        assertThrows(IllegalArgumentException.class, () -> product.applyDiscount(60), "Discount should not exceed 50%");
    }

    @ParameterizedTest
    @ValueSource(doubles = {10.0, 20.0, 30.0})
    @DisplayName("Test applying parameterized discounts")
    void testParameterizedDiscount(double discount) {
        product.applyDiscount(discount);
        assertEquals(1000 * (1 - discount / 100), product.getFinalPrice(), "Final price should be calculated correctly");
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test timeout scenario for product operations")
    void testTimeout() {
        product.applyDiscount(15);
        try {
            Thread.sleep(50); // Simulate some delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Test multiple assertions for product")
    void testMultipleAssertions() {
        assertNotNull(product, "Product should not be null");
        assertEquals("Laptop", product.getName(), "Product name should be 'Laptop'");
        assertEquals(1000, product.getPrice(), "Product price should be 1000");
        product.applyDiscount(20);
        assertEquals(800, product.getFinalPrice(), "Final price should be 800 after applying 20% discount");
    }
}

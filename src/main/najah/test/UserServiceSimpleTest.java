package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import main.najah.code.UserService;

import java.util.concurrent.TimeUnit;

class UserServiceTest {
    UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    @DisplayName("Test valid email format")
    void testValidEmail() {
        assertTrue(userService.isValidEmail("user@example.com"), "Email should be valid");
    }

    @Test
    @DisplayName("Test invalid email format")
    void testInvalidEmail() {
        assertFalse(userService.isValidEmail("userexample.com"), "Email should be invalid");
    }

    @Test
    @DisplayName("Test valid authentication")
    void testValidAuthentication() {
        assertTrue(userService.authenticate("admin", "1234"), "Authentication should succeed with correct credentials");
    }

    @Test
    @DisplayName("Test invalid authentication")
    void testInvalidAuthentication() {
        assertFalse(userService.authenticate("admin", "wrongpassword"), "Authentication should fail with incorrect credentials");
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin", "user", "guest"})
    @DisplayName("Test valid usernames for authentication")
    void testParameterizedAuthentication(String username) {
        assertFalse(userService.authenticate(username, "1234"), "Authentication should fail for parameterized username");
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test authentication timeout")
    void testAuthenticationTimeout() {
        // Simulate a delay
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(userService.authenticate("admin", "1234"), "Authentication should not timeout");
    }

    @Test
    @DisplayName("Test multiple assertions for email validation and authentication")
    void testMultipleAssertions() {
        assertTrue(userService.isValidEmail("user@example.com"), "Email should be valid");
        assertFalse(userService.isValidEmail("userexample.com"), "Email should be invalid");
        assertTrue(userService.authenticate("admin", "1234"), "Authentication should succeed with valid credentials");
        assertFalse(userService.authenticate("admin", "wrongpassword"), "Authentication should fail with invalid credentials");
    }
}

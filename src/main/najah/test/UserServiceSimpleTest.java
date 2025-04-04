package main.najah.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
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
        assertFalse(userService.isValidEmail("userexample.com"), "Email should be invalid (missing '@')");
    }

    @Test
    @DisplayName("Test empty email")
    void testEmptyEmail() {
        assertFalse(userService.isValidEmail(""), "Email should not be empty");
    }

    @Test
    @DisplayName("Test email with special characters")
    void testEmailWithSpecialCharacters() {
        assertTrue(userService.isValidEmail("user+test@example.com"), "Email with special characters should be valid");
    }

    @Test
    @DisplayName("Test email with missing domain")
    void testEmailWithMissingDomain() {
        assertFalse(userService.isValidEmail("user@"), "Email with missing domain should be invalid");
    }

    @Test
    @DisplayName("Test email with spaces")
    void testEmailWithSpaces() {
        assertFalse(userService.isValidEmail("user @example.com"), "Email with spaces should be invalid");
    }

    @Test
    @DisplayName("Test email with consecutive dots")
    void testEmailWithConsecutiveDots() {
        assertFalse(userService.isValidEmail("user..test@example.com"), "Email with consecutive dots should be invalid");
    }

    @Test
    @DisplayName("Test email with multiple domain parts")
    void testEmailWithMultipleDomainParts() {
        assertTrue(userService.isValidEmail("user@test.example.com"), "Email with multiple domain parts should be valid");
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

    @Test
    @DisplayName("Test authentication with empty username")
    void testAuthenticationWithEmptyUsername() {
        assertFalse(userService.authenticate("", "1234"), "Authentication should fail with empty username");
    }

    @Test
    @DisplayName("Test authentication with empty password")
    void testAuthenticationWithEmptyPassword() {
        assertFalse(userService.authenticate("admin", ""), "Authentication should fail with empty password");
    }

    @Test
    @DisplayName("Test authentication with weak password")
    void testAuthenticationWithWeakPassword() {
        assertFalse(userService.authenticate("admin", "123"), "Authentication should fail with a weak password (less than 6 characters)");
    }

    @Test
    @DisplayName("Test authentication with very long password")
    void testAuthenticationWithVeryLongPassword() {
        String longPassword = "a".repeat(100);  // 100 characters long
        assertFalse(userService.authenticate("admin", longPassword), "Authentication should fail with an excessively long password");
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin", "user", "guest", "user+test", "12345"})
    @DisplayName("Test valid usernames for authentication")
    void testParameterizedAuthentication(String username) {
        assertFalse(userService.authenticate(username, "1234"), "Authentication should fail for parameterized username");
    }

    @Test
    @Timeout(value = 200, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test authentication timeout")
    void testAuthenticationTimeout() {
        try {
            Thread.sleep(100);  // Simulate network delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(userService.authenticate("admin", "1234"), "Authentication should not timeout within 200ms");
    }

    @Test
    @DisplayName("Test multiple assertions for email validation and authentication")
    void testMultipleAssertions() {
        assertAll("Multiple assertions for email and authentication",
            () -> assertTrue(userService.isValidEmail("user@example.com"), "Email should be valid"),
            () -> assertFalse(userService.isValidEmail("userexample.com"), "Email should be invalid"),
            () -> assertTrue(userService.authenticate("admin", "1234"), "Authentication should succeed with valid credentials"),
            () -> assertFalse(userService.authenticate("admin", "wrongpassword"), "Authentication should fail with invalid credentials")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"admin:wrongpassword", "user:1234", "guest:guest"})
    @DisplayName("Test invalid username/password combinations")
    void testInvalidUsernamePasswordCombination(String credentials) {
        String[] creds = credentials.split(":");
        assertFalse(userService.authenticate(creds[0], creds[1]), "Authentication should fail for invalid username/password");
    }

    // Edge case: Password with special characters
    @Test
    @DisplayName("Test authentication with special characters in password")
    void testAuthenticationWithSpecialCharactersInPassword() {
        String passwordWithSpecialChars = "P@ssw0rd!";
        assertTrue(userService.authenticate("admin", passwordWithSpecialChars), "Authentication should succeed with special characters in password");
    }

    // Edge case: Username with spaces
    @Test
    @DisplayName("Test authentication with username with spaces")
    void testAuthenticationWithUsernameWithSpaces() {
        assertFalse(userService.authenticate("admin user", "1234"), "Authentication should fail for username with spaces");
    }

    // Edge case: Very short username
    @Test
    @DisplayName("Test authentication with very short username")
    void testAuthenticationWithVeryShortUsername() {
        assertFalse(userService.authenticate("a", "1234"), "Authentication should fail for a very short username");
    }

    // Edge case: Non-alphanumeric username
    @Test
    @DisplayName("Test authentication with non-alphanumeric username")
    void testAuthenticationWithNonAlphanumericUsername() {
        assertFalse(userService.authenticate("admin#123", "1234"), "Authentication should fail for username with non-alphanumeric characters");
    }

    // Stress test: Large number of failed authentication attempts
    @Test
    @DisplayName("Test large number of failed authentication attempts")
    void testLargeNumberOfFailedAuthenticationAttempts() {
        for (int i = 0; i < 1000; i++) {
            assertFalse(userService.authenticate("admin", "wrongpassword"), "Authentication should fail for incorrect password on attempt " + i);
        }
    }

    // Edge case: Empty username/password for authentication with large inputs
    @Test
    @DisplayName("Test authentication with empty username and password")
    void testAuthenticationWithEmptyUsernameAndPassword() {
        assertFalse(userService.authenticate("", ""), "Authentication should fail with both empty username and password");
    }

    // Timeout for edge cases
    @Test
    @Timeout(value = 300, unit = TimeUnit.MILLISECONDS)
    @DisplayName("Test extended authentication timeout")
    void testExtendedAuthenticationTimeout() {
        try {
            Thread.sleep(250);  // Simulate a delay greater than the timeout
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(userService.authenticate("admin", "1234"), "Authentication should not timeout within 300ms");
    }
}

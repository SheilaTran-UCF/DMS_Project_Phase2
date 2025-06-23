package EmployeeTracker.utils;


/**
 *
 *  *  Professor: Ashley Evans
 *  *  Student name: Minh Ngoc Tran
 *  *  Course: 202530-CEN-3024C-31774
 *  *  Date : Jun 18 - 2025
 *
 * Unit tests for the InputValidator utility class.
 *
 * These tests simulate user console input to verify that InputValidator methods:
 * - Correctly validate non-empty strings
 * - Accept only valid positive numbers (double and long)
 * - Parse valid dates in yyyy-MM-dd format
 * - Accept only boolean inputs "true" or "false" (case insensitive)
 * - Enforce menu choice integer input within a specified range
 * - Return default file path if no input is provided
 *
 * The tests use a helper method to simulate console input streams.
 */


// Import JUnit 5 testing annotations and assertions
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    /**
     * Helper method to create an InputValidator instance
     * with a Scanner that reads from a predefined input string.
     * This simulates user console input during tests.
     */
    private InputValidator createValidatorWithInput(String input) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes()); // Convert input string to InputStream
        Scanner testScanner = new Scanner(testIn); // Create Scanner to read from the InputStream
        return new InputValidator(testScanner); // Return new InputValidator using the custom Scanner
    }

    /**
     * Test that getNonEmptyString method:
     * - Rejects empty input lines and lines with only whitespace
     * - Returns the first valid non-empty string input
     */
    @Test
    void testGetNonEmptyString_rejectsEmptyAndWhitespace() {
        // Provide simulated input: empty line, whitespace line, then valid string
        InputValidator validator = createValidatorWithInput("\n   \nValid Input\n");
        // Call method under test, expecting it to skip invalid inputs and return "Valid Input"
        String result = validator.getNonEmptyString("Enter a non-empty string: ");
        // Verify the returned string matches expected valid input
        assertEquals("Valid Input", result);
    }

    /**
     * Test getPositiveDouble method:
     * - Rejects invalid non-numeric inputs and negative numbers
     * - Accepts zero (if allowed) and positive double values
     */
    @Test
    void testGetPositiveDouble_rejectsInvalidAndNegative() {
        // Simulate input: invalid string, negative number, zero, then valid positive double
        InputValidator validator = createValidatorWithInput("notANumber\n-3.14\n0\n42.5\n");
        Double value = validator.getPositiveDouble("Enter positive double: ");
        // Check that zero is accepted (assuming zero is allowed)
        assertEquals(0.0, value);

        // Test with input that directly provides a positive double
        validator = createValidatorWithInput("42.5\n");
        value = validator.getPositiveDouble("Enter positive double: ");
        // Confirm that the method returns the positive double correctly
        assertEquals(42.5, value);
    }

    /**
     * Test getPositiveLong method:
     * - Rejects non-numeric and non-positive values (including zero)
     * - Accepts and returns the first valid positive long number
     */
    @Test
    void testGetPositiveLong_rejectsInvalidAndZero() {
        // Input sequence: invalid string, zero, negative number, then valid positive long
        InputValidator validator = createValidatorWithInput("foo\n0\n-1\n123\n");
        Long value = validator.getPositiveLong("Enter positive long: ");
        // Verify that only 123 (valid positive long) is accepted and returned
        assertEquals(123L, value);
    }

    /**
     * Test getDate method:
     * - Rejects invalid date formats or impossible dates
     * - Accepts and returns the first valid date in yyyy-MM-dd format
     */
    @Test
    void testGetDate_rejectsInvalidFormats() {
        // Simulated input: invalid format, invalid month, valid date
        InputValidator validator = createValidatorWithInput("06/23/2025\n2025-13-01\n2025-06-23\n");
        LocalDate date = validator.getDate("Enter date: ");
        // Verify the date returned matches the valid input date
        assertEquals(LocalDate.of(2025, 6, 23), date);
    }

    /**
     * Test getBoolean method:
     * - Accepts only "true" or "false" (case insensitive)
     * - Rejects any other input strings and continues prompting
     */
    @Test
    void testGetBoolean_acceptsOnlyTrueOrFalse() {
        // Input sequence: invalid strings "yes" and "no", then valid "TRUE"
        InputValidator validator = createValidatorWithInput("yes\nno\nTRUE\n");
        Boolean value = validator.getBoolean("Enter boolean: ");
        // Assert that returned value is true
        assertTrue(value);

        // Test with valid "False" input (case insensitive)
        validator = createValidatorWithInput("False\n");
        value = validator.getBoolean("Enter boolean: ");
        // Assert that returned value is false
        assertFalse(value);
    }

    /**
     * Test getMenuChoice method:
     * - Rejects invalid integers or out-of-range inputs
     * - Returns the first valid integer within the specified range (min to max)
     */
    @Test
    void testGetMenuChoice_rejectsInvalidAndOutOfRange() {
        // Input sequence: invalid "abc", out-of-range "0" and "6", then valid "3"
        InputValidator validator = createValidatorWithInput("abc\n0\n6\n3\n");
        int choice = validator.getMenuChoice("Choose an option: ", 1, 5);
        // Verify that returned choice is the valid number within range
        assertEquals(3, choice);
    }

    /**
     * Test getFilePath method:
     * - Returns default filename "employees.txt" if input is empty
     * - Returns user input if not empty
     */
    @Test
    void testGetFilePath_returnsDefaultIfEmpty() {
        // Input is empty line, expect default filename returned
        InputValidator validator = createValidatorWithInput("\n");
        String path = validator.getFilePath("Enter file path: ");
        assertEquals("employees.txt", path);

        // Input is a custom filename, expect it returned as is
        validator = createValidatorWithInput("myFile.csv\n");
        path = validator.getFilePath("Enter file path: ");
        assertEquals("myFile.csv", path);
    }
}

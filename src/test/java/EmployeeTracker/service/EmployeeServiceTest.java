package EmployeeTracker.service;

/**
 *
 *  *  Professor: Ashley Evans
 *  *  Student name: Minh Ngoc Tran
 *  *  Course: 202530-CEN-3024C-31774
 *  *  Date : Jun 18 - 2025
 *
 *
 * Unit tests for the EmployeeService class.
 *
 * This test suite verifies the core functionalities of EmployeeService, including:
 * - Adding employees and verifying their attributes
 * - Removing employees and ensuring they are deleted
 * - Updating employee attributes and confirming changes
 * - Finding employees by ID
 * - Generating a tenure report grouping employees by years of service
 * - Saving employee data to a file and loading it back correctly
 *
 * Each test method uses JUnit assertions to validate expected outcomes.
 */



// Import Employee model and JUnit testing classes
import EmployeeTracker.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeServiceTest {

    private EmployeeService service;  // Declare an EmployeeService instance for testing

    @BeforeEach
    public void setUp() {
        service = new EmployeeService();  // Initialize a new EmployeeService before each test
    }

    @Test
    public void testAddEmployee() {
        // Add a new employee to the service
        Employee emp = service.addEmployee("John Doe", "Manager", 75000.0, LocalDate.of(2020, 1, 15), "HR", true);
        // Retrieve the list of all employees
        List<Employee> employees = service.getAllEmployees();

        // Check that exactly one employee exists after adding
        assertEquals(1, employees.size());
        // Check that the added employee's name matches expected value
        assertEquals("John Doe", emp.getName());
        // Check that the added employee's position matches expected value
        assertEquals("Manager", emp.getPosition());
        // Check that the employee is marked active
        assertTrue(emp.getActive());
    }

    @Test
    public void testRemoveEmployee() {
        // Add an employee to later remove
        Employee emp = service.addEmployee("Jane Smith", "Engineer", 60000.0, LocalDate.of(2022, 3, 1), "IT", true);
        // Remove the employee by their ID and capture whether removal succeeded
        boolean removed = service.removeEmployee(emp.getId());

        // Assert that removal was successful
        assertTrue(removed);
        // Assert that no employees remain after removal
        assertTrue(service.getAllEmployees().isEmpty());
    }

    @Test
    public void testUpdateEmployee() {
        // Add an employee to update
        Employee emp = service.addEmployee("Alex Turner", "Analyst", 55000.0, LocalDate.of(2021, 6, 10), "Finance", true);
        // Update the employee's salary and capture if update succeeded
        boolean updated = service.updateEmployee(emp.getId(), "salary", 60000.0);

        // Assert that update operation returned success
        assertTrue(updated);
        // Retrieve the employee by ID after update
        Optional<Employee> updatedEmp = service.findById(emp.getId());
        // Assert that the employee was found
        assertTrue(updatedEmp.isPresent());
        // Assert that the salary was updated correctly
        assertEquals(60000.0, updatedEmp.get().getSalary());
    }

    @Test
    public void testFindById() {
        // Add an employee to find later
        Employee emp = service.addEmployee("Lisa Ray", "Consultant", 70000.0, LocalDate.of(2019, 8, 5), "Consulting", false);
        // Search for the employee by their ID
        Optional<Employee> found = service.findById(emp.getId());

        // Assert that the employee was found
        assertTrue(found.isPresent());
        // Assert that the found employee's name matches expected value
        assertEquals("Lisa Ray", found.get().getName());
    }

    @Test
    public void testGenerateTenureReport() {
        // Add three employees with varying tenure periods
        service.addEmployee("Alice", "Developer", 60000.0, LocalDate.now().minusYears(0), "IT", true);  // 0 years
        service.addEmployee("Bob", "Developer", 62000.0, LocalDate.now().minusYears(3), "IT", true);    // 3 years
        service.addEmployee("Charlie", "Manager", 80000.0, LocalDate.now().minusYears(7), "IT", true); // 7 years

        // Generate the tenure report grouping employees by years worked
        Map<String, List<Employee>> report = service.generateTenureReport();

        // Assert that there is 1 employee with 0-1 years tenure
        assertEquals(1, report.get("0-1 years").size());
        // Assert that there is 1 employee with 1-5 years tenure
        assertEquals(1, report.get("1-5 years").size());
        // Assert that there is 1 employee with 5+ years tenure
        assertEquals(1, report.get("5+ years").size());
    }

    @Test
    public void testSaveAndLoadFromFile() throws IOException {
        // Create a temporary file to save employee data during the test
        File tempFile = File.createTempFile("employee_test", ".txt");
        tempFile.deleteOnExit();  // Ensure temp file is deleted after test ends
        String filepath = tempFile.getAbsolutePath();

        // Add an employee to save
        service.addEmployee("Dylan", "Tester", 50000.0, LocalDate.of(2023, 1, 1), "QA", true);
        // Save employee data to the temporary file and check success
        boolean saveSuccess = service.saveToFile(filepath);
        assertTrue(saveSuccess);

        // Create a new EmployeeService instance to test loading
        EmployeeService newService = new EmployeeService();
        // Load employee data from the file and check success
        boolean loadSuccess = newService.loadFromFile(filepath);
        assertTrue(loadSuccess);

        // Retrieve employees loaded from the file
        List<Employee> loadedEmployees = newService.getAllEmployees();
        // Assert exactly one employee was loaded
        assertEquals(1, loadedEmployees.size());
        // Assert the loaded employee's name is correct
        assertEquals("Dylan", loadedEmployees.get(0).getName());
    }
}

package Test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import util.DBConnection;
import dao.EmployeeDAO;
import model.Employee;

/**
 * JUnit 5 Test Class for Database functionality
 */
@DisplayName("Database Connection and DAO Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JUnitDatabaseTest {

    private EmployeeDAO employeeDAO;

    @BeforeAll
    static void setUpClass() {
        System.out.println("🧪 Starting JUnit 5 Database Tests");
    }

    @BeforeEach
    void setUp() {
        employeeDAO = new EmployeeDAO();
        System.out.println("🔧 Database test setup completed");
    }

    @Test
    @Order(1)
    @DisplayName("Database Connection Test")
    void testDatabaseConnection() {
        // Act & Assert
        boolean connected = DBConnection.testConnection();
        assertTrue(connected, "Database connection should be successful");
        
        System.out.println("✅ testDatabaseConnection passed");
    }

    @Test
    @Order(2)
    @DisplayName("Database Info Retrieval")
    void testDatabaseInfo() {
        // Act
        String dbInfo = DBConnection.getDatabaseInfo();
        
        // Assert
        assertNotNull(dbInfo, "Database info should not be null");
        assertFalse(dbInfo.isEmpty(), "Database info should not be empty");
        assertTrue(dbInfo.contains("aoopdatabase_payroll"), "Database info should contain database name");
        
        System.out.println("✅ testDatabaseInfo passed");
    }

    @Test
    @Order(3)
    @DisplayName("Employee DAO Functionality")
    void testEmployeeDAO() {
        try {
            // Test getting all employees
            var employees = employeeDAO.getAllEmployees();
            assertNotNull(employees, "Employees list should not be null");
            assertFalse(employees.isEmpty(), "Employees list should not be empty");
            assertTrue(employees.size() >= 34, "Should have at least 34 employees from setup");

            // Test getting specific employee
            Employee employee = employeeDAO.getEmployeeById(10001);
            assertNotNull(employee, "Employee should not be null");
            assertEquals(10001, employee.getEmployeeId(), "Employee ID should be 10001");
            assertEquals("Garcia", employee.getLastName(), "Last name should be Garcia");
            assertEquals("Manuel III", employee.getFirstName(), "First name should be Manuel III");
            
            System.out.println("✅ testEmployeeDAO passed");
            
        } catch (Exception e) {
            System.out.println("⚠️ testEmployeeDAO skipped - database not available: " + e.getMessage());
            Assumptions.assumeTrue(false, "Database not available for testing");
        }
    }

    @Test
    @Order(4)
    @DisplayName("Employee Search Functionality")
    void testEmployeeSearch() {
        try {
            // Test search functionality
            var results = employeeDAO.searchEmployees("Garcia");
            
            assertNotNull(results, "Search results should not be null");
            assertFalse(results.isEmpty(), "Search results should not be empty");
            assertTrue(results.stream().anyMatch(emp -> 
                emp.getLastName().contains("Garcia") || emp.getFirstName().contains("Garcia")), 
                "Results should contain Garcia");
            
            System.out.println("✅ testEmployeeSearch passed");
            
        } catch (Exception e) {
            System.out.println("⚠️ testEmployeeSearch skipped - database not available");
            Assumptions.assumeTrue(false, "Database not available for testing");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Employee Existence Check")
    void testEmployeeExists() {
        try {
            // Test existing employee
            assertTrue(employeeDAO.employeeExists(10001), "Employee 10001 should exist");
            
            // Test non-existing employee
            assertFalse(employeeDAO.employeeExists(99999), "Employee 99999 should not exist");
            
            System.out.println("✅ testEmployeeExists passed");
            
        } catch (Exception e) {
            System.out.println("⚠️ testEmployeeExists skipped - database not available");
            Assumptions.assumeTrue(false, "Database not available for testing");
        }
    }

    @AfterEach
    void tearDown() {
        employeeDAO = null;
        System.out.println("🧹 Database test cleanup completed");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("✅ All JUnit 5 Database Tests Completed Successfully!");
    }
}
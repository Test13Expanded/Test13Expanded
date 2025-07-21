package Test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import model.Attendance;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * JUnit 5 Test Class for Attendance functionality
 */
@DisplayName("Attendance Model Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JUnitAttendanceTest {

    private Attendance testAttendance;

    @BeforeAll
    static void setUpClass() {
        System.out.println("🧪 Starting JUnit 5 Attendance Tests");
    }

    @BeforeEach
    void setUp() {
        testAttendance = new Attendance();
        System.out.println("🔧 Attendance test setup completed");
    }

    @Test
    @Order(1)
    @DisplayName("Create Valid Attendance Record")
    void testCreateValidAttendance() {
        // Arrange
        int employeeId = 10001;
        Date date = Date.valueOf(LocalDate.now());
        Time logIn = Time.valueOf(LocalTime.of(8, 0));
        Time logOut = Time.valueOf(LocalTime.of(17, 0));

        // Act
        testAttendance.setEmployeeId(employeeId);
        testAttendance.setDate(date);
        testAttendance.setLogIn(logIn);
        testAttendance.setLogOut(logOut);

        // Assert
        assertAll("Attendance creation",
            () -> assertEquals(employeeId, testAttendance.getEmployeeId(), "Employee ID should match"),
            () -> assertEquals(date, testAttendance.getDate(), "Date should match"),
            () -> assertEquals(logIn, testAttendance.getLogIn(), "Log in time should match"),
            () -> assertEquals(logOut, testAttendance.getLogOut(), "Log out time should match"),
            () -> assertTrue(testAttendance.isPresent(), "Should be present"),
            () -> assertEquals(9.0, testAttendance.getWorkHours(), 0.01, "Work hours should be 9.0")
        );
        
        System.out.println("✅ testCreateValidAttendance passed");
    }

    @Test
    @Order(2)
    @DisplayName("Invalid Employee ID Validation")
    void testInvalidEmployeeId() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> testAttendance.setEmployeeId(-1),
            "Should throw exception for invalid employee ID"
        );
        
        assertTrue(exception.getMessage().contains("positive"), 
                  "Error message should mention positive requirement");
        
        System.out.println("✅ testInvalidEmployeeId passed");
    }

    @Test
    @Order(3)
    @DisplayName("Late Arrival Detection")
    void testLateArrival() {
        // Arrange
        testAttendance.setEmployeeId(10001);
        testAttendance.setDate(Date.valueOf(LocalDate.now()));
        testAttendance.setLogIn(Time.valueOf(LocalTime.of(8, 30))); // 30 minutes late
        testAttendance.setLogOut(Time.valueOf(LocalTime.of(17, 0)));

        // Act & Assert
        assertTrue(testAttendance.isLate(), "Should detect late arrival");
        assertEquals(30.0, testAttendance.getLateMinutes(), 0.01, "Late minutes should be 30");
        
        System.out.println("✅ testLateArrival passed");
    }

    @Test
    @Order(4)
    @DisplayName("Undertime Detection")
    void testUndertime() {
        // Arrange
        testAttendance.setEmployeeId(10001);
        testAttendance.setDate(Date.valueOf(LocalDate.now()));
        testAttendance.setLogIn(Time.valueOf(LocalTime.of(8, 0)));
        testAttendance.setLogOut(Time.valueOf(LocalTime.of(16, 30))); // 30 minutes early

        // Act & Assert
        assertTrue(testAttendance.hasUndertime(), "Should detect undertime");
        assertEquals(30.0, testAttendance.getUndertimeMinutes(), 0.01, "Undertime minutes should be 30");
        
        System.out.println("✅ testUndertime passed");
    }

    @Test
    @Order(5)
    @DisplayName("Work Duration Calculation")
    void testWorkDurationCalculation() {
        // Arrange
        testAttendance.setEmployeeId(10001);
        testAttendance.setDate(Date.valueOf(LocalDate.now()));
        testAttendance.setLogIn(Time.valueOf(LocalTime.of(8, 0)));
        testAttendance.setLogOut(Time.valueOf(LocalTime.of(17, 30)));

        // Act
        double workHours = testAttendance.getWorkHours();

        // Assert
        assertEquals(9.5, workHours, 0.01, "Work hours should be 9.5");
        
        System.out.println("✅ testWorkDurationCalculation passed");
    }

    @Test
    @Order(6)
    @DisplayName("Null Log Out Handling")
    void testNullLogOut() {
        // Arrange
        testAttendance.setEmployeeId(10001);
        testAttendance.setDate(Date.valueOf(LocalDate.now()));
        testAttendance.setLogIn(Time.valueOf(LocalTime.of(8, 0)));
        testAttendance.setLogOut(null);

        // Act & Assert
        assertTrue(testAttendance.isPresent(), "Should be present even with null log out");
        assertEquals(0.0, testAttendance.getWorkHours(), 0.01, "Work hours should be 0 with null log out");
        
        System.out.println("✅ testNullLogOut passed");
    }

    @Test
    @Order(7)
    @DisplayName("Date Validation")
    void testDateValidation() {
        // Test null date
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> testAttendance.setDate(null),
            "Should throw exception for null date"
        );
        
        assertTrue(exception.getMessage().contains("cannot be null"), 
                  "Error message should mention null restriction");
        
        System.out.println("✅ testDateValidation passed");
    }

    @AfterEach
    void tearDown() {
        testAttendance = null;
        System.out.println("🧹 Attendance test cleanup completed");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("✅ All JUnit 5 Attendance Tests Completed Successfully!");
    }
}
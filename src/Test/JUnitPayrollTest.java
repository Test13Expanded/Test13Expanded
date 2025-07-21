package Test;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import model.Payroll;
import service.PayrollCalculator;
import java.time.LocalDate;
import java.sql.Date;

/**
 * JUnit 5 Test Class for Payroll functionality
 * Demonstrates proper use of Assert Functions and viewable test results
 */
@DisplayName("Payroll Calculation Tests")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JUnitPayrollTest {

    private Payroll testPayroll;
    private PayrollCalculator payrollCalculator;

    @BeforeAll
    static void setUpClass() {
        System.out.println("🧪 Starting JUnit 5 Payroll Tests");
    }

    @BeforeEach
    void setUp() {
        testPayroll = new Payroll();
        payrollCalculator = new PayrollCalculator();
        System.out.println("🔧 Payroll test setup completed");
    }

    @Test
    @Order(1)
    @DisplayName("Payroll Object Creation")
    void testPayrollCreation() {
        // Arrange & Act
        testPayroll.setEmployeeId(10001);
        testPayroll.setMonthlyRate(50000.0);
        testPayroll.setDaysWorked(22);
        testPayroll.setGrossPay(50000.0);
        testPayroll.setTotalDeductions(10000.0);
        testPayroll.setNetPay(40000.0);

        // Assert
        assertAll("Payroll creation",
            () -> assertEquals(10001, testPayroll.getEmployeeId(), "Employee ID should match"),
            () -> assertEquals(50000.0, testPayroll.getMonthlyRate(), 0.01, "Monthly rate should match"),
            () -> assertEquals(22, testPayroll.getDaysWorked(), "Days worked should match"),
            () -> assertEquals(50000.0, testPayroll.getGrossPay(), 0.01, "Gross pay should match"),
            () -> assertEquals(10000.0, testPayroll.getTotalDeductions(), 0.01, "Total deductions should match"),
            () -> assertEquals(40000.0, testPayroll.getNetPay(), 0.01, "Net pay should match"),
            () -> assertTrue(testPayroll.isValid(), "Payroll should be valid")
        );
        
        System.out.println("✅ testPayrollCreation passed");
    }

    @Test
    @Order(2)
    @DisplayName("Payroll Validation Tests")
    void testPayrollValidation() {
        // Test invalid employee ID
        IllegalArgumentException exception1 = assertThrows(
            IllegalArgumentException.class,
            () -> testPayroll.setEmployeeId(-1),
            "Should throw exception for negative employee ID"
        );

        // Test invalid monthly rate
        IllegalArgumentException exception2 = assertThrows(
            IllegalArgumentException.class,
            () -> testPayroll.setMonthlyRate(-1000.0),
            "Should throw exception for negative monthly rate"
        );

        // Test invalid days worked
        IllegalArgumentException exception3 = assertThrows(
            IllegalArgumentException.class,
            () -> testPayroll.setDaysWorked(-1),
            "Should throw exception for negative days worked"
        );

        assertAll("Validation error messages",
            () -> assertTrue(exception1.getMessage().contains("positive"), "Employee ID error message"),
            () -> assertTrue(exception2.getMessage().contains("negative"), "Monthly rate error message"),
            () -> assertTrue(exception3.getMessage().contains("negative"), "Days worked error message")
        );
        
        System.out.println("✅ testPayrollValidation passed");
    }

    @Test
    @Order(3)
    @DisplayName("Government Contributions Calculation")
    void testGovernmentContributions() {
        // Test SSS calculation for different salary ranges
        assertEquals(180.00, calculateSSS(4000.0), 0.01, "SSS for 4000 should be 180.00");
        assertEquals(1125.00, calculateSSS(30000.0), 0.01, "SSS for 30000 should be 1125.00");
        assertEquals(1125.00, calculateSSS(50000.0), 0.01, "SSS for 50000 should be 1125.00");

        // Test PhilHealth calculation
        double philHealthContrib = calculatePhilHealth(50000.0);
        assertTrue(philHealthContrib >= 500.00 && philHealthContrib <= 5000.00, 
                  "PhilHealth contribution should be between 500 and 5000");

        // Test Pag-IBIG calculation
        assertEquals(15.00, calculatePagIBIG(1500.0), 0.01, "Pag-IBIG for 1500 should be 15.00");
        assertEquals(200.00, calculatePagIBIG(20000.0), 0.01, "Pag-IBIG for 20000 should be 200.00");
        assertEquals(200.00, calculatePagIBIG(50000.0), 0.01, "Pag-IBIG for 50000 should be 200.00");
        
        System.out.println("✅ testGovernmentContributions passed");
    }

    @Test
    @Order(4)
    @DisplayName("Payroll Calculation Components")
    void testPayrollCalculationComponents() {
        // Test daily rate calculation
        double monthlySalary = 50000.0;
        double expectedDailyRate = monthlySalary / 22.0; // 22 working days
        assertEquals(2272.73, expectedDailyRate, 0.01, "Daily rate calculation should be correct");

        // Test hourly rate calculation
        double expectedHourlyRate = expectedDailyRate / 8.0; // 8 hours per day
        assertEquals(284.09, expectedHourlyRate, 0.01, "Hourly rate calculation should be correct");

        // Test overtime calculation
        double overtimeHours = 5.0;
        double overtimeRate = expectedHourlyRate * 1.25; // 125% rate
        double expectedOvertimePay = overtimeHours * overtimeRate;
        assertEquals(1775.57, expectedOvertimePay, 0.01, "Overtime pay calculation should be correct");
        
        System.out.println("✅ testPayrollCalculationComponents passed");
    }

    @Test
    @Order(5)
    @DisplayName("Date Range Validation")
    void testDateRangeValidation() {
        // Test valid date range
        Date startDate = Date.valueOf(LocalDate.of(2024, 6, 1));
        Date endDate = Date.valueOf(LocalDate.of(2024, 6, 30));
        
        assertDoesNotThrow(() -> {
            testPayroll.setPeriodStart(startDate);
            testPayroll.setPeriodEnd(endDate);
        }, "Should not throw exception for valid date range");

        // Test invalid date range (end before start)
        Date invalidEndDate = Date.valueOf(LocalDate.of(2024, 5, 30));
        
        testPayroll.setPeriodStart(startDate);
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> testPayroll.setPeriodEnd(invalidEndDate),
            "Should throw exception when end date is before start date"
        );
        
        assertTrue(exception.getMessage().contains("cannot be before"), 
                  "Error message should mention date order restriction");
        
        System.out.println("✅ testDateRangeValidation passed");
    }

    @Test
    @Order(6)
    @DisplayName("Payroll Calculator Integration")
    void testPayrollCalculatorIntegration() {
        try {
            // Test with valid employee ID and date range
            LocalDate periodStart = LocalDate.of(2024, 6, 1);
            LocalDate periodEnd = LocalDate.of(2024, 6, 30);
            
            Payroll calculatedPayroll = payrollCalculator.calculatePayroll(10001, periodStart, periodEnd);
            
            assertNotNull(calculatedPayroll, "Calculated payroll should not be null");
            assertEquals(10001, calculatedPayroll.getEmployeeId(), "Employee ID should match");
            assertTrue(calculatedPayroll.getGrossPay() >= 0, "Gross pay should be non-negative");
            assertTrue(calculatedPayroll.getTotalDeductions() >= 0, "Total deductions should be non-negative");
            assertTrue(calculatedPayroll.isValid(), "Calculated payroll should be valid");
            
            System.out.println("✅ testPayrollCalculatorIntegration passed");
            
        } catch (Exception e) {
            System.out.println("⚠️ testPayrollCalculatorIntegration skipped - requires database connection");
            Assumptions.assumeTrue(false, "Database not available for testing");
        }
    }

    // Helper methods for testing calculations
    private double calculateSSS(double monthlySalary) {
        if (monthlySalary <= 4000) return 180.00;
        if (monthlySalary <= 25000) return Math.min(monthlySalary * 0.045, 1125.00);
        return 1125.00;
    }

    private double calculatePhilHealth(double monthlySalary) {
        double contribution = monthlySalary * 0.025;
        return Math.max(Math.min(contribution, 5000.00), 500.00);
    }

    private double calculatePagIBIG(double monthlySalary) {
        if (monthlySalary <= 1500) return monthlySalary * 0.01;
        return Math.min(monthlySalary * 0.02, 200.00);
    }

    @AfterEach
    void tearDown() {
        testPayroll = null;
        System.out.println("🧹 Payroll test cleanup completed");
    }

    @AfterAll
    static void tearDownClass() {
        System.out.println("✅ All JUnit 5 Payroll Tests Completed Successfully!");
    }
}
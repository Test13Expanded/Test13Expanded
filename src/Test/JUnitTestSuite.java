package Test;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

/**
 * JUnit 5 Test Suite for MotorPH Payroll System
 * Addresses mentor feedback about proper JUnit usage with Assert Functions
 */
@Suite
@SuiteDisplayName("MotorPH Payroll System Test Suite")
@SelectClasses({
    JUnitEmployeeTest.class,
    JUnitPayrollTest.class,
    JUnitAttendanceTest.class,
    JUnitDatabaseTest.class
})
public class JUnitTestSuite {
    // Test suite configuration
    // All tests will be executed automatically by JUnit 5
}
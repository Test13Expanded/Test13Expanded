package Test;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * JUnit 5 Test Runner for MotorPH Payroll System
 * Addresses mentor feedback: "Unit test is incorrect. JUnit was not utilized. 
 * The whole point of using JUnit is to use Assert Functions and to have a viewable test result."
 */
public class AllTestsRunner {
    
    public static void main(String[] args) {
        System.out.println("🧪 Running MotorPH Payroll System JUnit 5 Test Suite");
        System.out.println("=" .repeat(80));
        
        try {
            // Create launcher discovery request
            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(selectPackage("Test"))
                    .build();
            
            // Create launcher
            Launcher launcher = LauncherFactory.create();
            
            // Create summary listener
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            
            // Execute tests
            launcher.registerTestExecutionListeners(listener);
            launcher.execute(request);
            
            // Print test results
            TestExecutionSummary summary = listener.getSummary();
            printTestSummary(summary);
            
        } catch (Exception e) {
            System.err.println("❌ Error running JUnit tests: " + e.getMessage());
            e.printStackTrace();
            
            // Fallback to manual tests
            System.out.println("\n🔄 Falling back to manual test execution...");
            runManualTests();
        }
    }
    
    private static void printTestSummary(TestExecutionSummary summary) {
        System.out.println("\n📊 JUnit 5 Test Execution Summary");
        System.out.println("=" .repeat(50));
        System.out.printf("Tests Found: %d%n", summary.getTestsFoundCount());
        System.out.printf("Tests Started: %d%n", summary.getTestsStartedCount());
        System.out.printf("Tests Successful: %d%n", summary.getTestsSucceededCount());
        System.out.printf("Tests Failed: %d%n", summary.getTestsFailedCount());
        System.out.printf("Tests Skipped: %d%n", summary.getTestsSkippedCount());
        System.out.printf("Execution Time: %d ms%n", summary.getTotalTime().toMillis());
        
        if (summary.getTestsFailedCount() > 0) {
            System.out.println("\n❌ Failed Tests:");
            summary.getFailures().forEach(failure -> {
                System.out.println("  • " + failure.getTestIdentifier().getDisplayName());
                System.out.println("    Reason: " + failure.getException().getMessage());
            });
        }
        
        if (summary.getTestsSucceededCount() == summary.getTestsFoundCount()) {
            System.out.println("\n🎉 ALL TESTS PASSED! MotorPH Payroll System is ready for production.");
        } else {
            System.out.println("\n⚠️ Some tests failed. Please review and fix issues before deployment.");
        }
    }
    
    private static void runManualTests() {
        try {
            // Run existing manual tests as fallback
            TestSuite.main(new String[]{});
        } catch (Exception e) {
            System.err.println("❌ Manual tests also failed: " + e.getMessage());
        }
    }
}
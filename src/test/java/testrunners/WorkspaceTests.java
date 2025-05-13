package testrunners;

import io.cucumber.testng.CucumberOptions;

/**
 * The test runner class {@code WorkspaceTests} used to run Atomicwork
 * Workspace tests.
 */
@CucumberOptions(
    features = "classpath:features/atomicworkportal", glue = "stepdefinitions.atomicworkportal", tags = "@workspace",  plugin = {"pretty", 
        "html:target/reports/cucumber-reports/workspace_test_report.html",
        "json:target/reports/cucumber-json-reports/workspace_test_report.json",
        "testng:target/reports/cucumber-xml-reports/workspace_test_report.xml", // Changed from junit to testng
        "timeline:target/reports/timeline-reports/overview"
    }
)
public class WorkspaceTests extends BaseTest {

 
}
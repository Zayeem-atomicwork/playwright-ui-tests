package testrunners;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import managers.FileReaderManager;
import managers.PlaywrightManager;
import utilities.ConvertUtils;
import utilities.CucumberReport;
import utilities.Log;

/**
 * The {@code BaseTest} class contains methods, hooks which can be used by other
 * test runners.
 */
public class BaseTest extends AbstractTestNGCucumberTests {
    private final Logger logger = Log.getLogger(BaseTest.class);
    private static final String PROJECT_NAME = "Atomicwork UI Tests";
    private static PlaywrightManager playwrightManager;

    /**
     * BeforeSuite hook method performing validation for mandatory parameters which
     * should be passed while running the tests. Exit if not provided.
     */
    @BeforeSuite
    public void cmdLineParamValidations() {
        logger.info("Validating command line parameters.");
        if (StringUtils.isBlank(System.getProperty("dashboardType"))) {
            logger.error("dashboardType is a mandatory parameter.");
            System.exit(1);
        }
        if (StringUtils.isBlank(System.getProperty("envType"))) {
            logger.error("envType is a mandatory parameter.");
            System.exit(1);
        }
        final boolean areAllPresent = StringUtils.isAnyBlank(System.getProperty("email"),
                System.getProperty("password"));
        final boolean areAllAbsent = StringUtils.isAllBlank(System.getProperty("email"), System.getProperty("password"));

        if (areAllPresent != areAllAbsent) {
            logger.error(
                    "Either all Email, Password are expected or None as command line parameters. If all are absent, then values are taken from the config.yaml file.");
            System.exit(1);
        }
        final String scenarioThreadCount = System.getProperty("scenarioThreadCount"),
                featureThreadCount = System.getProperty("scenarioThreadCount");
        if ("webkit".equals(System.getProperty("browser")) && (!StringUtils.isBlank(scenarioThreadCount)
                && ConvertUtils.stringToInt(scenarioThreadCount) != 1
                || !StringUtils.isBlank(scenarioThreadCount) && ConvertUtils.stringToInt(featureThreadCount) != 1)) {
            logger.error(
                    "The WebKit driver may have issues with high concurrency. Keep both scenarioThreadCount and scenarioThreadCount as 1 instead.");
            System.exit(1);
        }
    }

    /**
     * BeforeSuite hook method to setup the browser driver.
     */
    @BeforeSuite(dependsOnMethods = { "cmdLineParamValidations" })
    public void setupPlaywright() {
        logger.info("Setting up the browser driver.");
        try {
            playwrightManager = PlaywrightManager.getInstance();
            logger.info("Playwright manager initialized successfully");
            
            // Create a page to validate setup
            playwrightManager.getPage();
            logger.info("Playwright page created successfully");
        } catch (Exception e) {
            logger.error("Failed to setup Playwright: {}", e.getMessage(), e);
            
            // Attempt to clean up if initialization failed
            if (playwrightManager != null) {
                try {
                    playwrightManager.quitPlaywright();
                } catch (Exception ex) {
                    logger.error("Error during cleanup after failed setup: {}", ex.getMessage());
                }
            }
            
            throw new RuntimeException("Failed to setup Playwright", e);
        }
    }

    /**
     * Overriding the scenarios method of {@code AbstractTestNGCucumberTests} class
     * and setting the parallel option to true for test parallelization as specified
     * {@link https://cucumber.io/docs/guides/parallel-execution/#testng} here.
     */
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    /**
     * AfterSuite hook method to generate cucumber report after the test run.
     */
    @AfterSuite
    public void generateReport() {
        final Map<String, String> metadata = new ConcurrentHashMap<>();
        metadata.put("Dashboard", System.getProperty("dashboardType"));
        metadata.put("Environment", System.getProperty("envType"));
        metadata.put("Browser", FileReaderManager.getInstance().getConfigReader().getBrowserType().toString()
                .toLowerCase(Locale.getDefault()));
        CucumberReport.generateReport(PROJECT_NAME, metadata);
    }
    
    /**
     * AfterSuite hook method to close Playwright resources.
     */
    @AfterSuite
    public void quitPlaywright() {
        logger.info("Cleaning up Playwright resources");
        if (playwrightManager != null) {
            try {
                playwrightManager.quitPlaywright();
                logger.info("Playwright resources cleaned up successfully");
            } catch (Exception e) {
                logger.error("Error cleaning up Playwright resources: {}", e.getMessage(), e);
            }
        }
    }
}
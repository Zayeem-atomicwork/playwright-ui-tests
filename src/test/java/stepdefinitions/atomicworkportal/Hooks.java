package stepdefinitions.atomicworkportal;

import org.apache.logging.log4j.Logger;
import cucumber.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import managers.FileReaderManager;
import utilities.Log;

/**
 * The class {@code Hooks} includes method hooks which are executed before and
 * after each test scenario.
 */
public class Hooks {
    private final TestContext testContext;
    private final Logger logger;

    public Hooks(final TestContext testContext) {
        this.testContext = testContext;
        logger = Log.getLogger(Hooks.class);
    }

    /**
     * Before hook method to open the Atomicwork portal login page.
     */
    @Before
    public void openLoginPage() {
        final String loginPageUrl = FileReaderManager.getInstance().getConfigReader().getLoginPageUrl();
        logger.info("Opening login page: {}", loginPageUrl);
        testContext.getPlaywrightManager().navigateTo(loginPageUrl);
    }

    /**
     * After hook method to completely recreate the browser context after each scenario.
     * This keeps browser open but creates a fresh browser context for complete isolation.
     */
    @After(order = 0)
    public void cleanupAfterScenario() {
        logger.info("Cleaning up after scenario");
        
        // Recreate the browser context for a fresh start
        testContext.getPlaywrightManager().recreateBrowserContext();
    }

    /**
     * After hook method to take screenshot when the cucumber test fails.
     *
     * @param scenario: {@link Scenario} object
     */
    @After(order = 1)
    public void takeScreenshotOnFailure(final Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                final byte[] screenshot = testContext.getPlaywrightManager().getPage().screenshot();
                scenario.attach(screenshot, "image/png", scenario.getName().replace(" ", "_"));
                scenario.log(String.format("URL at failure: %s",
                        testContext.getPlaywrightManager().getPage().url()));
            } catch (Exception e) {
                scenario.log(String.format("Screenshot failed. %s", e.getMessage()));
            }
        }
    }
}
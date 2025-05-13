package cucumber;

import enums.ContextData;
import managers.PageObjectManager;
import managers.PlaywrightManager;
import utilities.ScenarioContext;

/**
 * The class {@code TestContext} is the parent class used to share test state
 * (objects of page step library classes etc.) between all the step definition
 * files using PicoContainer DI library. Cucumber tells PicoContainer to
 * instantiate the step definition classes. To do this, constructors for the
 * step definition classes require an instance of the same class which is
 * {@code TestContext} class.
 */
public class TestContext {
    private final PlaywrightManager playwrightManager;
    private PageObjectManager pageObjectManager;
    private final ScenarioContext<ContextData> scenarioContext;
    
    public TestContext() {
        playwrightManager = PlaywrightManager.getInstance();
        scenarioContext = new ScenarioContext<>();
    }
    
    /**
     * Method to get the instance of {@code PlaywrightManager} class.
     * 
     * @return {@code PlaywrightManager} instance
     */
    public PlaywrightManager getPlaywrightManager() {
        return playwrightManager;
    }
    
    /**
     * Method to instantiate the object of {@code PageObjectManager} class.
     * 
     * @return {@code PageObjectManager} object
     */
    public PageObjectManager getPageObjectManager() {
        return (pageObjectManager == null) ? pageObjectManager = new PageObjectManager(playwrightManager.getPage())
                : pageObjectManager;
    }
    
    /**
     * Method to instantiate the object of {@code ScenarioContext} class.
     * 
     * @return {@code ScenarioContext} object
     */
    public ScenarioContext<ContextData> getScenarioContext() {
        return scenarioContext;
    }
}
package pageimplementations;

import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import utilities.Log;
import pageinterfaces.NavigationHeaderInterface;
import pageselectors.NavigationHeaderSelectors;

/**
 * The class {@code NavigationHeader} includes methods to perform actions on
 * navigation header sub page.
 */
public class NavigationHeader implements NavigationHeaderInterface {
    private final Page page;
    private final NavigationHeaderSelectors navigationHeaderSelectors;
    private final Logger logger;
    
    public NavigationHeader(final Page page) {
        this.page = page;
        navigationHeaderSelectors = new NavigationHeaderSelectors(page);
        logger = Log.getLogger(NavigationHeader.class);
    }
    
    /**
     * Method to navigate to the user settings.
     * 
     * @return Object of this page.
     */
    @Override
    public NavigationHeader goToManageUserSettingsFromNavigationHeader() {
        logger.info("Navigating to the user settings page from navigation header.");
        Locator dropdownLink = page.locator(navigationHeaderSelectors.drpDwnNavigationLink);
        dropdownLink.hover();
        dropdownLink.click();
        page.waitForLoadState();
        return this;
    }
}
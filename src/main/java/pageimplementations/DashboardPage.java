package pageimplementations;

import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import utilities.Log;
import utilities.PlaywrightUtils;

import pageinterfaces.LeftDrawerInterface;
import pageinterfaces.TopBannerInterface;
import pageselectors.DashboardPageSelectors;

/**
 * The class {@code DashboardPage} includes methods to perform actions on the
 * dashboard page which implements top banner and left
 * drawer interface.
 */
public class DashboardPage implements TopBannerInterface, LeftDrawerInterface {

    private final Page page;
    private final DashboardPageSelectors dashboardPageSelectors;
    private final Logger logger;
 
    public DashboardPage(final Page page) {
        this.page = page;
        dashboardPageSelectors = new DashboardPageSelectors(page);
        logger = Log.getLogger(DashboardPage.class);
    }

    /**
     * Method to get the page title from the top banner.
     * 
     * @return page title as a String
     */
    @Override
    public String getPageTitle(String title) {
        logger.info("Fetching page title from the top banner.");
        Locator pageTitleLocator = page.locator(dashboardPageSelectors.pageTitle);
        PlaywrightUtils.waitUntilTextMatches(pageTitleLocator, title, 10);
        return pageTitleLocator.textContent().trim();
    }

    /**
     * Method to get the sub page title from the top banner.
     * 
     * @return sub page title as a String
     */
    @Override
    public String getSubPageTitle(String subtitle) {
        logger.info("Fetching sub page title from the top banner.");
        Locator subPageTitleLocator = page.locator(dashboardPageSelectors.subPageTitle);
        PlaywrightUtils.waitUntilTextMatches(subPageTitleLocator, subtitle, 10);
        return subPageTitleLocator.textContent().trim();
    }

    /**
     * Method to navigate to the approvals and tasks page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage goToApprovalsAndTasksPage() {
        logger.info("Navigating to the approvals and tasks page.");
        Locator approvalsAndTasksLocator = page.locator(dashboardPageSelectors.approvalsAndTasks);
        
        approvalsAndTasksLocator.hover();
        approvalsAndTasksLocator.click();
        
        // Wait for navigation to complete
        page.waitForLoadState();
        
        return this;
    }

    /**
     * Method to navigate to the activity page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage goToActivityPage() {
        logger.info("Navigating to the activity page.");
        Locator activityLocator = page.locator(dashboardPageSelectors.activity);
   
        activityLocator.hover();
        activityLocator.click();
        
        // Wait for navigation to complete
        page.waitForLoadState();
        
        return this;
    }

    /**
     * Method to navigate to the RequestedByMe page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage gotoRequestedByMePage() {
        logger.info("Navigating to the Requested by me page.");
        Locator requestedByMeLocator = page.locator(dashboardPageSelectors.requestedByMe);
        
        requestedByMeLocator.hover();
        requestedByMeLocator.click();
        
        // Wait for navigation to complete
        page.waitForLoadState();
        
        return this;
    }

    /**
     * Method to navigate to the journeys page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage gotoJourneysPage() {
        logger.info("Navigating to the journeys page.");
        Locator journeysLocator = page.locator(dashboardPageSelectors.journeys);
        
        journeysLocator.hover();
        journeysLocator.click();
        
        // Wait for navigation to complete
        page.waitForLoadState();
        
        return this;
    }

    /**
     * Method to navigate to the assigned to me page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage gotoAssignedToMePage() {
        logger.info("Navigating to the assigned to me page.");
        Locator assignedToMeLocator = page.locator(dashboardPageSelectors.assignedToMe);
        
        assignedToMeLocator.hover();
        assignedToMeLocator.click();
        
        // Wait for navigation to complete
        page.waitForLoadState();
        
        return this;
    }

    /**
     * Method to navigate to the Default workspace Requests page.
     * 
     * @return Object of this page. 
     */
    @Override
    public DashboardPage goToDefaultWorkspacePage() {
        logger.info("Navigating to the default workspace page.");
        Locator defaultWorkspaceBtnLocator = page.locator(dashboardPageSelectors.DefaultWorkspaceBtn);
        Locator workspaceDropdownLocator = page.locator(dashboardPageSelectors.WorkspaceDropdown);
        
        // Click the button using JS
        PlaywrightUtils.clickWithJavaScript(defaultWorkspaceBtnLocator);
        
        // Force dropdown visible
        PlaywrightUtils.forceElementVisible(workspaceDropdownLocator);
        
        // Click again to ensure dropdown is fully displayed
        PlaywrightUtils.clickWithJavaScript(defaultWorkspaceBtnLocator);
        
        // Verify the dropdown is visible
        workspaceDropdownLocator.waitFor();
        
        return this;
    }

    /**
     * Method to navigate to the Default workspace Requests page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage goToDefaultRequestPage() {
        logger.info("Navigating to the default workspace requests page.");
        Locator requestsBtnLocator = page.locator(dashboardPageSelectors.RequestsBtn);
        
        requestsBtnLocator.hover();
        PlaywrightUtils.clickWithJavaScript(requestsBtnLocator);
        
        return this;
    }

    /**
     * Method to navigate to the Default workspace problems page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage goToDefaultProblemsPage() {
        logger.info("Navigating to the default workspace problems page.");
        Locator problemsBtnLocator = page.locator(dashboardPageSelectors.ProblemsBtn);
        
        problemsBtnLocator.hover();
        PlaywrightUtils.clickWithJavaScript(problemsBtnLocator);
        
        return this;
    }

    /**
     * Method to navigate to the Default workspace changes page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage goToDefaultChangesPage() {
        logger.info("Navigating to the default workspace changes page.");
        Locator changesBtnLocator = page.locator(dashboardPageSelectors.ChangesBtn);
        
        changesBtnLocator.hover();
       changesBtnLocator.click();
        
        return this;
    }

    /**
     * Method to navigate to the settings page.
     * 
     * @return Object of this page.
     */
    @Override
    public DashboardPage gotoSettingsPage() {
        logger.info("Navigating to the settings page.");
        Locator settingsLocator = page.locator(dashboardPageSelectors.settings);
        settingsLocator.hover();
        settingsLocator.click();
        page.waitForLoadState();
        return this;
    }
}
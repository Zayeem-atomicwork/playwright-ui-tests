package pageimplementations;

import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import utilities.Log;
import utilities.PlaywrightUtils;
import pageinterfaces.RequestDrawerInterface;
import pageselectors.RequestDrawerSelectors;

/**
 * The class {@code RequestDrawer} includes methods to perform request creation and
 * deletion operations via the drawer, implementing RequestDrawerInterface.
 */
public class RequestDrawer implements RequestDrawerInterface {

    private final Page page;
    private final RequestDrawerSelectors requestDrawerSelectors;
    private final Logger logger;
 
    public RequestDrawer(final Page page) {
        this.page = page;
        requestDrawerSelectors = new RequestDrawerSelectors(page);
        logger = Log.getLogger(RequestDrawer.class);
    }

    /**
     * Opens the create request drawer.
     * 
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer openCreateRequestDrawer() {
        logger.info("Opening create request drawer");
        PlaywrightUtils.untilPageLoadComplete(page); 
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.createButton));       
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.createRequestMenuItem));
        PlaywrightUtils.waitForElement(page.locator(requestDrawerSelectors.requestDrawer)); 
        return this;
    }

    /**
     * Opens the create problem drawer.
     * 
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer openReportProblemDrawer() {
        logger.info("Opening report problem drawer");
        
        // Ensure page is loaded
        PlaywrightUtils.untilPageLoadComplete(page);
        
        // Click the Create button
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.createButton));
        
        // Click "Report a problem" menu item
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.createProblemMenuItem));
        
        // Wait for drawer to appear
        PlaywrightUtils.waitForElement(page.locator(requestDrawerSelectors.problemDrawer));
        
        return this;
    }
    
    /**
     * Creates a new request with the specified subject.
     * 
     * @param subject The subject for the request
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer createNewRequest(String subject) {
        logger.info("Creating new request with subject: {}", subject);
        
        // Enter subject
        PlaywrightUtils.enterText(page.locator(requestDrawerSelectors.subjectInput), subject);
        
        // Submit the request
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.submitButton));
        
        return this;
    }
    
    /**
     * Gets the request ID from the breadcrumb in the request details page.
     * 
     * @return The request ID as a String
     */
    @Override
    public String getBreadcrumbRequestId() {
        logger.info("Getting request ID from breadcrumb");
        
        return PlaywrightUtils.getText(page.locator(requestDrawerSelectors.breadcrumbRequestId));
    }

    /**
     * Views the created request details by clicking view button.
     * 
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer viewCreatedRequest() {
        logger.info("Viewing created request");
        
        // Click the View button
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.viewButton));
        
        // Wait for page to load
        PlaywrightUtils.untilPageLoadComplete(page);
        
        // Wait for request ID element
        PlaywrightUtils.waitForElement(page.locator(requestDrawerSelectors.requestIdElement));
        
        return this;
    }

    /**
     * Deletes a request by moving it to trash.
     * 
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer deleteRequest() {
        logger.info("Deleting request (moving to trash)");
        
        // Wait for page to be stable
        PlaywrightUtils.untilPageLoadComplete(page);
        
        // Click the More (ellipsis) button
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.ellipsisButton));
        
        // Click Delete option
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.deleteOption));
        
        // Confirm deletion
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.deleteConfirmButton));
        
        return this;
    }

    /**
     * Permanently deletes a request from trash.
     * 
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer deleteRequestPermanently() {
        logger.info("Permanently deleting request from trash");
        
        // Wait for page to be stable
        PlaywrightUtils.untilPageLoadComplete(page);
        
        // Click Delete forever button
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.deleteForeverButton));
        
        // Confirm permanent deletion
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.deleteForeverConfirmButton));
        
        return this;
    }

    /**
     * Gets the current toast message text.
     * 
     * @return Toast message as a String
     */
    @Override
    public String getToastMessage() {
        logger.info("Getting toast message text");  
        return PlaywrightUtils.getText(page.locator(requestDrawerSelectors.toastMessage));
    }
    
    /**
     * Closes the toast message notification.
     * 
     * @return RequestDrawer for method chaining
     */
    @Override
    public RequestDrawer closeToastMessage() {
        logger.info("Closing toast message");
        
        // Use JavaScript click for toast close button to ensure it works
        PlaywrightUtils.safeClick(page.locator(requestDrawerSelectors.toastCloseButton));
        Locator toastLocator = page.locator(requestDrawerSelectors.toastMessage);
        PlaywrightUtils.waitForElementToDisappear(toastLocator);
        return this;
    }
   
    /**
     * Gets the status text from the request details page.
     * 
     * @return The status text displayed on the page
     */
    @Override
    public String getRequestStatus() {
        logger.info("Getting request status from details page");
        
        return PlaywrightUtils.getText(page.locator(requestDrawerSelectors.statusDisplay));
    }
   
    /**
     * Gets the subject text from the request details page.
     * 
     * @return The subject text displayed on the page
     */
    @Override
    public String getRequestSubject() {
        logger.info("Getting request subject from details page");
        
        return PlaywrightUtils.getText(page.locator(requestDrawerSelectors.subjectDisplay));
    }
}
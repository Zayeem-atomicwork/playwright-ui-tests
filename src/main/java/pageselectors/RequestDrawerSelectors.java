package pageselectors;

import com.microsoft.playwright.Page;

/**
 * The class {@code RequestDrawerSelectors} holds the selectors for the request drawer
 * and related operations.
 */
public class RequestDrawerSelectors {
    // Create request selectors
    public final String createButton = "//button[normalize-space()='Create']";
    public final String createRequestMenuItem = "li[role='menuitem']:has-text('Create a request')";
    public final String createProblemMenuItem = "li[role='menuitem']:has-text('Report a problem')";
    
    // Drawer selectors
    public final String requestDrawer = "div[role='dialog']:has-text('Create request')";
    public final String problemDrawer = "div[role='dialog']:has-text('Analyze a problem')";
    public final String subjectInput = "//div[@data-node='subject']//textarea[@id='subject']";
    public final String submitButton = "button:has-text('Submit')";
    
    // Toast selectors
    public final String toastMessage = "//div[@class='ant-notification-notice-message']";
    public final String toastCloseButton = "a[aria-label='Close']";
    public final String viewButton = "a:has-text('View')";
    
    // Request details selectors
    public final String requestIdElement = "li:last-child p";
    public final String breadcrumbRequestId = "li:last-child p";
    
    // Delete request selectors
    public final String ellipsisButton = "button[aria-label='More']";
    public final String deleteOption = "li[role='menuitem']:has-text('Delete')";
    public final String deleteConfirmButton = "div[role='dialog'] button:has-text('Delete')";
    public final String deleteForeverButton = "button:has-text('Delete forever')";
    public final String deleteForeverConfirmButton = "div[role='dialog'] button:has-text('Delete')";
    
    public final String statusDisplay = "//div[@id='status']";
    public final String subjectDisplay = "//textarea[@id='subject']";
    
    private final Page page;
    
    public RequestDrawerSelectors(final Page page) {
        this.page = page;
    }
}
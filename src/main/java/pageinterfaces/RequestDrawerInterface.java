package pageinterfaces;

import pageimplementations.RequestDrawer;

/**
 * The interface {@code RequestDrawerInterface} includes methods used to perform
 * request creation and deletion operations via the drawer.
 */
public interface RequestDrawerInterface {
    
    /**
     * Opens the create request drawer.
     * 
     * @return RequestDrawer for method chaining
     */
    RequestDrawer openCreateRequestDrawer();
    
    /**
     * Creates a new request with the specified subject.
     * 
     * @param subject The subject for the request
     * @return RequestDrawer for method chaining
     */
    RequestDrawer createNewRequest(String subject);
    
    /**
     * Gets the request ID from the breadcrumb in the request details page.
     * 
     * @return The request ID as a String
     */
    String getBreadcrumbRequestId();
    
    /**
     * Views the created request details by clicking view button.
     * 
     * @return RequestDrawer for method chaining
     */
    RequestDrawer viewCreatedRequest();
    
    /**
     * Deletes a request by moving it to trash.
     * 
     * @return RequestDrawer for method chaining
     */
    RequestDrawer deleteRequest();
    
    /**
     * Permanently deletes a request from trash.
     * 
     * @return RequestDrawer for method chaining
     */
    RequestDrawer deleteRequestPermanently();
    
    /**
     * Gets the current toast message text.
     * 
     * @return Toast message as a String
     */
    String getToastMessage();
    
    /**
     * Closes the toast message notification.
     * 
     * @return RequestDrawer for method chaining
     */
    RequestDrawer closeToastMessage();
    
    
 // In RequestDrawerInterface
    String getRequestStatus();
   
 // In RequestDrawerInterface
    String getRequestSubject();

	/**
	 * Opens the create request drawer.
	 * 
	 * @return RequestDrawer for method chaining
	 */
	RequestDrawer openReportProblemDrawer();
}

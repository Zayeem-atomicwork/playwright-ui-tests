package pagemessages;

/**
 * The class {@link RequestDrawerPageMsgs} holds request drawer messages and constants.
 */
public class RequestDrawerPageMsgs {

    // Toast notification messages
    public static final String REQUEST_CREATED_NOTIFICATION = "Request '%s' created successfully";
    public static final String PROBLEM_CREATED_NOTIFICATION = "Problem '%s' created successfully";
    public static final String REQUEST_MOVED_TO_TRASH_NOTIFICATION = "%s moved to Trash successfully";
    public static final String REQUEST_DELETED_FOREVER_NOTIFICATION = "Request has been deleted forever";
    
    // Dialog messages
    public static final String DELETE_CONFIRMATION_MESSAGE = "Once deleted, this request will be accessible only in Trash";
    public static final String DELETE_FOREVER_CONFIRMATION_MESSAGE = "Are you sure? Once deleted, this request cannot be restored later.";
    
    // Regex patterns
    public static final String REQUEST_ID_REGEX = ".*'([A-Za-z]+-\\d+)'.*";

	public static final String REQUEST_STATUS_OPEN = "Open";

    private RequestDrawerPageMsgs() {
    }
}
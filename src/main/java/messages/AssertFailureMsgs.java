package messages;


/**
 * The class {@link AssertFailureMsgs} holds assertion failure messages.
 */
public final class AssertFailureMsgs {

    public static final String NOT_ON_LOGIN_PAGE = "The user is not on the login page.";
    public static final String NOT_ON_ASSIGNED_TO_ME_PAGE = "The user is not on the assigned to me page.";
    public static final String NOT_ON_ACTIVITY_PAGE = "The user is not on the activity page.";
    public static final String NOT_ON_APPROVALSANDTASKS_PAGE = "The user is not on the approvals and tasks page.";
    public static final String NOT_ON_REQUESTEDBYME_PAGE = "The user is not on the requested by me page.";
    public static final String NOT_ON_JOURNEYS_PAGE = "The user is not on the support page.";
    public static final String NOT_ON_DEFAULT_REQUEST_PAGE = "The user is not on the default requests page.";
    public static final String NOT_ON_DEFAULT_PROBLEM_PAGE = "The user is not on the default problems page.";
    public static final String NOT_ON_DEFAULT_CHANGES_PAGE = "The user is not on the default changes page.";
    public static final String INCORRECT_WORKSPACE_NOTIFICATION = "Workspace message is incorrect";
    public static final String NOT_ON_MANAGE_USER_SETTINGS_PAGE = "The user is not on the team management page";
    public static final String NOT_ON_WORKSPACE_SETTINGS_PAGE = "The user is not on the workspace settings page";
    public static final String NOT_ON_GENERAL_SETTINGS_PAGE = "The user is not on the General settings page";
    public static final String NEW_USER_NOT_CREATED = "The user is not created";
    public static final String INCORRECT_RESENT_INVITE = "Invitation re-sent message is incorrect";
    public static final String INCORRECT_ACCESS_DISABLED_NOTIFICATION = "Access disabled message is incorrect";
    public static final String INCORRECT_WORKSPACE = "In-correct workspace";
    public static final String INCORRECT_WORKSPACE_NAME_IN_DELETE_MODAL = "In-correct workspace name in the modal";
    public static final String INCORRECT_EDIT_USER_FORM_TITLE = "Edit user form title is incorrect";
 // Add to existing AssertFailureMsgs class
    public static final String REQUEST_CREATION_FAILED = "Request creation failed or toast message doesn't match expected";
    public static final String REQUEST_ID_NOT_FOUND = "Request ID was not recorded";
    public static final String INVALID_REQUEST_ID_FORMAT = "Request ID does not match expected format";
    public static final String REQUEST_ID_MISMATCH = "Request ID in breadcrumb doesn't match created request ID";
    public static final String REQUEST_MOVE_TO_TRASH_FAILED = "Request move to trash failed or toast message doesn't match expected";
    public static final String REQUEST_PERMANENT_DELETION_FAILED = "Request permanent deletion failed or toast message doesn't match expected";
    public static final String VIEW_BUTTON_NOT_FOUND = "View button not found on toast notification";
    public static final String ELLIPSIS_BUTTON_NOT_FOUND = "Ellipsis (More) button not found on request details page";
    public static final String DELETE_FOREVER_BUTTON_NOT_FOUND = "Delete forever button not found in trash view";
    
 // In AssertFailureMsgs class
    public static final String REQUEST_SUBJECT_MISMATCH = "Request subject doesn't match expected value";
    public static final String REQUEST_STATUS_MISMATCH = "Request status doesn't match expected value";

    private AssertFailureMsgs() {
        super();
    }
}
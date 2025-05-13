package pageselectors;

import com.microsoft.playwright.Page;

/**
 * The class {@code DashboardPageSelectors} holds the selectors of top
 * banner, left drawer and calendar container and is responsible for
 * initializing them.
 */
public class DashboardPageSelectors {
    // Top banner selectors
    public final String pageTitle = "#app-layout > header h1";
    public final String subPageTitle = "div[id='app-layout'] li:nth-child(3)";
    
    // Left drawer navigation selectors
    public final String approvalsAndTasks = "//a[@href='/approvals-and-tasks']";
    public final String activity = "a[href='/activity']";
    public final String requestedByMe = "a[href='/requested-by-me']";
    public final String journeys = "//a[@href='/journeys']";
    public final String assignedToMe = "a[href='/assigned-to-me']";
    public final String settings = "a[href='/settings']";
    
    // Workspace selectors
    public final String DefaultWorkspaceBtn = "//div[text()='Workspaces']/ancestor::li//div[text()='Default workspace']";
    public final String WorkspaceDropdown = "//li[@role='none']//ul";
    public final String RequestsBtn = "a[href='/workspaces/3810/requests']";
    public final String ProblemsBtn = "a[href='/workspaces/3810/problems']";
    public final String ChangesBtn = "a[href='/workspaces/3810/changes']";
    public final String userSettings = "a[href='/settings/users']";

    private final Page page;

    public DashboardPageSelectors(final Page page) {
        this.page = page;
    }
}
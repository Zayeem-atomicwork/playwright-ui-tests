package pageselectors;

import com.microsoft.playwright.Page;

/**
 * The class {@code SettingsPageSelectors} holds the selectors of the
 * settings page and is responsible for initializing them.
 */
public class SettingsPageSelectors extends NavigationHeaderSelectors {

    public final String advancedSettings = "a[href='/settings/advanced']";
    public final String scoringModel = "a[href='/settings/scoring']";
    public final String teamManagement = "a[href='/settings/team']";
    public final String userSettings = "//a[normalize-space()='Users']";
    public final String workspaceSettings = "//a[@href='/settings/workspaces']";
    public final String SettingsPageTitle = "#app-layout > header h1";
    public final String addWorkspaceBtn = "//a[normalize-space()='Add']";
    public final String workspacenameInputbx = "//input[@id='name']";
    public final String addWorkspaceBtnAfterInput = "//button[normalize-space()='Add']";
    public final String addedWorkspaceFromList = "//h5[normalize-space()='Atomicwork']";
    public final String workspaceToast = "//div[@class='ant-notification-notice-message']";
    public final String workspaceDeletedToast = "//div[@class='ant-notification-notice-message'][contains(.,'Workspace Deleted successfully')]";
    public final String workspaceEllipsis = "//tr[contains(.,'Atomicwork')]//button[1]";
    public final String workspaceEllipsisMenu = ".ant-dropdown-menu.ant-dropdown-menu-root.ant-dropdown-menu-vertical.ant-dropdown-menu-light.css-1nmg3s7";
    public final String deleteWorkspaceBtn = "//span[normalize-space()='Delete workspace']";
    public final String deleteModal = "div[class='ant-flex css-1nmg3s7 ant-flex-align-stretch ant-flex-vertical'] p strong";
    public final String copyWorkspaceNameBtn = "//span[@aria-label='copy']";
    public final String deleteWorkspaceInputBx = "//input[@id='delete-workspace_delete-workspace']";
    public final String confirmDeleteWorkspaceBtn = "//button[normalize-space()='Confirm']";
    public final String closeToastMessage = "a[aria-label='Close']";

    public SettingsPageSelectors(final Page page) {
        super(page);
    }
}
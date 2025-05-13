package stepdefinitions.atomicworkportal;

import java.awt.HeadlessException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.testng.Assert;

import cucumber.TestContext;
import enums.ContextData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messages.AssertFailureMsgs;
import pageimplementations.DashboardPage;
import pageimplementations.NavigationHeader;
import pageimplementations.SettingsPage;
import pagemessages.SettingsPageMsgs;

public class SettingsPageSteps {

    private final SettingsPage settingsPage;
    private final DashboardPage dashboardPage;
    private final NavigationHeader navigationHeader;
    private final TestContext testContext;

    public SettingsPageSteps(final TestContext testContext) {
        this.testContext = testContext;
        settingsPage = testContext.getPageObjectManager().getSettingsPage();
        dashboardPage = testContext.getPageObjectManager().getDashboardPage();
        navigationHeader = testContext.getPageObjectManager().getNavigationHeader();
    }

    @When("^the user navigates to the settings page$")
    public void goToSettingsPageFromHomePage() {
        dashboardPage.gotoSettingsPage();
    }

    @Then("^verify the user is on the general settings page$")
    public void verifyUserOnGeneralPage() {
        Assert.assertEquals(
            settingsPage.getSettingsPageTitle(SettingsPageMsgs.GENERAL_SETTING_PAGE_TITLE),
            SettingsPageMsgs.GENERAL_SETTING_PAGE_TITLE,
            AssertFailureMsgs.NOT_ON_GENERAL_SETTINGS_PAGE
        );
    }

    @When("^the user navigates to the user settings page from navigation header$")
    public void goToOverviewPage() {
        navigationHeader.goToManageUserSettingsFromNavigationHeader();
    }

    @Then("^verify the user is on the manage user settings page$")
    public void VerifyUserOnManageUserSettingsPage() {
        Assert.assertEquals(settingsPage.getSettingsPageTitle(SettingsPageMsgs.MANAGE_USER_SETTING_PAGE_TITLE), 
            SettingsPageMsgs.MANAGE_USER_SETTING_PAGE_TITLE,
                AssertFailureMsgs.NOT_ON_MANAGE_USER_SETTINGS_PAGE);
    }

    @When("^the user navigates to the users page$")
    public void goToManageUserSettingsPage() {
        settingsPage.goToManageUserSettings();
    }

    @When("^the user navigates to the workspace settings page$")
    public void goToSettingsWorkspacePage() {
        settingsPage.goToWorkspaceSettingPage();
    }

    @Then("^verify the user is on the workspace settings page$")
    public void VerifyUserOnWorkspaceSettingsPage() {
        Assert.assertEquals(settingsPage.getSettingsPageTitle(SettingsPageMsgs.WORKSPACE_SETTING_PAGE_TITLE), 
            SettingsPageMsgs.WORKSPACE_SETTING_PAGE_TITLE,
                AssertFailureMsgs.NOT_ON_WORKSPACE_SETTINGS_PAGE);
    }

    @Then("^verify the workspace created toast message is displayed$")
    public void verifyWorkspaceCreatedNotification() {
        Assert.assertEquals(settingsPage.WorkspaceNotificationToast(), 
            SettingsPageMsgs.WORKSPACE_CREATED_NOTIFICATION,
                AssertFailureMsgs.INCORRECT_WORKSPACE_NOTIFICATION);
    }

    @Then("^verify the workspace already exists toast message is displayed$")
    public void verifyWorkspaceExistsNotification() {
        Assert.assertEquals(settingsPage.WorkspaceNotificationToast(), 
            SettingsPageMsgs.WORKSPACE_ALREADY_EXISTS_NOTIFICATION,
                AssertFailureMsgs.INCORRECT_WORKSPACE_NOTIFICATION);
    }

    @Then("^verify the workspace deleted toast message is displayed$")
    public void verifyWorkspaceDeletedNotification() {
        Assert.assertEquals(settingsPage.WorkspaceDeletedNotificationToast(), 
            SettingsPageMsgs.WORKSPACE_DELETED_NOTIFICATION,
                AssertFailureMsgs.INCORRECT_WORKSPACE_NOTIFICATION);
    }

    @Then("^verify the workspace is added to the list$")
    public void verifyNewWorkpaceCreated() {
        Assert.assertEquals(settingsPage.isNewWorkspaceCreated(),
                testContext.getScenarioContext().getContext(ContextData.NEW_WORKSPACE),        
                AssertFailureMsgs.INCORRECT_WORKSPACE);
    }

    @When("^the user taps on ellipsis and delete$")
    public void deleteOption() throws Exception {
        settingsPage.closeToastMessage();
        settingsPage.deleteWorkspace();
    }

    @When("^the user enter the workspace name and confirm$")
    public void deleteWorkspaceConfirm() throws HeadlessException, UnsupportedFlavorException, IOException {
        settingsPage.deleteWorkspaceConfirmation();
    }

    @Then("^verify the workspace name in delete modal is displayed$")
    public void verifyDeleteModal() {
        Assert.assertEquals(settingsPage.workspaceDeleteModal(),
                testContext.getScenarioContext().getContext(ContextData.NEW_WORKSPACE),        
                AssertFailureMsgs.INCORRECT_WORKSPACE_NAME_IN_DELETE_MODAL);
    }
   
	@When("^the user enters the name and add the workspace$")
	public void addingWorkspace() {
		String name = "Atomicwork";
		settingsPage.addWorkspace(name);
		testContext.getScenarioContext().setContext(ContextData.NEW_WORKSPACE, name);
	}
	@When("^the user enters the name that already exists$")
	public void addingAlreadyExistsWorkspace() {
		String name = "Default workspace";
		settingsPage.addWorkspace(name);
		testContext.getScenarioContext().setContext(ContextData.NEW_WORKSPACE, name);
	}
}
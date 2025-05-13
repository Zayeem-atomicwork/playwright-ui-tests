package pageinterfaces;

import java.awt.HeadlessException;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import pageimplementations.SettingsPage;

/**
 * The interface {@code SettingsPageInterface} includes methods used to perform
 * some action on the settings page.
 */
public interface SettingsPageInterface extends NavigationHeaderInterface {

	SettingsPage goToManageUserSettings();

	SettingsPage goToWorkspaceSettingPage();

	//String getSettingsPageTitle();

	SettingsPage addWorkspace(String name);

	String isNewWorkspaceCreated();

	String WorkspaceNotificationToast();

	SettingsPage deleteWorkspace() throws InterruptedException, Exception;

	String workspaceDeleteModal();

	SettingsPage deleteWorkspaceConfirmation() throws HeadlessException, UnsupportedFlavorException, IOException;

	SettingsPage closeToastMessage();

	String WorkspaceDeletedNotificationToast();

	/**
	 * Method to get the page title.
	 *
	 * @param expectedTitle The title text to wait for
	 * @return The page title text
	 */
	String getSettingsPageTitle(String expectedTitle);


	//String isWorkspaceCreatedToast();

}

package pageimplementations;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.LoadState;

import utilities.Log;
import utilities.PlaywrightUtils;

import pageinterfaces.SettingsPageInterface;
import pageselectors.SettingsPageSelectors;

/**
 * The class {@code SettingsPage} includes methods to perform actions on the
 * settings page.
 */
public class SettingsPage extends NavigationHeader implements SettingsPageInterface {

    private final Page page;
    private final SettingsPageSelectors settingsPageSelectors;
    private final Logger logger;

    public SettingsPage(final Page page) {
        super(page);
        this.page = page;
        settingsPageSelectors = new SettingsPageSelectors(page);
        logger = Log.getLogger(SettingsPage.class);
    }

    /**
     * Method to get the page title.
     *
     * @param expectedTitle The title text to wait for
     * @return The page title text
     */
    @Override
    public String getSettingsPageTitle(String expectedTitle) {
        logger.info("Fetching the settings page title.");
        Locator titleLocator = page.locator(settingsPageSelectors.SettingsPageTitle);
        titleLocator.waitFor();
        PlaywrightUtils.waitUntilTextMatches(titleLocator, expectedTitle, 10);
        return titleLocator.textContent().trim();
    }

    /**
     * Method to navigate to the user settings.
     * 
     * @return Object of this page.
     */
    @Override
    public SettingsPage goToManageUserSettings() {
        logger.info("Navigating to the user settings page.");
        Locator userSettingsLocator = page.locator(settingsPageSelectors.userSettings);
        
        try {
            // Wait for element to be visible before clicking
            userSettingsLocator.waitFor();
            
            // Try hover first
            userSettingsLocator.hover();
            
            // Then click
            userSettingsLocator.click();
            
            // Wait for navigation to complete
            page.waitForLoadState(LoadState.NETWORKIDLE);
        } catch (Exception e) {
            logger.warn("Standard click failed, trying JavaScript click: {}", e.getMessage());
            try {
                // JavaScript click as fallback
                userSettingsLocator.evaluate("element => element.click()");
                page.waitForLoadState(LoadState.NETWORKIDLE);
            } catch (Exception ex) {
                logger.error("Failed to navigate to user settings: {}", ex.getMessage());
            }
        }
        
        return this;
    }

    /**
     * Method to navigate to the workspace settings.
     * 
     * @return Object of this page.
     */
    @Override
    public SettingsPage goToWorkspaceSettingPage() {
        logger.info("Navigating to the workspace settings page.");
        Locator workspaceSettingsLocator = page.locator(settingsPageSelectors.workspaceSettings);
        
       // try {
            // Wait for element to be visible before clicking
            workspaceSettingsLocator.waitFor();
            
            // Try hover first
            workspaceSettingsLocator.hover();
            
            // Then click
            workspaceSettingsLocator.click();
            
            // Wait for navigation to complete
            page.waitForLoadState();
//        } catch (Exception e) {
//            logger.warn("Standard click failed, trying JavaScript click: {}", e.getMessage());
//            try {
//                // JavaScript click as fallback
//                workspaceSettingsLocator.evaluate("element => element.click()");
//                page.waitForLoadState(LoadState.NETWORKIDLE);
//            } catch (Exception ex) {
//                logger.error("Failed to navigate to workspace settings: {}", ex.getMessage());
//            }
        
        return this;
    }

    /**
     * Method to input name and add workspace.
     * 
     * @return Object of this page.
     */
    @Override
    public SettingsPage addWorkspace(String name) {
        logger.info("Adding workspace with name: {}", name);
        
        // Click Add Workspace button
        try {
            Locator addWorkspaceBtn = page.locator(settingsPageSelectors.addWorkspaceBtn);
            //addWorkspaceBtn.waitFor();
            //addWorkspaceBtn.hover();
            addWorkspaceBtn.click();
            
            // Enter workspace name - wait for input field to be ready
            Locator nameInput = page.locator(settingsPageSelectors.workspacenameInputbx);
            nameInput.waitFor();
            nameInput.fill(name);
            
            // Click add button after input
            Locator addBtn = page.locator(settingsPageSelectors.addWorkspaceBtnAfterInput);
            addBtn.waitFor();
            addBtn.hover();
            addBtn.click();
            
            // Wait for operation to complete
            page.waitForLoadState(LoadState.NETWORKIDLE);
        } catch (Exception e) {
            logger.error("Failed to add workspace: {}", e.getMessage());
        }
        
        return this;
    }

    /**
     * Method to get added workspace from workspace Settings Page.
     * 
     * @return workspace as string
     */
    @Override
    public String isNewWorkspaceCreated() {
        logger.info("Fetching added workspace from the list.");
        Locator workspaceLocator = page.locator(settingsPageSelectors.addedWorkspaceFromList);
            // Wait for workspace to appear in list
            workspaceLocator.waitFor();
            return workspaceLocator.textContent().trim();
        }

    /**
     * Method to get workspace created notification.
     * 
     * @return workspace created notification as string.
     */
    @Override
    public String WorkspaceNotificationToast() {
        logger.info("Fetching the workspace created toast message");
        Locator toastLocator = page.locator(settingsPageSelectors.workspaceToast);
        toastLocator.waitFor(new Locator.WaitForOptions().setTimeout(15000));
        return toastLocator.textContent().trim();
        }

    /**
     * Method to get workspace deleted notification.
     * 
     * @return workspace deleted notification as string.
     */
    @Override
    public String WorkspaceDeletedNotificationToast() {
        logger.info("Fetching the workspace deleted toast message");
        Locator toastLocator = page.locator(settingsPageSelectors.workspaceDeletedToast);
        toastLocator.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        return toastLocator.textContent().trim();

    }

    /**
     * Method to close the toast notification.
     * 
     * @return Object of this page.
     */
    @Override
    public SettingsPage closeToastMessage() {
        logger.info("Closing the toast notification");
        try {
            Locator closeBtn = page.locator(settingsPageSelectors.closeToastMessage);
            closeBtn.waitFor();
            closeBtn.evaluate("element => element.click()");
            logger.info("Toast close button clicked successfully");
        } catch (Exception e) {
            logger.error("Failed to close the toast message: {}", e.getMessage());
        }
        return this;
    }

    /**
     * Method to delete workspace from the system.
     * 
     * @return Object of this page
     * @throws InterruptedException 
     */
    @Override
    public SettingsPage deleteWorkspace() throws InterruptedException {
        logger.info("Deleting newly created workspace from the system.");
        
        // Retry logic implemented directly
        int maxAttempts = 3;
        int attemptCount = 0;
        int delay = 500;
        
        while (attemptCount < maxAttempts) {
            try {
                // Get locators for the elements
                Locator ellipsisLocator = page.locator(settingsPageSelectors.workspaceEllipsis);
                Locator menuLocator = page.locator(settingsPageSelectors.workspaceEllipsisMenu);
                Locator deleteBtn = page.locator(settingsPageSelectors.deleteWorkspaceBtn);
                
                // Click the ellipsis button
                ellipsisLocator.waitFor();
                ellipsisLocator.hover();
                ellipsisLocator.click();
                
                // Force the menu to be visible
                PlaywrightUtils.forceElementVisible(menuLocator);
                
                // Wait for menu to be visible
                menuLocator.waitFor();
                
                // Click the delete button
                deleteBtn.waitFor();
                deleteBtn.evaluate("element => element.click()");
                
                // If we get here, operation was successful
                logger.info("Successfully clicked delete button");
                break;
            } catch (Exception e) {
                attemptCount++;
                logger.warn("Attempt {} failed: {}. Retrying in {}ms", 
                        attemptCount, e.getMessage(), delay);
                
                if (attemptCount >= maxAttempts) {
                    logger.error("All {} attempts failed", maxAttempts);
                    break;
                }
                
                // Wait before retrying
                Thread.sleep(delay);
                
                // Increase delay for next attempt
                delay *= 2;
            }
        }
        
        return this;
    }

    /**
     * Method to get text from delete workspace modal.
     * 
     * @return workspace as string
     */
    @Override
    public String workspaceDeleteModal() {
        logger.info("Fetching text from delete workspace modal.");
        Locator modalLocator = page.locator(settingsPageSelectors.deleteModal);
        
        try {
            // Wait for modal to be visible
            modalLocator.waitFor();
            return modalLocator.textContent().trim();
        } catch (Exception e) {
            logger.error("Failed to get modal text: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Method to delete workspace from the system.
     * 
     * @return Object of this page
     * @throws IOException 
     * @throws UnsupportedFlavorException 
     * @throws HeadlessException  
     */
    @Override
    public SettingsPage deleteWorkspaceConfirmation() throws HeadlessException, UnsupportedFlavorException, IOException {
        logger.info("Confirming workspace deletion");
        
        try {
            // Copy workspace name
            Locator copyBtn = page.locator(settingsPageSelectors.copyWorkspaceNameBtn);
            copyBtn.waitFor();
            copyBtn.hover();
            copyBtn.click();
            
            // Get text from clipboard
            String myText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            logger.info("Copied workspace name: {}", myText);
            
            // Enter workspace name in input box - wait for it to be ready
            Locator inputBox = page.locator(settingsPageSelectors.deleteWorkspaceInputBx);
            inputBox.waitFor();
            inputBox.fill(myText);
            
            // Confirm deletion
            Locator confirmBtn = page.locator(settingsPageSelectors.confirmDeleteWorkspaceBtn);
            confirmBtn.waitFor();
            confirmBtn.click();
            
            // Wait for operation to complete
            page.waitForLoadState(LoadState.NETWORKIDLE);
        } catch (Exception e) {
            logger.error("Failed to confirm workspace deletion: {}", e.getMessage());
        }
        
        return this;
    }
}
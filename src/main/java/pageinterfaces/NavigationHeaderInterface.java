package pageinterfaces;

import pageimplementations.NavigationHeader;

/**
 * The interface {@code NavigationHeaderInterface} includes methods used to perform
 * some action on navigation header sub page.
 */
public interface NavigationHeaderInterface {
    
    /**
     * Method to navigate to the user settings from navigation header.
     * 
     * @return Object of NavigationHeader
     */
    NavigationHeader goToManageUserSettingsFromNavigationHeader();
}
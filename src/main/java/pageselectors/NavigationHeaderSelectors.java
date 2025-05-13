package pageselectors;

import com.microsoft.playwright.Page;

/**
 * The class {@code NavigationHeaderSelectors} holds the selectors of
 * navigation header sub page and is responsible for initializing them.
 */
public class NavigationHeaderSelectors {
	
    public final String drpDwnNavigationLink = "//span[@class='account-logo-title']";
    public final String userDropdown = ".ant-dropdown-menu.ant-dropdown-menu-root.ant-dropdown-menu-vertical.ant-dropdown-menu-light.css-x1ch0w";
    public final String userSettings = "//a[normalize-space()='Users']";
    
    public NavigationHeaderSelectors(final Page page) {
    }
}
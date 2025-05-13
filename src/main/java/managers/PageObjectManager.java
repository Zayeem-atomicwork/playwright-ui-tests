package managers;

import com.microsoft.playwright.Page;

import pageimplementations.DashboardPage;
import pageimplementations.LoginPage;
import pageimplementations.NavigationHeader;
import pageimplementations.RequestDrawer;
import pageimplementations.SettingsPage;

/**
 * The class {@code PageObjectManager} includes methods to initialize the step
 * library page's object and makes sure that same object is not initialized
 * again.
 */
public class PageObjectManager {

	private final Page page;
	private LoginPage loginPage;
	private NavigationHeader navigationHeader;
	private DashboardPage dashboardPage;
	private SettingsPage settingsPage;
	private RequestDrawer requestDrawer;

	public PageObjectManager(final Page page) {
		this.page = page;
	}

	/**
	 * Method to initialize the object of {@code LoginPage} of Atomicwork portal.
	 * 
	 * @return {@code LoginPage} object
	 */
	public LoginPage getLoginPage() {
		return (loginPage == null) ? loginPage = new LoginPage(page) : loginPage;
	}

	/**
	 * Method to initialize the object of {@code NavigationHeader} sub page of
	 * Atomicwork portal.
	 * 
	 * @return {@code NavigationHeader} object
	 */
	public NavigationHeader getNavigationHeader() {
		return (navigationHeader == null) ? navigationHeader = new NavigationHeader(page) : navigationHeader;
	}

	/**
	 * Method to initialize the object of {@code DashboardPage} page of Atomicwork
	 * portal.
	 * 
	 * @return {@code DashboardPage} object
	 */
	public DashboardPage getDashboardPage() {
		return (dashboardPage == null) ? dashboardPage = new DashboardPage(page) : dashboardPage;
	}

	/**
	 * Method to initialize the object of {@code SettingsPage} page of Atomicwork
	 * portal.
	 * 
	 * @return {@code SettingsPage} object
	 */
	public SettingsPage getSettingsPage() {
		return (settingsPage == null) ? settingsPage = new SettingsPage(page) : settingsPage;
	}

	/**
	 * Method to initialize the object of {@code RequestDrawerPage} page of Atomicwork
	 * portal.
	 * 
	 * @return {@code RequestDrawerPage} object
	 */
	public RequestDrawer getRequestDrawer() {
	    return (requestDrawer == null) ? requestDrawer =  new RequestDrawer(page) : requestDrawer;
	}
}
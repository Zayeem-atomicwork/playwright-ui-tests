package managers;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.ViewportSize;
import org.apache.logging.log4j.Logger;
import utilities.Log;

import java.util.Arrays;

import dataproviders.ConfigFileReader;

/**
 * The class {@code PlaywrightManager} is responsible to initialize the required
 * Playwright, Browser, BrowserContext, and Page objects.
 */
public class PlaywrightManager {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;
    private static Page page;
    private static PlaywrightManager playwrightManager;
    private static final ConfigFileReader configFileReader = new ConfigFileReader();
    private static final Logger logger = Log.getLogger(PlaywrightManager.class);
    private static boolean isInitialized = false;

    private PlaywrightManager() {
        // Private constructor for singleton pattern
        if (!isInitialized) {
            initializePlaywright();
        }
    }

    /**
     * Method to get instance of the PlaywrightManager.
     * 
     * @return instance of PlaywrightManager
     */
    public static synchronized PlaywrightManager getInstance() {
        if (playwrightManager == null) {
            playwrightManager = new PlaywrightManager();
        }
        return playwrightManager;
    }

    /**
     * Initialize Playwright resources. Made synchronized to prevent race conditions.
     */
    private synchronized void initializePlaywright() {
        if (isInitialized) {
            logger.info("Playwright already initialized");
            return;
        }

        logger.info("Initializing Playwright resources");
        try {
            // Create Playwright instance
            playwright = Playwright.create();
            logger.info("Playwright instance created");
            
            // Create browser
            browser = createBrowser();
            logger.info("Browser created: {}", configFileReader.getBrowserType());
            
            // Create browser context
            browserContext = createBrowserContext();
            logger.info("Browser context created");
            
            isInitialized = true;
            logger.info("Playwright initialization completed successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Playwright: {}", e.getMessage(), e);
            
            // Clean up any resources that were created
            closeAllResources();
            
            // Rethrow the exception
            throw new RuntimeException("Failed to initialize Playwright", e);
        }
    }

    /**
     * Method to return Page object. This Page instance will have lifetime
     * of the test. Existing Page object will be returned else new object will
     * be created and returned.
     * 
     * @return Page object
     */
    public synchronized Page getPage() {
        if (page == null) {
            if (browserContext == null) {
                logger.error("Browser context is null, cannot create page");
                throw new IllegalStateException("Browser context is null, cannot create page");
            }
            
            try {
                logger.info("Creating new page");
                page = browserContext.newPage();
                
                // Set timeout (equivalent to implicit wait in Selenium)
                page.setDefaultTimeout(configFileReader.getImplicitlyWait() * 1000);
                
                logger.info("Page created successfully");
            } catch (Exception e) {
                logger.error("Failed to create page: {}", e.getMessage(), e);
                throw e;
            }
        }
        return page;
    }

    /**
     * Closes only the current page without closing the browser or context.
     * This is useful between scenarios.
     */
    public synchronized void closePage() {
        try {
            if (page != null) {
                page.close();
                logger.info("Page closed");
                page = null;
            }
        } catch (Exception e) {
            logger.error("Error closing page: {}", e.getMessage());
            page = null;
        }
    }

    /**
     * Method to create Browser object based on the type of browser.
     * 
     * @return Browser object
     */
    private Browser createBrowser() {
        // Create Playwright browser launch options
        com.microsoft.playwright.BrowserType.LaunchOptions launchOptions = new com.microsoft.playwright.BrowserType.LaunchOptions();
        
        // Check for headless mode override from command line
        String headlessParam = System.getProperty("headless");
        boolean isHeadless;
        
        if (headlessParam != null) {
            // Use command line parameter if provided
            isHeadless = Boolean.parseBoolean(headlessParam);
            logger.info("Using command line headless setting: {}", isHeadless);
        } else {
            // Otherwise use config file setting
            isHeadless = configFileReader.isHeadless();
            logger.info("Using config file headless setting: {}", isHeadless);
        }
        
        // Set headless mode
        launchOptions.setHeadless(isHeadless);
        
        // Enhanced browser arguments for stability
        launchOptions.setArgs(Arrays.asList(
            "--disable-gpu",
            "--no-sandbox",
            "--disable-dev-shm-usage",
            "--disable-infobars",
            "--disable-extensions",
            "--disable-popup-blocking"
        ));
        
        // Set browser channel (use stable version)
        launchOptions.setChannel("chrome");
        
        // Launch browser based on type
        switch (configFileReader.getBrowserType()) {
            case CHROME:
                logger.info("Launching Chrome browser (headless: {})", isHeadless);
                return playwright.chromium().launch(launchOptions);
            case WEBKIT:
                logger.info("Launching WebKit browser (headless: {})", isHeadless);
                return playwright.webkit().launch(launchOptions);
            case FIREFOX:
                logger.info("Launching Firefox browser (headless: {})", isHeadless);
                return playwright.firefox().launch(launchOptions);
            default:
                throw new RuntimeException(String.format("Unsupported browser: %s", configFileReader.getBrowserType()));
        }
    }

    /**
     * Method to create BrowserContext with desired capabilities.
     * 
     * @return BrowserContext object
     */
    private BrowserContext createBrowserContext() {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        
        // Set viewport size
        ViewportSize viewportSize = configFileReader.getBrowserDimension();
        contextOptions.setViewportSize(viewportSize.width, viewportSize.height);
        
        // Configure browser capabilities
        contextOptions.setIgnoreHTTPSErrors(true);
        contextOptions.setJavaScriptEnabled(true);
        
        // Create browser context
        BrowserContext context = browser.newContext(contextOptions);
        
        // Clear cookies if needed
        if (configFileReader.deleteCookies()) {
            context.clearCookies();
        }
        
        return context;
    }

    /**
     * Method to navigate to the login page URL.
     */
    public void navigateToLoginPage() {
        try {
            logger.info("Navigating to login page: {}", configFileReader.getLoginPageUrl());
            Page currentPage = getPage();
            currentPage.navigate(configFileReader.getLoginPageUrl());
            
            // Wait for page to load fully
            try {
                currentPage.waitForLoadState(LoadState.NETWORKIDLE, 
                    new Page.WaitForLoadStateOptions().setTimeout(30000));
                logger.info("Login page loaded successfully (network idle)");
            } catch (Exception e) {
                logger.warn("Timeout waiting for network idle: {}", e.getMessage());
                
                // Try waiting for DOM content at least
                try {
                    currentPage.waitForLoadState(LoadState.DOMCONTENTLOADED);
                    logger.info("Login page DOM loaded successfully");
                } catch (Exception ex) {
                    logger.warn("Timeout waiting for DOM content: {}", ex.getMessage());
                }
            }
            
            // Try to wait for a common element that should be on any login page
            try {
                currentPage.waitForSelector("input, form, button", 
                    new Page.WaitForSelectorOptions().setTimeout(10000));
                logger.info("Login page elements detected");
            } catch (Exception e) {
                logger.warn("Could not detect common login page elements: {}", e.getMessage());
            }
        } catch (Exception e) {
            logger.error("Failed to navigate to login page: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Method to navigate to a specific URL.
     * 
     * @param url URL to navigate to
     */
    public void navigateTo(String url) {
        try {
            logger.info("Navigating to URL: {}", url);
            Page currentPage = getPage();
            currentPage.navigate(url);
            
            // Wait for page to load fully
            try {
                currentPage.waitForLoadState(LoadState.NETWORKIDLE);
                logger.info("Page loaded successfully");
            } catch (Exception e) {
                logger.warn("Timeout waiting for page load: {}", e.getMessage());
            }
        } catch (Exception e) {
            logger.error("Failed to navigate to URL: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Method to close Playwright resources.
     */
    public synchronized void quitPlaywright() {
        logger.info("Quitting Playwright resources");
        closeAllResources();
    }
    
    /**
     * Close all Playwright resources in the correct order.
     */
    private static synchronized void closeAllResources() {
        try {
            if (page != null) {
                try {
                    page.close();
                    logger.info("Page closed");
                } catch (Exception e) {
                    logger.warn("Error closing page: {}", e.getMessage());
                }
                page = null;
            }
            
            if (browserContext != null) {
                try {
                    browserContext.close();
                    logger.info("Browser context closed");
                } catch (Exception e) {
                    logger.warn("Error closing browser context: {}", e.getMessage());
                }
                browserContext = null;
            }
            
            if (browser != null) {
                try {
                    browser.close();
                    logger.info("Browser closed");
                } catch (Exception e) {
                    logger.warn("Error closing browser: {}", e.getMessage());
                }
                browser = null;
            }
            
            if (playwright != null) {
                try {
                    playwright.close();
                    logger.info("Playwright closed");
                } catch (Exception e) {
                    logger.warn("Error closing playwright: {}", e.getMessage());
                }
                playwright = null;
            }
            
            isInitialized = false;
            logger.info("All Playwright resources closed successfully");
        } catch (Exception e) {
            logger.error("Error during resource cleanup: {}", e.getMessage(), e);
        }
    }
   
//    /**
//     * Clears browser state (cookies, localStorage, etc.) but keeps the browser context open.
//     * This helps ensure a clean state between scenarios.
//     */
//    public synchronized void clearBrowserState() {
//        try {
//            if (browserContext != null) {
//                // Clear cookies
//                browserContext.clearCookies();
//                logger.info("Browser cookies cleared");
//                
//                // Clear localStorage and sessionStorage for the current page if it exists
//                if (page != null && !page.isClosed()) {
//                    try {
//                        page.evaluate("() => { localStorage.clear(); sessionStorage.clear(); }");
//                        logger.info("Browser storage cleared");
//                    } catch (Exception e) {
//                        logger.warn("Could not clear browser storage: {}", e.getMessage());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Error clearing browser state: {}", e.getMessage(), e);
//        }
//    }
   
    /**
     * Recreates the browser context but keeps the browser open.
     * Use this for a completely fresh start between scenarios.
     */
    public synchronized void recreateBrowserContext() {
        try {
            // Close existing context if any
            if (browserContext != null) {
                try {
                    // Make sure page is closed first
                    if (page != null) {
                        page.close();
                        page = null;
                    }
                    
                    browserContext.close();
                    logger.info("Browser context closed");
                } catch (Exception e) {
                    logger.warn("Error closing browser context: {}", e.getMessage());
                }
                browserContext = null;
            }
            
            // Create new context
            if (browser != null) {
                browserContext = createBrowserContext();
                logger.info("New browser context created");
            } else {
                logger.error("Cannot create browser context, browser is null");
                throw new IllegalStateException("Browser is null, cannot create context");
            }
        } catch (Exception e) {
            logger.error("Error recreating browser context: {}", e.getMessage(), e);
            throw e;
        }
    }
}
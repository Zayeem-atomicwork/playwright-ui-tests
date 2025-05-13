package pageimplementations;

import org.apache.logging.log4j.Logger;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import utilities.Log;
import pageinterfaces.LoginPageInterface;
import pageselectors.LoginPageSelectors;

/**
 * The class {@code LoginPage} includes methods to perform actions on Atomicwork
 * dashboard login page.
 */
public class LoginPage implements LoginPageInterface {
    private final Page page;
    private final LoginPageSelectors loginPageSelectors;
    private final Logger logger;

    public LoginPage(final Page page) {
        this.page = page;
        loginPageSelectors = new LoginPageSelectors(page);
        logger = Log.getLogger(LoginPage.class);
    }

    /**
     * Method to enter email on Atomicwork dashboard login page.
     * 
     * @param email: user email
     * @return Object of this class
     */
    @Override
    public LoginPage enterEmail(final String email) {
        logger.info("Entering user email as {}", email);
        page.locator(loginPageSelectors.txtbxEmail).fill(email);
        return this;
    }

    /**
     * Method to enter password on Atomicwork dashboard login page.
     * 
     * @param password: user password
     * @return Object of this class
     */
    @Override
    public LoginPage enterPassword(final String password) {
        logger.info("Entering password as *******");
        page.locator(loginPageSelectors.txtbxPassword).fill(password);
        return this;
    }

    /**
     * Method to click on sign-in button on Atomicwork dashboard login page.
     * 
     * @return Object of this class
     */
    @Override
    public LoginPage clickSignIn() {
        logger.info("Clicking sign in button.");
        Locator signInButton = page.locator(loginPageSelectors.btnSignIn);
        signInButton.click();
        
        // Wait for navigation to complete
        page.waitForLoadState();
        
        return this;
    }

    /**
     * Binding method to enter user credentials on Atomicwork dashboard login page.
     * 
     * @param email:    user email
     * @param password: user password
     * @return Object of this class
     */
    @Override
    public LoginPage enterCredentials(final String email, final String password) {
        return enterEmail(email).enterPassword(password);
    }

    /**
     * Method which returns true if the user is on the login page.
     *
     * @return true if the user is on the login page else false.
     */
    public boolean isLoginPage() {
        logger.info("Checking if the user is on the login page.");
        
        try {
            // First create the locator
            var loginElement = page.locator(loginPageSelectors.login);
            
            // Wait for the element (separately) with timeout
            loginElement.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            
            // Then check visibility
            return loginElement.isVisible();
        } catch (Exception e) {
            logger.warn("Login element not found with primary selector: " + e.getMessage());
            
            // Fallback check for common login elements as a backup
            try {
                // Use the selectors from LoginPageSelectors class
                boolean hasEmailField = page.locator(loginPageSelectors.txtbxEmail).isVisible();
                boolean hasPasswordField = page.locator(loginPageSelectors.txtbxPassword).isVisible();
                
                logger.info("Fallback login detection - Email field: {}, Password field: {}", 
                    hasEmailField, hasPasswordField);
                    
                return hasEmailField && hasPasswordField;
            } catch (Exception ex) {
                logger.error("Fallback login detection also failed: " + ex.getMessage());
                return false;
            }
        }
    }
  
}
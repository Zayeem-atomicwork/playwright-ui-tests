package pageinterfaces;

import pageimplementations.LoginPage;

/**
 * The interface {@code LoginPageInterface} includes methods to perform
 * actions on the login page.
 */
public interface LoginPageInterface {
    
    /**
     * Method to enter email on Atomicwork dashboard login page.
     * 
     * @param email user email
     * @return Object of LoginPage
     */
    LoginPage enterEmail(String email);
    
    /**
     * Method to enter password on Atomicwork dashboard login page.
     * 
     * @param password user password
     * @return Object of LoginPage
     */
    LoginPage enterPassword(String password);
    
    /**
     * Method to click on sign-in button on Atomicwork dashboard login page.
     * 
     * @return Object of LoginPage
     */
    LoginPage clickSignIn();
    
    /**
     * Method to enter user credentials on Atomicwork dashboard login page.
     * 
     * @param email user email
     * @param password user password
     * @return Object of LoginPage
     */
    LoginPage enterCredentials(String email, String password);
}
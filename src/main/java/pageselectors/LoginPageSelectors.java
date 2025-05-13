package pageselectors;

import com.microsoft.playwright.Page;

/**
 * The class {@code LoginPageSelectors} holds the selectors for elements on the
 * login page and is responsible for initializing them.
 */
public class LoginPageSelectors {
    // Keeping the exact same variable names as the original Selenium version
    public final String txtbxEmail = "//input[@id='username']";
    public final String txtbxPassword = "//input[@id='password']";
    public final String btnSignIn = "#kc-login";
    public final String login = "//h1[@id='kc-page-title']";

    private final Page page;

    public LoginPageSelectors(final Page page) {
        this.page = page;
    }
}
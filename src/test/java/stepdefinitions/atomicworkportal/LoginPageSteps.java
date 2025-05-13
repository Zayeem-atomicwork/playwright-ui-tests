package stepdefinitions.atomicworkportal;

import org.testng.Assert;
import cucumber.TestContext;
import io.cucumber.java.en.Given;
import managers.FileReaderManager;
import messages.AssertFailureMsgs;
import pageimplementations.LoginPage;

public class LoginPageSteps {
    private final LoginPage loginPage;
    
    public LoginPageSteps(final TestContext testContext) {
        loginPage = testContext.getPageObjectManager().getLoginPage();
    }
    
    @Given("^the user (?:is logged in|logs in)$")
    public void logIn() {
        Assert.assertTrue(loginPage.isLoginPage(), AssertFailureMsgs.NOT_ON_LOGIN_PAGE);
        final String userEmail = FileReaderManager.getInstance().getConfigReader().getUserEmail(),
                password = FileReaderManager.getInstance().getConfigReader().getUserPassword();
        loginPage.enterCredentials(userEmail, password).clickSignIn();
    }
}
package stepdefinitions.atomicworkportal;

import org.testng.Assert;

import cucumber.TestContext;
import enums.ContextData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import messages.AssertFailureMsgs;
import pageimplementations.DashboardPage;
import pageimplementations.RequestDrawer;
import pagemessages.RequestDrawerPageMsgs;

/**
 * The class {@code DashboardPageSteps} containing the implementation for steps
 * in the feature files interacting with the top banner and left drawer.
 */
public class DashboardPageSteps {
	
	private final RequestDrawer requestDrawer;
    private final DashboardPage dashboardPage;
    private final TestContext testContext;
    
    public DashboardPageSteps(final TestContext testContext) {
    	this.testContext = testContext;
        dashboardPage = testContext.getPageObjectManager().getDashboardPage();
        requestDrawer = testContext.getPageObjectManager().getRequestDrawer();
    }
      
    @When("the user creates a request with subject {string}")
    public void userCreatesRequest(String subject) {
        requestDrawer.openCreateRequestDrawer();
        requestDrawer.createNewRequest(subject);
        testContext.getScenarioContext().setContext(ContextData.REQUEST_SUBJECT, subject);
    }

    @When("the user reports a problem with subject {string}")
    public void userReportsProblem(String subject) {
        requestDrawer.openReportProblemDrawer();        
        requestDrawer.createNewRequest(subject);
        testContext.getScenarioContext().setContext(ContextData.REQUEST_SUBJECT, subject);
    }

    @Then("^verify the request is created successfully$")
    public void verifyRequestCreated() {
        // Get toast message directly
        String toastMessage = requestDrawer.getToastMessage();
        
        // Extract request ID and store in context (this part is necessary)
        String requestId = toastMessage.replaceAll(RequestDrawerPageMsgs.REQUEST_ID_REGEX, "$1");
        testContext.getScenarioContext().setContext(ContextData.REQUEST_ID, requestId);
        
        // Direct assertion without creating variable
        Assert.assertEquals(
            requestDrawer.getToastMessage(),
            String.format(RequestDrawerPageMsgs.REQUEST_CREATED_NOTIFICATION, requestId),
            AssertFailureMsgs.REQUEST_CREATION_FAILED
        );

    }

    @Then("^verify the problem is created successfully$")
    public void verifyProblemCreated() {
        // Get toast message directly
        String toastMessage = requestDrawer.getToastMessage();
        
        // Extract request ID and store in context (this part is necessary)
        String problemId = toastMessage.replaceAll(RequestDrawerPageMsgs.REQUEST_ID_REGEX, "$1");
        testContext.getScenarioContext().setContext(ContextData.REQUEST_ID, problemId);
        
        // Direct assertion without creating variable
        Assert.assertEquals(
            requestDrawer.getToastMessage(),
            String.format(RequestDrawerPageMsgs.PROBLEM_CREATED_NOTIFICATION, problemId),
            AssertFailureMsgs.REQUEST_CREATION_FAILED
        );

    }

    @When("^the user views the (?:request|problem) details$")
    public void userViewsRequestDetails() {
        // Only perform the view action
        requestDrawer.viewCreatedRequest();
        // Close the toast message
        requestDrawer.closeToastMessage();
    }
    
    @Then("^verify the (?:request|problem) details ID, subject, and status are correct$")
    public void verifyBreadcrumbRequestId() {
        Assert.assertEquals(
            requestDrawer.getBreadcrumbRequestId(),
            testContext.getScenarioContext().getContext(ContextData.REQUEST_ID),
            AssertFailureMsgs.REQUEST_ID_MISMATCH);
        
        Assert.assertEquals(
            requestDrawer.getRequestSubject(),
            testContext.getScenarioContext().getContext(ContextData.REQUEST_SUBJECT),
            AssertFailureMsgs.REQUEST_SUBJECT_MISMATCH);
        
        Assert.assertEquals(
            requestDrawer.getRequestStatus(),
            RequestDrawerPageMsgs.REQUEST_STATUS_OPEN,
            AssertFailureMsgs.REQUEST_STATUS_MISMATCH);
    }

    @When("^the user deletes the (?:request|problem)$")
    public void userDeletesRequest() {
        requestDrawer.deleteRequest();
    }

    @Then("^verify the (?:request|problem) is moved to trash successfully$")
    public void verifyRequestMovedToTrash() {
        Assert.assertEquals(
            requestDrawer.getToastMessage(),
            String.format(
                RequestDrawerPageMsgs.REQUEST_MOVED_TO_TRASH_NOTIFICATION, 
                testContext.getScenarioContext().getContext(ContextData.REQUEST_ID)),
            AssertFailureMsgs.REQUEST_MOVE_TO_TRASH_FAILED);
        
        requestDrawer.closeToastMessage();
    }

    @When("the user permanently deletes the request from trash")
    public void userPermanentlyDeletesRequestFromTrash() {
        requestDrawer.deleteRequestPermanently();
    }

    @Then("verify the request is permanently deleted successfully")
    public void verifyRequestPermanentlyDeleted() {
        Assert.assertEquals(
            requestDrawer.getToastMessage(),
            RequestDrawerPageMsgs.REQUEST_DELETED_FOREVER_NOTIFICATION,
            	AssertFailureMsgs.REQUEST_PERMANENT_DELETION_FAILED);
        
        requestDrawer.closeToastMessage();
    }

}
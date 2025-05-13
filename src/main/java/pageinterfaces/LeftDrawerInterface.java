package pageinterfaces;

import pageimplementations.DashboardPage;

/**
 * The interface {@code LeftDrawerInterface} includes methods used to perform
 * some action on left drawer navigation panel sub page.
 */
public interface LeftDrawerInterface {
	
    DashboardPage goToApprovalsAndTasksPage();
    
    DashboardPage goToActivityPage();
    
    DashboardPage gotoRequestedByMePage();
    
    DashboardPage gotoJourneysPage();
    
    DashboardPage gotoAssignedToMePage();
    
    DashboardPage gotoSettingsPage();
    
    DashboardPage goToDefaultWorkspacePage();
    
    DashboardPage goToDefaultProblemsPage();
    
    DashboardPage goToDefaultChangesPage();
    
    DashboardPage goToDefaultRequestPage();
    
    //DashboardPage goToManageUserSettings();
}
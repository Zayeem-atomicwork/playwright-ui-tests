Feature: Workspace Tests
	Description: The purpose of this feature is to test Workspace Creation, Deletion in the atomicwork dashboard.

Background:
	Given the user is logged in

@workspace
Scenario Outline: Verify that Workspace Add, Delete is working as expected
	When the user navigates to the settings page
#	Then verify the user is on the general settings page
	When the user navigates to the workspace settings page
#	Then verify the user is on the workspace settings page
	When the user enters the name and add the workspace
	Then verify the workspace created toast message is displayed
	And verify the workspace is added to the list
	When the user taps on ellipsis and delete
	Then verify the workspace name in delete modal is displayed
	When the user enter the workspace name and confirm
	Then verify the workspace deleted toast message is displayed

@workspace
Scenario Outline: Verify that Workspace Add, Delete is working as expected
	When the user navigates to the settings page
#	Then verify the user is on the general settings page
	When the user navigates to the workspace settings page
#	Then verify the user is on the workspace settings page
	When the user enters the name that already exists
	Then verify the workspace already exists toast message is displayed

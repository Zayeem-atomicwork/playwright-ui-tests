Feature: Request Management
  Description: The purpose of this feature is to test Request Creation and Deletion in the Atomicwork portal.

  Background:
    Given the user is logged in

  @request
  Scenario: Verify that Request Creation and Deletion works as expected
    When the user creates a request with subject "<subject>"
    Then verify the request is created successfully
    When the user views the request details
    Then verify the request details ID, subject, and status are correct
    When the user deletes the request
    Then verify the request is moved to trash successfully
    When the user permanently deletes the request from trash
    Then verify the request is permanently deleted successfully

    Examples:
    |subject|
    |Simple request|
    |Urgent request|
    |Long request title with special chars !@#$|
    |Request with numbers 123456|
    
  @request
  Scenario: Verify that Report problem and Deletion works as expected
    When the user reports a problem with subject "<subject>"
    Then verify the problem is created successfully
    When the user views the problem details
    Then verify the problem details ID, subject, and status are correct
    When the user deletes the problem
    Then verify the problem is moved to trash successfully
    When the user permanently deletes the request from trash
    Then verify the request is permanently deleted successfully

    Examples:
    |subject|
    |Simple problem|
    |Urgent problem|
    |Long request title with special chars !@#$|
    |Request with numbers 123456|
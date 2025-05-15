Feature: User Login and Authentication
  As a user (customer, chef, or admin),
  I want to log in to the system with my credentials
  So that I can access my respective functionalities securely.

  Scenario: Successful login with valid credentials
    Given a user with username "john_doe" and password "Pass1234" exists with role "Customer"
    When the user attempts to log in with username "john_doe" and password "Pass1234"
    Then the system should authenticate the user successfully
    And the user should have access to "Customer" functionalities

  Scenario: Failed login with incorrect password
    Given a user with username "john_doe" and password "Pass1234" exists with role "Customer"
    When the user attempts to log in with username "john_doe" and password "WrongPass"
    Then the system should deny access
    And the user should receive an error message "Invalid username or password"

  Scenario: Failed login with non-existent username
    Given no user exists with username "unknown_user"
    When the user attempts to log in with username "unknown_user" and password "AnyPass"
    Then the system should deny access
    And the user should receive an error message "Invalid username or password"

  Scenario: Role-based access after login
    Given a user with username "chef_alice" and password "Chef1234" exists with role "Chef"
    When the user attempts to log in with username "chef_alice" and password "Chef1234"
    Then the system should authenticate the user successfully
    And the user should have access to "Chef" functionalities
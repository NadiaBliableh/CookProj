Feature: User Login

  Scenario: Successful login with correct credentials
    Given a registered user "john_doe" with password "SecurePass123"
    When the user enters username "john_doe" and password "SecurePass123"
    Then the system should authenticate the user and grant access

  Scenario: Failed login with incorrect password
    Given a registered user "jane_doe" with password "MyPassword456"
    When the user enters username "jane_doe" and password "WrongPassword"
    Then the system should deny access with an "Invalid credentials" message

  Scenario: Failed login for unregistered user
    Given no user exists with username "ghost_user"
    When the user enters username "ghost_user" and password "SomePassword"
    Then the system should deny access with a "User not found" message

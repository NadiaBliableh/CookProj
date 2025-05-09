Feature: User Login
  The system should allow different types of users to log in:
  Admins, Chefs, Customers, and Kitchen Managers.

  Background:
    Given the system is running

  Scenario Outline: Successful login with valid credentials
    When the user enters a valid <accountType> username and password
    Then the user should be redirected to the <accountType> dashboard
    
    Examples:
      | accountType       |
      | Admin             |
      | Chef              |
      | Customer          |
      | Kitchen Manager   |

  Scenario Outline: Failed login with invalid credentials
    When the user enters an invalid <accountType> username or password
    Then an error message should be displayed saying "Invalid credentials"

    Examples:
      | accountType       |
      | Admin             |
      | Chef              |
      | Customer          |
      | Kitchen Manager   |

  Scenario: Attempt to login with empty fields
    When the user leaves the username and password fields empty
    Then a validation message should be displayed saying "Please enter username and password"



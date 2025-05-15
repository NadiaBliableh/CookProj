Feature: Track past orders and personalized meal plans

  Scenario: Customer views past meal orders
    Given I am a registered customer
    And I have previously ordered meals
    When I view my past orders
    Then I should see a list of meals I previously ordered
    And I should be able to reorder any of them

  Scenario: Chef suggests personalized meals based on order history
    Given I am a chef and I have access to customer order history
    And the customer has previously ordered Vegan meals
    When I analyze their past orders
    Then I should be able to suggest personalized Vegan meals for the customer

  Scenario: System administrator stores and retrieves customer order history
    Given I am a system administrator
    And customer order history exists in the database
    When I retrieve the order history for a customer
    Then I should see a complete and accurate list of their past meal orders
    And I should be able to generate reports on order trends

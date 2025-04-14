Feature: Customer Profile Management

  Scenario: Customer inputs dietary preferences and allergies
    Given a customer with an empty profile
    When the customer inputs dietary preferences "Vegetarian" and allergies "Peanuts"
    Then the system should store the preferences and allergies

  Scenario: Chef views customer dietary preferences
    Given a customer with dietary preferences "Vegetarian" and allergies "Peanuts"
    When a chef views the customer profile
    Then the chef should see "Vegetarian" as preferences and "Peanuts" as allergies
Feature: Track Orders

  Scenario: Customer views their order history
    Given a customer is logged into their account
    When they navigate to the order history section
    Then they should see a list of their past meal orders
    Then they should have the option to reorder a meal they liked

  Scenario: Chef views a customer's order history
    Given a chef is logged into the system
    When they access a specific customer’s order history
    Then they should see a list of past meals ordered by the customer
    Then they should be able to suggest personalized meal plans based on the order history

  Scenario: Admin retrieves and analyzes the order history
    Given the system administrator has access to the order database
    When they query for customer order history
    Then they should be able to retrieve past orders
    Then they should be able to analyze trends to improve service offerings
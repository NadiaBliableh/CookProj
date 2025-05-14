Feature: Send reminders for upcoming orders and deliveries

  Scenario: Send delivery reminder to customer
    Given a customer has an upcoming meal delivery
    When the delivery time approaches
    Then the customer receives a reminder notification

  Scenario: Notify chef about scheduled cooking tasks
    Given a chef has a scheduled cooking task
    When the cooking time approaches
    Then the chef receives a notification to prepare the meal

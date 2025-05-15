Feature: Notifications and Alerts As a customer,
  I want to receive reminders for my upcoming meal deliveries so that I can be prepared to receive them.
  As a chef, I want to get notified of scheduled cooking tasks so that I can prepare meals on time.

  Scenario: Customer receives a delivery reminder
    Given a customer has an upcoming meal delivery
    When the system sends a delivery reminder
    Then the customer receives a reminder with delivery details

  Scenario: Customer receives a message when delivery time is not set
    Given a customer has an order without a delivery time
    When the system sends a delivery reminder
    Then the customer receives a message indicating no delivery time is scheduled

  Scenario: Chef receives a task notification
    Given a chef is assigned a cooking task
    When the system sends a task notification
    Then the chef receives a notification with task details
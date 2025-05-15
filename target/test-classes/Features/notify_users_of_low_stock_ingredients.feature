Feature: Low Stock Notifications
  As a kitchen manager
  I want to be notified when ingredients are low in stock
  So that I can restock them before they run out

  Scenario: Low stock ingredients alert
    Given the inventory has the following ingredients:
      | ingredient | quantity |
      | Onions     | 15       |
      | Garlic     | 2        |
      | Tomatoes   | 100      |
    And the critical stock level is 15
    When the system checks for low stock ingredients
    Then the kitchen manager should receive an alert for the following low stock ingredients:
      | Onions |
      | Garlic |

  Scenario: No notification when all ingredients are above critical stock level
    Given the inventory has the following ingredients:
      | ingredient | quantity |
      | Tomato     | 10       |
      | Lettuce    | 15       |
      | Onion      | 8        |
    And the critical stock level is 5
    When the system checks for low stock ingredients
    Then the kitchen manager should receive no low stock alerts
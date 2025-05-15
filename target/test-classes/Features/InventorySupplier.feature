Feature: Inventory and Supplier Management

  # Scenario 1: Track available ingredients and suggest restocking
  Scenario: Track available ingredients and suggest restocking
    Given the kitchen manager has access to the inventory system
    And the following ingredients are available in the system:
      | Ingredient   | Quantity |
      | Tomatoes     | 50      |
      | Onions       | 10      |
      | Garlic       | 5       |
    When the kitchen manager checks the inventory levels
    Then the system should display the current stock levels of all ingredients
    And the system should suggest restocking for ingredients with low stock
    And the system should suggest restocking for "Onions" and "Garlic"

  # Scenario 2: Track available ingredients and prevent shortages
  Scenario: Prevent ingredient shortages by tracking stock levels
    Given the kitchen manager has access to the inventory system
    And the following ingredients are available in the system:
      | Ingredient   | Quantity |
      | Tomatoes     | 100     |
      | Onions       | 15      |
      | Garlic       | 2       |
    When the kitchen manager checks the inventory levels
    Then the system should display the current stock levels of all ingredients
    And the system should warn the kitchen manager about low stock for "Garlic"
    And the system should not warn about "Tomatoes" or "Onions"

  # Scenario 3: Suggest restocking automatically when ingredient stock is low
  Scenario: Automatic restocking suggestion when stock is low
    Given the kitchen manager has access to the inventory system
    And the following ingredients are available in the system:
      | Ingredient   | Quantity |
      | Tomatoes     | 25      |
      | Onions       | 5       |
      | Garlic       | 1       |
    When the kitchen manager checks the inventory levels
    Then the system should automatically suggest restocking for "Onions" and "Garlic"
    And the system should suggest a reorder for "Tomatoes"

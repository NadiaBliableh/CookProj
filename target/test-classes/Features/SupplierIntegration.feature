Feature: Supplier Integration for Real-Time Pricing and Ordering

  As a kitchen manager,
  I want the system to fetch real-time ingredient prices from suppliers
  So that I can make cost-effective purchasing decisions.

  As a system,
  I want to automatically generate purchase orders when stock levels are critically low
  So that supplies are replenished without manual intervention.

  Background:
    Given the following inventory levels:
      | Ingredient | Quantity |
      | Onions     | 10       |
      | Garlic     | 5        |
      | Tomatoes   | 50       |
    And the following suppliers are available:
      | Supplier       | Ingredient | PricePerUnit |
      | FreshFarms     | Onions     | 0.50         |
      | FreshFarms     | Garlic     | 0.30         |
      | FreshFarms     | Tomatoes   | 0.80         |
      | GreenGrocers   | Onions     | 0.45         |
      | GreenGrocers   | Garlic     | 0.35         |
      | GreenGrocers   | Tomatoes   | 0.75         |

  Scenario: Fetch real-time prices for an ingredient
    When I request prices for "Onions" from all suppliers
    Then the system should return the following prices:
      | Supplier       | Ingredient | PricePerUnit |
      | FreshFarms     | Onions     | 0.50         |
      | GreenGrocers   | Onions     | 0.45         |

  Scenario: Select the cheapest supplier for an ingredient
    When I request the cheapest supplier for "Garlic"
    Then the system should select "FreshFarms" with price 0.30 for "Garlic"

  Scenario: Automatically generate purchase order and notify for critically low stock
    Given the critical stock level is 5 units
    When the system checks inventory levels
    Then a purchase order should be generated for "Garlic" with:
      | Supplier    | Ingredient | Quantity | PricePerUnit |
      | FreshFarms  | Garlic     | 50       | 0.30         |
    And the kitchen manager should receive a notification: "Purchase order created for Garlic: 50 units from FreshFarms at $0.30 per unit"
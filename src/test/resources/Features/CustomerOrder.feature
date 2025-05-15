Feature: Order and Menu Customization

  Scenario: Customer creates a custom meal request with available ingredients
    Given a customer with dietary preference "Vegan" and allergy "Peanut"
    And the following ingredients are available:
      | name         | available |
      | Lettuce      | true      |
      | Tomato       | true      |
      | Tofu         | true      |
      | Olive Oil    | true      |
    When the customer selects the following ingredients for their custom meal:
      | ingredient   |
      | Lettuce      |
      | Tomato       |
      | Tofu         |
    Then the system should confirm the custom meal is created successfully
    And the customer should be able to add the meal to their order

  Scenario: Customer tries to create a custom meal with incompatible ingredients
    Given a customer with dietary preference "Vegan" and allergy "Peanut"
    And the following ingredients are available:
      | name         | available |
      | Lettuce      | true      |
      | Tomato       | true      |
      | Tofu         | true      |
      | Peanut Butter| true      |
    When the customer selects the following ingredients for their custom meal:
      | ingredient   |
      | Lettuce      |
      | Tomato       |
      | Peanut Butter|
    Then the system should alert the customer that "Peanut Butter" is not compatible with their dietary preference
    And the customer should be prompted to select a different ingredient

  Scenario: Customer tries to create a custom meal with unavailable ingredients
    Given a customer with dietary preference "Vegan" and allergy "Peanut"
    And the following ingredients are available:
      | name         | available |
      | Lettuce      | true      |
      | Tomato       | true      |
      | Tofu         | true      |
    When the customer selects the following ingredients for their custom meal:
      | ingredient   |
      | Lettuce      |
      | Tomato       |
      | Chicken      |
    Then the system should notify the customer that "Chicken" is currently unavailable
    And the customer should be asked to choose a different ingredient
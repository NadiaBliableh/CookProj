Feature: Substitution

  Scenario: Substitute unavailable ingredient with available alternative
    Given a customer with dietary preference "Vegan" and allergy "Peanut"
    And the following ingredients are available:
      | name         | available |
      | Lettuce      | true      |
      | Tomato       | true      |
      | Chicken      | false     |
      | Tofu         | true      |
    When the customer selects the following ingredients for their custom meal:
      | ingredient   |
      | Lettuce      |
      | Tomato       |
      | Chicken      |
    Then the system should notify the customer that "Chicken" is currently unavailable
    And the customer should be asked to choose a different ingredient

  Scenario: No substitution available for unavailable ingredient
    Given a customer with dietary preference "Vegan" and allergy "Peanut"
    And the following ingredients are available:
      | name         | available |
      | Lettuce      | true      |
      | Tomato       | true      |
      | Chicken      | false     |
    When the customer selects the following ingredients for their custom meal:
      | ingredient   |
      | Lettuce      |
      | Tomato       |
      | Chicken      |
    Then the system should notify the customer that "Chicken" is currently unavailable
    And the customer should be asked to choose a different ingredient

  Scenario: Substitute incompatible ingredient with compatible alternative
    Given a customer with dietary preference "Vegan" and allergy "Peanut"
    And the following ingredients are available:
      | name         | available |
      | Lettuce      | true      |
      | Tomato       | true      |
      | Cheese       | true      |
      | Tofu         | true      |
    When the customer selects the following ingredients for their custom meal:
      | ingredient   |
      | Lettuce      |
      | Tomato       |
      | Cheese       |
    Then the system should alert the customer that "Cheese" is not compatible with their dietary preference
    And the customer should be prompted to select a different ingredient
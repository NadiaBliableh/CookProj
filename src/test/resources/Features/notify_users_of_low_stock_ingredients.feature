Feature: Notify users of low-stock ingredients

  Scenario: Alert kitchen manager of low-stock ingredients
    Given the system monitors ingredient stock levels
    When an ingredient's stock becomes low
    Then the kitchen manager receives an alert
    And the manager can reorder the ingredient

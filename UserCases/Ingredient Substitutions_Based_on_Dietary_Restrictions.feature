Feature: Ingredient Substitution for Dietary Restrictions

  As a customer, I want the system to suggest alternative ingredients
  if an ingredient is unavailable or does not fit my dietary restrictions,
  so that I can enjoy my meal without compromising my health.

  As a chef, I want to receive an alert when an ingredient substitution is applied,
  so that I can approve or adjust the final recipe.

  Scenario: Suggesting an alternative ingredient for a dietary restriction
    Given a customer has a dietary restriction for "dairy"
    And a recipe contains "milk"
    When the system detects the restriction
    Then it suggests an alternative ingredient such as "almond milk" or "oat milk"

  Scenario: Suggesting an alternative ingredient due to unavailability
    Given an ingredient "eggs" is unavailable
    And a recipe requires "eggs"
    When the system detects the unavailability
    Then it suggests an alternative ingredient such as "flaxseed meal" or "applesauce"

  Scenario Outline: Chef receives an alert for ingredient substitution
    Given the system has suggested a substitute for an ingredient
    When the substitution is applied to a recipe
    Then the chef receives an alert with the original and substitute ingredient details
    And the chef can approve or modify the substitution before finalizing the recipe
    Examples:

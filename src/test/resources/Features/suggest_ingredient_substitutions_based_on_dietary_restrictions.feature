Feature: Ingredient substitution based on dietary restrictions

  As a customer,
  I want the system to suggest alternative ingredients,
  So that I can enjoy my meal without health risks.

  As a chef,
  I want to be alerted about ingredient substitutions,
  So that I can approve or adjust the final recipe.

  Scenario: Suggest substitution for unavailable ingredient
    Given the customer has a dietary restriction for "gluten"
    And the meal contains the ingredient "Nutritional Yeast"
    When the system detects a conflict
    Then the system should suggest "Vegan Cheese" as an alternative
    And notify the chef about the suggested substitution

  Scenario: Suggest substitution for allergic ingredient
    Given the customer has an allergy to "Vegan"
    And the meal contains the ingredient "Vegan Butter"
    When the system detects a conflict
    Then the system should suggest "Olive Oil" as an alternative
    And notify the chef about the suggested substitution
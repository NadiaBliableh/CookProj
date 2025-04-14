Feature: Meal Modification and Customization

  Scenario: Customer modifies an existing meal order
    Given a customer has an existing meal order with ingredients "Chicken", "Lettuce", and "Tomato"
    When the customer modifies the meal by adding "Cucumber" and removing "Lettuce"
    Then the system should update the meal with the new ingredients: "Chicken", "Tomato", and "Cucumber"

  Scenario: Customer attempts to modify an order after it has been confirmed for preparation
    Given a customer has confirmed their meal order with ingredients "Chicken", "Lettuce", and "Tomato"
    When the customer tries to modify the meal after confirmation
    Then the system should reject the modification and inform the customer that the meal cannot be changed

  Scenario: Customer tries to customize a meal with an unavailable ingredient
    Given the ingredient "Peanuts" is out of stock
    When the customer attempts to add "Peanuts" to their custom meal
    Then the system should inform the customer that "Peanuts" is unavailable and cannot be added
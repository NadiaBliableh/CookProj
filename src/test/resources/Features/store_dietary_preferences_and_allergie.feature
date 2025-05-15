Feature: Store dietary preferences and allergies
    As a customer, I want to input my dietary preferences and allergies
    so that the system can recommend appropriate meals and prevent unwanted ingredients.
    As a chef, I want to view customer dietary preferences so that I can customize meals accordingly.

    Scenario: Customer enters dietary preferences and allergies
        Given a customer named "Aria"
        When they input dietary preference as "Vegan"
        And they input allergy to "Gluten"
        Then the system should store their preferences and allergies

    Scenario: System recommends meals based on stored dietary data
        Given a customer named "Aria" with a dietary preference "Vegan" and allergy to "Gluten"
        When the system suggests meals
        Then all suggested meals should match the dietary preference "Vegan"
        And should not contain "Gluten"


    Scenario: Chef views customer dietary preferences
        Given a customer named "Aria" with a dietary preference "Vegan" and allergy to "Peanut"
        When the chef views Aria's dietary profile
        Then they should see "Vegan" as the dietary preference
        And "Peanut" listed under allergies


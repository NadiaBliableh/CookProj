Feature: Store dietary preferences and allergies

    Customers can save their dietary preferences and allergies so the system can suggest meals accordingly.
    Chefs can view these preferences to prepare customized meals.

    Scenario: Customer adds dietary preferences and allergies
        Given the customer is logged in
        When the customer enters their dietary preferences as "vegetarian" and allergies as "peanuts"
        And saves the information
        Then the system should store the dietary preferences and allergies
        And confirm that the information was saved successfully

    Scenario: Chef views customer dietary preferences
        Given the chef is logged in
        When the chef selects a customer profile
        Then the system should display the customer's dietary preferences and allergies

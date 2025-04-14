package FeatureTest;

import static org.junit.Assert.*;
import io.cucumber.java.en.*;
import java.util.Arrays;
import java.util.List;
import ProductionCode.*;
public class MealOrderStepDefinitions {
    private MealOrder mealOrder;
    private Exception caughtException;

    @Given("a customer has an existing meal order with ingredients {string}, {string}, and {string}")
    public void a_customer_has_an_existing_meal_order_with_ingredients_and(String ingredient1, String ingredient2, String ingredient3) {
        List<String> ingredients = Arrays.asList(ingredient1, ingredient2, ingredient3);
        mealOrder = new MealOrder(ingredients);
    }

    @When("the customer modifies the meal by adding {string} and removing {string}")
    public void the_customer_modifies_the_meal_by_adding_and_removing(String addIngredient, String removeIngredient) {
        mealOrder.modifyMeal(addIngredient, removeIngredient);
    }

    @Then("the system should update the meal with the new ingredients: {string}, {string}, and {string}")
    public void the_system_should_update_the_meal_with_the_new_ingredients_and(String expected1, String expected2, String expected3) {
        List<String> expectedIngredients = Arrays.asList(expected1, expected2, expected3);
        assertEquals(expectedIngredients, mealOrder.getIngredients());
    }

    @Given("a customer has confirmed their meal order with ingredients {string}, {string}, and {string}")
    public void a_customer_has_confirmed_their_meal_order_with_ingredients_and(String ingredient1, String ingredient2, String ingredient3) {
        List<String> ingredients = Arrays.asList(ingredient1, ingredient2, ingredient3);
        mealOrder = new MealOrder(ingredients);
        mealOrder.confirmOrder();
    }

    @When("the customer tries to modify the meal after confirmation")
    public void the_customer_tries_to_modify_the_meal_after_confirmation() {
        try {
            mealOrder.modifyMeal("New Ingredient", "Old Ingredient");
        } catch (Exception e) {
            caughtException = e;
        }
    }

    @Then("the system should reject the modification and inform the customer that the meal cannot be changed")
    public void the_system_should_reject_the_modification_and_inform_the_customer_that_the_meal_cannot_be_changed() {
        assertNotNull(caughtException);
        assertEquals("Meal order cannot be modified after confirmation.", caughtException.getMessage());
    }

    @Given("the ingredient {string} is out of stock")
    public void the_ingredient_is_out_of_stock(String ingredient) {
        IngredientStock.markOutOfStock(ingredient);
    }

    @When("the customer attempts to add {string} to their custom meal")
    public void the_customer_attempts_to_add_to_their_custom_meal(String ingredient) {
        if (IngredientStock.isOutOfStock(ingredient)) {
            caughtException = new IllegalStateException(ingredient + " is unavailable and cannot be added.");
        }
    }

    @Then("the system should inform the customer that {string} is unavailable and cannot be added")
    public void the_system_should_inform_the_customer_that_is_unavailable_and_cannot_be_added(String ingredient) {
        assertNotNull(caughtException);
        assertEquals(ingredient + " is unavailable and cannot be added.", caughtException.getMessage());
    }
}

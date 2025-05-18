package FeatureTest;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.actors.Customer;
import production_code.core.Ingredients;
import production_code.core.IngredientService;
import production_code.customer_features.CustomerOrderService;
import production_code.core.Mainn;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;

public class CustomerOrderTest {
    private final Mainn app;
    private Customer customer;
    private CustomerOrderService service;
    private IngredientService ingredientService;
    private List<Ingredients> selectedIngredients;
    private String customMealResult;

    public CustomerOrderTest(Mainn app) {
        this.app = app;
        this.selectedIngredients = new ArrayList<>();
    }

    @Given("a customer with dietary preference {string} and allergy {string}")
    public void aCustomerWithDietaryPreferenceAndAllergy(String dietaryPreference, String allergy) {
        customer = new Customer("Test Customer", dietaryPreference, List.of(allergy));
        app.addCustomer(customer);
        ingredientService = new IngredientService();
        service = new CustomerOrderService(customer, ingredientService);
        app.addCustomerOrderService(service);
    }

    @Given("the following ingredients are available:")
    public void theFollowingIngredientsAreAvailable(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<Ingredients> availableIngredients = new ArrayList<>();
        for (Map<String, String> row : rows) {
            String name = row.get("name") != null ? row.get("name") : row.get("ingredient");
            boolean available = row.get("available") != null ? Boolean.parseBoolean(row.get("available")) : true;
            Ingredients ingredient = new Ingredients(name, available);
            availableIngredients.add(ingredient);
        }
        app.setAvailableIngredients(availableIngredients);
        if (service != null && ingredientService != null) {
            app.setAvailableIngredientsForService(ingredientService, availableIngredients);
        }
    }

    @When("the customer selects the following ingredients for their custom meal:")
    public void theCustomerSelectsTheFollowingIngredientsForTheirCustomMeal(DataTable dataTable) {
        selectedIngredients.clear();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String ingredientName = row.get("ingredient");
            selectedIngredients.add(new Ingredients(ingredientName, true));
        }
        customMealResult = app.createCustomMeal(service, selectedIngredients);
    }

    @Then("the system should confirm the custom meal is created successfully")
    public void theSystemShouldConfirmTheCustomMealIsCreatedSuccessfully() {
        assertEquals("Custom meal should be created successfully", "Custom meal created successfully.", customMealResult);
    }

    @Then("the customer should be able to add the meal to their order")
    public void theCustomerShouldBeAbleToAddTheMealToTheirOrder() {
        assertEquals("Meal should be added to order successfully.", app.addMealToOrder(service, new production_code.core.Meal(selectedIngredients)));
    }

    @Then("the system should alert the customer that {string} is not compatible with their dietary preference")
    public void theSystemShouldAlertTheCustomerThatIsNotCompatibleWithTheirDietaryPreference(String ingredient) {
        String expectedMessage = ingredient + " is not compatible with your dietary preference.";
        assertTrue("Expected alert for incompatible ingredient: " + expectedMessage, customMealResult.contains(expectedMessage));
    }

    @Then("the customer should be prompted to select a different ingredient")
    public void theCustomerShouldBePromptedToSelectADifferentIngredient() {
        assertTrue("Customer should be prompted to select a different ingredient due to incompatibility",
                customMealResult.contains("Please choose a different ingredient."));
    }

    @Then("the system should notify the customer that {string} is currently unavailable")
    public void theSystemShouldNotifyTheCustomerThatIsCurrentlyUnavailable(String ingredient) {
        String expectedMessage = ingredient + " is currently unavailable.";
        assertTrue("Expected notification for unavailable ingredient: " + expectedMessage, customMealResult.contains(expectedMessage));
    }

    @Then("the customer should be asked to choose a different ingredient")
    public void theCustomerShouldBeAskedToChooseADifferentIngredient() {
        assertTrue("Customer should be asked to choose a different ingredient due to unavailability",
                customMealResult.contains("Please choose a different ingredient."));
    }
}
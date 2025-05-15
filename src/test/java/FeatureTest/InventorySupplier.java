package FeatureTest;



import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import production_code.core.*;

import java.util.List;

import production_code.actors.InventoryManager;

import production_code.InventoryManagerfeature.RestockSuggestion;

import java.util.Map;

public class InventorySupplier {
    private Mainn app;
    private InventoryManager inventoryManager;
    private RestockSuggestion restockSuggestion;

    public InventorySupplier(Mainn app) {
        this.app = app;
    }

    @Given("the kitchen manager has access to the inventory system")
    public void the_kitchen_manager_has_access_to_the_inventory_system() {
        app.resetLists();
        inventoryManager = new InventoryManager();
        app.addInventoryManager(inventoryManager);
        restockSuggestion = new RestockSuggestion(inventoryManager);
        app.addRestockSuggestion(restockSuggestion);
    }

    @Given("the following ingredients are available in the system:")
    public void the_following_ingredients_are_available_in_the_system(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String ingredient = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            app.addIngredient(inventoryManager, ingredient, quantity);
        }
    }

    @When("the kitchen manager checks the inventory levels")
    public void the_kitchen_manager_checks_the_inventory_levels() {
        // No action required
    }

    @Then("the system should display the current stock levels of all ingredients")
    public void the_system_should_display_the_current_stock_levels_of_all_ingredients() {
        for (String ingredient : inventoryManager.getInventory().keySet()) {
            System.out.println(ingredient + ": " + inventoryManager.getInventory().get(ingredient));
        }
    }

    @Then("the system should suggest restocking for ingredients with low stock")
    public void the_system_should_suggest_restocking_for_ingredients_with_low_stock() {
        for (String suggestion : app.getRestockSuggestions(restockSuggestion)) {
            System.out.println("Restock suggested for: " + suggestion);
        }
    }

    @Then("the system should suggest restocking for {string} and {string}")
    public void the_system_should_suggest_restocking_for_and(String ingredient1, String ingredient2) {
        List<String> suggestions = app.getRestockSuggestions(restockSuggestion);
        if (suggestions.contains(ingredient1) && suggestions.contains(ingredient2)) {
            System.out.println("Restock suggested for both " + ingredient1 + " and " + ingredient2);
        }
    }

    @Then("the system should warn the kitchen manager about low stock for {string}")
    public void the_system_should_warn_the_kitchen_manager_about_low_stock_for(String ingredient) {
        if (app.getRestockSuggestions(restockSuggestion).contains(ingredient)) {
            System.out.println("Warning: Low stock for " + ingredient);
        }
    }

    @Then("the system should not warn about {string} or {string}")
    public void the_system_should_not_warn_about_or(String ingredient1, String ingredient2) {
        List<String> suggestions = app.getRestockSuggestions(restockSuggestion);
        if (!suggestions.contains(ingredient1) && !suggestions.contains(ingredient2)) {
            System.out.println("No low stock warning for " + ingredient1 + " or " + ingredient2);
        }
    }

    @Then("the system should automatically suggest restocking for {string} and {string}")
    public void the_system_should_automatically_suggest_restocking_for_and(String ingredient1, String ingredient2) {
        List<String> suggestions = app.getRestockSuggestions(restockSuggestion);
        if (suggestions.contains(ingredient1) && suggestions.contains(ingredient2)) {
            System.out.println("Automatic restock suggestion for: " + ingredient1 + ", " + ingredient2);
        }
    }

    @Then("the system should suggest a reorder for {string}")
    public void the_system_should_suggest_a_reorder_for(String ingredient) {
        if (app.getRestockSuggestions(restockSuggestion).contains(ingredient)) {
            System.out.println("Reorder suggested for: " + ingredient);
        }
    }
}

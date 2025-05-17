package FeatureTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import production_code.actors.InventoryManager;
import production_code.core.Mainn;

import java.util.Map;
import java.util.List;

import static org.junit.Assert.*;

public class notify_users_of_low_stock_ingredients {
	private Mainn app;
	private InventoryManager inventoryManager;
	private String lowStockAlert;

	public notify_users_of_low_stock_ingredients() {
		app = new Mainn();
		inventoryManager = new InventoryManager();
		app.addInventoryManager(inventoryManager);
	}

	@Given("the inventory has the following ingredients:")
	public void theInventoryHasTheFollowingIngredients(DataTable dataTable) {
		List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> row : rows) {
			String ingredient = row.get("ingredient");
			int quantity = Integer.parseInt(row.get("quantity"));
			app.addIngredient(inventoryManager, ingredient, quantity);
		}
	}

	@Given("the critical stock level is {int}")
	public void theCriticalStockLevelIs(int stockLevel) {
		app.setCriticalStockLevel(stockLevel);
	}

	@When("the system checks for low stock ingredients")
	public void theSystemChecksForLowStockIngredients() {
		lowStockAlert = app.generateLowStockAlert(inventoryManager);
	}

	@Then("the kitchen manager should receive an alert for the following low stock ingredients:")
	public void theKitchenManagerShouldReceiveAnAlertForTheFollowingLowStockIngredients(DataTable dataTable) {
		List<String> expectedLowStockIngredients = dataTable.asList(String.class);
		assertTrue("Alert should indicate low stock", lowStockAlert.contains("The following ingredients are low in stock:"));
		for (String ingredient : expectedLowStockIngredients) {
			Integer quantity = inventoryManager.getInventory().get(ingredient);
			assertNotNull("Ingredient '" + ingredient + "' not found in inventory. Ensure it is set up in the 'Given' step.", quantity);
			String expectedMessage = "- " + ingredient + " (" + quantity + " units)";
			assertTrue("Alert should contain low stock ingredient: " + expectedMessage, lowStockAlert.contains(expectedMessage));
		}
	}

	@Then("the kitchen manager should receive no low stock alerts")
	public void theKitchenManagerShouldReceiveNoLowStockAlerts() {
		assertEquals("No low stock alert should be generated", "No low stock ingredients detected.", lowStockAlert);
	}
}
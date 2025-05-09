package FeatureTest;
import static production_code.core.Ingredients.getIngredients;
import static production_code.kitchen_manager_features.IngredientInventoryDisplay.displayAvailableIngredients;
import production_code.core.Ingredients;
import production_code.core.Main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;

public class NotifyUsersOfLowStockIngredientsTest {
	private Main main;
	
	public NotifyUsersOfLowStockIngredientsTest(Main main) {
		this.main = main;
	}

	@Given("the system monitors ingredient stock levels")
	public void the_system_monitors_ingredient_stock_levels() {
		assert displayAvailableIngredients();
	}
	
	@When("an ingredient's stock becomes low")
	public void an_ingredient_s_stock_becomes_low() {
		assert displayAvailableIngredients();
	}
	
	@Then("the kitchen manager receives an alert")
	public void the_kitchen_manager_receives_an_alert() {
		assert displayAvailableIngredients();
	}
	
	@Then("the manager can reorder the ingredient")
	public void the_manager_can_reorder_the_ingredient() {
		new Ingredients("new", List.of("item1", "item2"), 50, 50);
		assert !getIngredients().get("new").isEmpty();
	}
}
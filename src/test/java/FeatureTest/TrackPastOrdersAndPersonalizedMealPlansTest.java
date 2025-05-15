
package FeatureTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.actors.Customer;

import production_code.core.Ingredients;
import production_code.core.Meal;
import production_code.core.Order;
import production_code.core.Invoice;
import production_code.core.Mainn;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;


import java.util.List;

import static org.junit.Assert.*;

public class TrackPastOrdersAndPersonalizedMealPlansTest {
	private Mainn app;
	private Customer customer;
	private List<String> pastOrders;
	private List<String> suggestions;

	public TrackPastOrdersAndPersonalizedMealPlansTest(Mainn app) {
		this.app = app;
	}

	@Given("I am a registered customer")
	public void i_am_a_registered_customer() {
		app.resetLists();
		customer = new Customer("John Doe", "Vegan", List.of("Peanuts"));
		app.addCustomer(customer);
	}

	@Given("I have previously ordered meals")
	public void i_have_previously_ordered_meals() {
		app.addPastOrder(customer, "Vegan Burger");
		app.addPastOrder(customer, "Quinoa Salad");
	}

	@When("I view my past orders")
	public void i_view_my_past_orders() {
		pastOrders = customer.getPastOrders();
	}

	@Then("I should see a list of meals I previously ordered")
	public void i_should_see_a_list_of_meals_i_previously_ordered() {
		assertTrue(pastOrders.contains("Vegan Burger"));
		assertTrue(pastOrders.contains("Quinoa Salad"));
	}

	@Then("I should be able to reorder any of them")
	public void i_should_be_able_to_reorder_any_of_them() {
		String reorder = app.reorderMeal(customer, "Vegan Burger");
		assertEquals("Vegan Burger", reorder);
	}

	@Given("I am a chef and I have access to customer order history")
	public void i_am_a_chef_and_i_have_access_to_customer_order_history() {
		app.resetLists();
		customer = new Customer("Jane Doe", "Vegan", List.of());
		app.addCustomer(customer);
		app.addPastOrder(customer, "Tofu Stir Fry");
		app.addPastOrder(customer, "Lentil Soup");
	}

	@Given("the customer has previously ordered Vegan meals")
	public void the_customer_has_previously_ordered_vegan_meals() {
		assertTrue(customer.getPastOrders().stream().allMatch(meal -> meal.toLowerCase().contains("tofu") || meal.toLowerCase().contains("lentil")));
	}

	@When("I analyze their past orders")
	public void i_analyze_their_past_orders() {
		suggestions = app.suggestMeals(customer);
	}

	@Then("I should be able to suggest personalized Vegan meals for the customer")
	public void i_should_be_able_to_suggest_personalized_vegan_meals_for_the_customer() {
		assertFalse(suggestions.isEmpty());
		assertTrue(suggestions.contains("Vegan Salad"));
	}

	@Given("I am a system administrator")
	public void i_am_a_system_administrator() {
		app.resetLists();
	}

	@Given("customer order history exists in the database")
	public void customer_order_history_exists_in_the_database() {
		customer = new Customer("Admin View", "Vegetarian", List.of());
		app.addCustomer(customer);
		app.addPastOrder(customer, "Veggie Pasta");
		app.addPastOrder(customer, "Grilled Vegetables");
	}

	@When("I retrieve the order history for a customer")
	public void i_retrieve_the_order_history_for_a_customer() {
		pastOrders = customer.getPastOrders();
	}

	@Then("I should see a complete and accurate list of their past meal orders")
	public void i_should_see_a_complete_and_accurate_list_of_their_past_meal_orders() {
		assertEquals(2, pastOrders.size());
		assertTrue(pastOrders.contains("Veggie Pasta"));
		assertTrue(pastOrders.contains("Grilled Vegetables"));
	}

	@Then("I should be able to generate reports on order trends")
	public void i_should_be_able_to_generate_reports_on_order_trends() {
		String report = app.analyzeMealPreferences(customer);
		assertNotNull(report);
		assertTrue(report.contains("trend"));
	}
}
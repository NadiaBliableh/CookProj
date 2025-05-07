package FeatureTest;
import static production_code.chif_features.SuggestPersonalizedMealPlans.suggestMealPlan;
import static production_code.chif_features.SuggestPersonalizedMealPlans.viewCustomerOrderHistory;
import static production_code.core.Main.Login;
import static production_code.files_manager.DataManager.*;
import static production_code.system_features.CustomerOrderHistoryManager.*;

import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.core.Main;
import production_code.core.Meal;
import production_code.core.Specialty;
import production_code.customer_features.MealOrder;
import production_code.customer_features.ViewPastMealOrders;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TrackPastOrdersAndPersonalizedMealPlansTest {
	private Main main;
	private final Customer customer;
	private boolean loginResult;
	private boolean displayed;
	
	public TrackPastOrdersAndPersonalizedMealPlansTest(Main main) {
		this.main = main;
		this.customer = new Customer("customer", "custpass");
		addCustomer(customer.getUsername(), customer);
		Chef chef = new Chef("chef", "chefpass");
		addChef(chef.getUsername(), chef);
	}
	
	@Given("a customer is logged into their account")
	public void a_customer_is_logged_into_their_account() {
		loginResult = Login("customer", "custpass", getCustomerMap());
		assert loginResult;
	}
	
	@When("they navigate to the order history section")
	public void they_navigate_to_the_order_history_section() {
		List<String> ingredients = new ArrayList<>();
		ingredients.add("Beef");
		ingredients.add("Cheese");
		ingredients.add("Lettuce");
		
		Meal meal = new Meal(ingredients, "Beef Meal", Specialty.BEEF_DISHES, 5);
		addMeal("Beef Meal", meal);
		MealOrder order = new MealOrder(customer.getUsername(), meal);
		order.confirmOrder(order, getMealMap(), customer, getChefSpeciality());
		
		Map<LocalDateTime, Meal> orderHistory = customer.viewOrderHistory();
		assert !orderHistory.isEmpty();
	}
	
	@Then("they should see a list of their past meal orders")
	public void they_should_see_a_list_of_their_past_meal_orders() {
		assert ViewPastMealOrders.printOrderHistory(customer);
	}
	
	@Given("a chef is logged into the system")
	public void a_chef_is_logged_into_the_system() {
		loginResult = Login("chef", "chefpass", getChefMap());
		assert loginResult;
	}
	
	@When("they access a specific customer’s order history")
	public void they_access_a_specific_customer_s_order_history() {
		displayed = viewCustomerOrderHistory(new ArrayList<>(List.of(customer)));
	}
	
	@Then("they should see a list of past meals ordered by the customer")
	public void they_should_see_a_list_of_past_meals_ordered_by_the_customer() {
		assert displayed;
	}
	
	@Then("they should be able to suggest personalized meal plans based on the order history")
	public void they_should_be_able_to_suggest_personalized_meal_plans_based_on_the_order_history() {
		suggestMealPlan(customer);
	}
	
	@Given("the system administrator has access to the order database")
	public void the_system_administrator_has_access_to_the_order_database() {
		saveOrderHistory();
	}
	
	@And("they should be able to analyze trends to improve service offerings")
	public void they_should_be_able_to_analyze_trends_to_improve_service_offerings() {
		calculateTop5MealsOrdered(getMealRepetitionCount(), getCustomersOrders());
		assert printTop5MealsOrdered(getMealRepetitionCount());
	}
}

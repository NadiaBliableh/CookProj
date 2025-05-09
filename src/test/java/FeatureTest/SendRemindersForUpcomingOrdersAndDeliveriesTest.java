package FeatureTest;
import static production_code.chif_features.AssignedCookingTasks.displayAssignedCookingTasks;
import static production_code.customer_features.MealReminder.remindCustomer;
import static production_code.files_manager.DataManager.*;
import static production_code.kitchen_manager_features.AssignTasksToChefs.assignTasksToChefs;

import production_code.actors.Customer;
import production_code.core.Main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import production_code.core.Meal;
import production_code.core.Specialty;
import production_code.customer_features.MealOrder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SendRemindersForUpcomingOrdersAndDeliveriesTest {
	private Main main;
	private final Customer customer;
	private final Meal meal;



	public SendRemindersForUpcomingOrdersAndDeliveriesTest(Main main) {
		this.main = main;
		this.customer = new Customer("customer", "custpass");
		addCustomer(customer.getUsername(), customer);
		
		List<String> ingredients = new ArrayList<>();
		ingredients.add("Beef");
		ingredients.add("Cheese");
		ingredients.add("Lettuce");
		
		meal = new Meal(ingredients, "Beaf Meal", Specialty.BEEF_DISHES, 5);
		addMeal("Beef Meal", meal);
		MealOrder order = new MealOrder(customer.getUsername(), meal);
		order.confirmOrder(order, getMealMap(), customer, getChefSpeciality());
	}
	
	@Given("a customer has an upcoming meal delivery")
	public void a_customer_has_an_upcoming_meal_delivery() {
		customer.addOrderToHistory(LocalDateTime.now(), meal);
	}
	
	@When("the delivery time approaches")
	public void the_delivery_time_approaches() {
		assert customer.viewOrderHistory()
				.get(customer.viewOrderHistory().keySet().iterator().next())
				.getOrderDate().isBefore(LocalDateTime.now().plusMinutes(5));
	}
	
	@Then("the customer receives a reminder notification")
	public void the_customer_receives_a_reminder_notification() {
		assert remindCustomer(customer, customer.viewOrderHistory());
	}
	
	@Given("a chef has a scheduled cooking task")
	public void a_chef_has_a_scheduled_cooking_task() {
		assignTasksToChefs(getChefSpeciality(), getSpecialtyMeals());
		assert displayAssignedCookingTasks(Specialty.BEEF_DISHES, getSpecialtyMeals());
	}
	
	@When("the cooking time approaches")
	public void the_cooking_time_approaches() {
		assert LocalDateTime.now().isBefore(LocalDateTime.now().plusMinutes(5));
	}
	
	@Then("the chef receives a notification to prepare the meal")
	public void the_chef_receives_a_notification_to_prepare_the_meal() {
		assert LocalDateTime.now().isBefore(LocalDateTime.now().plusMinutes(5));
	}
}
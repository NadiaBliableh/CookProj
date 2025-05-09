package FeatureTest;
import static production_code.chif_features.AssignedCookingTasks.*;
import static production_code.files_manager.DataManager.*;
import static production_code.kitchen_manager_features.AssignTasksToChefs.*;

import production_code.actors.Customer;
import production_code.core.Main;
import production_code.core.Meal;
import production_code.core.Specialty;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import production_code.customer_features.MealOrder;
import java.util.List;

public class AssignTasksToChefsAndKitchenStaffTest {

	private Main main;
	private final Customer customer;
	private final Meal meal;
	
	public AssignTasksToChefsAndKitchenStaffTest(Main main) {

		this.main = main;
		customer = new Customer("customer", "custpass");
		meal = new Meal(List.of("Beef", "Cheese", "Lettuce"), "BeefffMeal", Specialty.BEEF_DISHES, 45);
		addMeal("BeefffMeal", meal);
		MealOrder mealOrder = new MealOrder(customer.getUsername(), meal);
		mealOrder.confirmOrder(mealOrder, getMealMap(), customer, getChefSpeciality());
	}
	
	@Given("a list of chefs with their current workload and expertise")
	public void a_list_of_chefs_with_their_current_workload_and_expertise() {
		assert printChefSpeciality(getChefSpeciality());
	}
	
	@When("the manager selects a task to assign")
	public void the_manager_selects_a_task_to_assign() {
		assert assignTasksToChefs(getChefSpeciality(), getSpecialtyMeals());
	}
	
	@Then("the system should assign the task to the most suitable chef")
	public void the_system_should_assign_the_task_to_the_most_suitable_chef() {
		assert printSpecialtyMeals(getSpecialtyMeals());
	}
	
	@Given("a chef has been assigned a new task")
	public void a_chef_has_been_assigned_a_new_task() {
		assignTasksToChefs(getChefSpeciality(), getSpecialtyMeals());

		assert viewOrderDeliveryDates(Specialty.BEEF_DISHES, getSpecialtyMeals());
	}
	
	@When("the assignment is confirmed")
	public void the_assignment_is_confirmed() {
		assert isConfirmed;
	}
	
	@When("the chef receives a notification with the task details")
	public void the_chef_receives_a_notification_with_the_task_details() {

		assignTasksToChefs(getChefSpeciality(), getSpecialtyMeals());
		assert displayAssignedCookingTasks(Specialty.BEEF_DISHES, getSpecialtyMeals());
	}
	
	@Then("the chef will complete the task")
	public void the_chef_will_complete_the_task() {

		MealOrder order = new MealOrder(customer.getUsername(), meal);
		order.confirmOrder(order, getMealMap(), customer, getChefSpeciality());
		assert finishCookingTask(meal, getChefSpeciality());
	}
}
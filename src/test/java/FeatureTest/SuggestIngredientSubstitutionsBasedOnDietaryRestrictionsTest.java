package FeatureTest;
import static production_code.chif_features.AssignedCookingTasks.displayAssignedCookingTasks;
import static production_code.files_manager.DataManager.*;
import static production_code.kitchen_manager_features.AssignTasksToChefs.assignTasksToChefs;
import static production_code.system_features.SuggestAlternativeIngredients.suggestAlternativeIngredients;
import production_code.actors.Customer;
import production_code.core.Main;
import production_code.core.Specialty;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;

public class SuggestIngredientSubstitutionsBasedOnDietaryRestrictionsTest {
	private Main main;
	private final Customer customer;
	private String restriction;
	
	public SuggestIngredientSubstitutionsBasedOnDietaryRestrictionsTest(Main main) {
		this.main = main;
		this.customer = new Customer("customer", "custpass");
		addCustomer(customer.getUsername(), customer);
	}
	
	@Given("the customer has a dietary restriction for {string}")
	public void the_customer_has_a_dietary_restriction_for(String string) {
		restriction = string;
		customer.addAllergy(restriction);
		assert true;
	}
	
	@Given("the meal contains the ingredient {string}")
	public void the_meal_contains_the_ingredient(String string) {
		List<String> alternatives = suggestAlternativeIngredients(string);
		assert !alternatives.isEmpty();
	}
	
	@When("the system detects a conflict")
	public void the_system_detects_a_conflict() {
		assert customer.getAllergies().contains(restriction);
	}
	
	@Then("the system should suggest {string} as an alternative")
	public void the_system_should_suggest_as_an_alternative(String string) {
		List<String> alternatives = suggestAlternativeIngredients(string);
		assert !alternatives.isEmpty();
	}
	
	@Then("notify the chef about the suggested substitution")
	public void notify_the_chef_about_the_suggested_substitution() {
		assignTasksToChefs(getChefSpeciality(), getSpecialtyMeals());
		assert displayAssignedCookingTasks(Specialty.BEEF_DISHES, getSpecialtyMeals());
	}
	
	@Given("the customer has an allergy to {string}")
	public void the_customer_has_an_allergy_to(String string) {
		restriction = string;
		customer.addAllergy(restriction);
		assert true;
	}
}
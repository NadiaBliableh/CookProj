
package FeatureTest;
import io.cucumber.java.en.*;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
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
public class StoreDietaryPreferencesAndAllergieTest {
	private Mainn app;
	private Customer customer;

	public StoreDietaryPreferencesAndAllergieTest(Mainn app) {
		this.app = app;
	}

	@Given("a customer named {string}")
	public void a_customer_named(String name) {
		app.resetLists();
		customer = new Customer(name, "", new ArrayList<>());
		app.addCustomer(customer);
	}

	@When("they input dietary preference as {string}")
	public void they_input_dietary_preference_as(String preference) {
		customer.setDietaryPreference(preference);
	}

	@When("they input allergy to {string}")
	public void they_input_allergy_to(String allergy) {
		customer.setAllergy(allergy);
	}

	@Then("the system should store their preferences and allergies")
	public void the_system_should_store_their_preferences_and_allergies() {
		assertNotNull(customer);
		assertFalse(customer.getDietaryPreference().isEmpty());
		assertFalse(customer.getAllergy().isEmpty());
	}

	@Given("a customer named {string} with a dietary preference {string} and allergy to {string}")
	public void a_customer_named_with_a_dietary_preference_and_allergy_to(String name, String preference, String allergy) {
		app.resetLists();
		customer = new Customer(name, preference, List.of(allergy));
		app.addCustomer(customer);
	}

	@When("the system suggests meals")
	public void the_system_suggests_meals() {
		app.suggestMeals(customer);
	}

	@Then("all suggested meals should match the dietary preference {string}")
	public void all_suggested_meals_should_match_the_dietary_preference(String preference) {
		for (String meal : customer.getSuggestedMeals()) {
			if (preference.equalsIgnoreCase("Vegan")) {
				assertFalse("Meal not vegan: " + meal, meal.toLowerCase().contains("meat"));
			}
		}
	}

	@Then("should not contain {string}")
	public void should_not_contain(String allergy) {
		for (String meal : customer.getSuggestedMeals()) {
			assertFalse("Meal contains allergen: " + meal,
					meal.toLowerCase().contains(allergy.toLowerCase()));
		}
	}

	@Given("a chef wants to prepare a meal for {string}")
	public void a_chef_wants_to_prepare_a_meal_for(String name) {
		app.resetLists();
		customer = app.getCustomerByName(name);
		if (customer == null) {
			customer = new Customer(name, "Vegan", List.of("Peanut"));
			app.addCustomer(customer);
		}
		assertNotNull("Customer should not be null", customer);
		assertEquals(name, customer.getName());
	}

	@When("the chef views Aria's dietary profile")
	public void the_chef_views_aria_s_dietary_profile() {
		customer = app.getCustomerByName("Aria");
		assertNotNull("Customer should not be null", customer);
		String dietaryPreference = customer.getDietaryPreference();
		String allergy = customer.getAllergy();
		System.out.println("Dietary preference: " + dietaryPreference);
		System.out.println("Allergy: " + allergy);
		assertNotNull("Dietary preference should not be null", dietaryPreference);
		assertNotNull("Allergy should not be null", allergy);
	}

	@Then("they should see {string} as the dietary preference")
	public void they_should_see_as_the_dietary_preference(String expectedDiet) {
		assertEquals(expectedDiet, customer.getDietaryPreference());
	}

	@Then("{string} listed under allergies")
	public void listed_under_allergies(String expectedAllergy) {
		assertEquals(expectedAllergy, customer.getAllergy());
	}
}
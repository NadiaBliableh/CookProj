package FeatureTest;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.actors.Customer;
import production_code.core.Mainn;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StoreDietaryPreferencesAndAllergieTest {
	private final Mainn app;
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
		customer.setAllergies(List.of(allergy));
	}

	@Then("the system should store their preferences and allergies")
	public void the_system_should_store_their_preferences_and_allergies() {
		assertNotNull(customer);
		assertFalse(customer.getDietaryPreference().isEmpty(), "Dietary preference should not be empty");
		assertFalse(customer.getAllergies().isEmpty(), "Allergies should not be empty");
	}

	@Given("a customer named {string} with a dietary preference {string} and allergy to {string}")
	public void a_customer_named_with_a_dietary_preference_and_allergy_to(String name, String preference, String allergy) {
		app.resetLists();
		customer = new Customer(name, preference, new ArrayList<>());
		customer.setAllergies(List.of(allergy));
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
				assertFalse(meal.toLowerCase().contains("meat"), "Meal not vegan: " + meal);
			}
		}
	}

	@Then("should not contain {string}")
	public void should_not_contain(String allergy) {
		for (String meal : customer.getSuggestedMeals()) {
			assertFalse(meal.toLowerCase().contains(allergy.toLowerCase()), "Meal contains allergen: " + meal);
		}
	}

	@Given("a chef wants to prepare a meal for {string}")
	public void a_chef_wants_to_prepare_a_meal_for(String name) {
		app.resetLists();
		customer = app.getCustomerByName(name);
		if (customer == null) {
			customer = new Customer(name, "Vegan", new ArrayList<>());
			customer.setAllergies(List.of("Peanut"));
			app.addCustomer(customer);
		}
		assertEquals(name, customer.getName());
	}

	@When("the chef views Aria's dietary profile")
	public void the_chef_views_aria_s_dietary_profile() {
		customer = app.getCustomerByName("Aria");
		String dietaryPreference = customer.getDietaryPreference();
		List<String> allergies = customer.getAllergies();
		System.out.println("Dietary preference: " + dietaryPreference);
		System.out.println("Allergies: " + allergies);
		assertNotNull(dietaryPreference, "Dietary preference should not be null");
		assertFalse(allergies.isEmpty(), "Allergies should not be empty");
	}

	@Then("they should see {string} as the dietary preference")
	public void they_should_see_as_the_dietary_preference(String expectedDiet) {
		assertEquals(expectedDiet, customer.getDietaryPreference());
	}

	@Then("{string} listed under allergies")
	public void listed_under_allergies(String expectedAllergy) {
		assertTrue(customer.getAllergies().contains(expectedAllergy));
	}
}
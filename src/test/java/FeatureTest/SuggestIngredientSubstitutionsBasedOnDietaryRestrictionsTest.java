package FeatureTest;

import production_code.actors.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Arrays;
import static org.junit.Assert.*;
import production_code.core.Mainn;

public class SuggestIngredientSubstitutionsBasedOnDietaryRestrictionsTest {
	private Mainn app;
	private Customer customer;
	private List<String> suggestions;
	private String substitutionSuggestion;
	private String originalIngredient;
	private String substitutedIngredient;
	private String alertMessage;

	public SuggestIngredientSubstitutionsBasedOnDietaryRestrictionsTest(Mainn app) {
		this.app = app;
	}

	@Given("the customer's dietary preference is {string}")
	public void the_customer_s_dietary_preference_is(String preference) {
		app.resetLists();
		customer = new Customer("Test User", preference, Arrays.asList());
		app.addCustomer(customer);
	}

	@Then("the system should suggest alternative ingredients suitable for {string} preference")
	public void the_system_should_suggest_alternative_ingredients_suitable_for_preference(String preference) {
		suggestions = app.suggestMeals(customer);
		assertFalse("Suggestions should not be empty for " + preference, suggestions.isEmpty());
		for (String meal : suggestions) {
			assertTrue("Meal should match preference",
					preference.equalsIgnoreCase("Vegan") ?
							!(meal.toLowerCase().contains("meat") || meal.toLowerCase().contains("chicken"))
							: true);
		}
	}

	@When("the system suggests {string} as a substitution")
	public void the_system_suggests_as_a_substitution(String suggestion) {
		substitutionSuggestion = suggestion;
	}

	@Then("the chef should be alerted that a substitution was made for {string} with {string}")
	public void the_chef_should_be_alerted_that_a_substitution_was_made_for_with(String original, String substitute) {
		this.originalIngredient = original;
		this.substitutedIngredient = substitute;
		alertMessage = app.getSubstitutionAlert(original, substitute);
		assertNotNull(alertMessage);
		assertTrue(alertMessage.contains(originalIngredient));
		assertTrue(alertMessage.contains(substitutedIngredient));
	}
}
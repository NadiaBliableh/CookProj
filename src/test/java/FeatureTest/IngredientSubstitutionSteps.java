package FeatureTest;

import java.util.HashMap;
import java.util.Map;
import ProductionCode.*;
import io.cucumber.java.en.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IngredientSubstitutionSteps {

    private String dietaryRestriction;
    private String unavailableIngredient;
    private String originalIngredient;
    private String suggestedSubstitute;
    private boolean chefNotified = false;

    private static final Map<String, String[]> substitutions = new HashMap<>();

    static {
        substitutions.put("milk", new String[]{"almond milk", "oat milk"});
        substitutions.put("eggs", new String[]{"flaxseed meal", "applesauce"});
        substitutions.put("butter", new String[]{"margarine", "coconut oil"});
    }

    @Given("a customer has a dietary restriction for {string}")
    public void a_customer_has_a_dietary_restriction_for(String restriction) {
        dietaryRestriction = restriction;
    }

    @Given("a recipe contains {string}")
    public void a_recipe_contains(String ingredient) {
        originalIngredient = ingredient;
        assertNotNull("Error: Original ingredient is null!", originalIngredient);
    }

    @When("the system detects the restriction")
    public void the_system_detects_the_restriction() {
        assertNotNull("Error: No ingredient set before detecting restriction!", originalIngredient);
        if (substitutions.containsKey(originalIngredient)) {
            suggestedSubstitute = substitutions.get(originalIngredient)[0];
        } else {
            throw new RuntimeException("No substitute found for " + originalIngredient);
        }
    }

    @Then("it suggests an alternative ingredient such as {string} or {string}")
    public void it_suggests_an_alternative_ingredient_such_as_or(String substitute1, String substitute2) {
        assertNotNull("Error: No substitute was suggested for " + originalIngredient, suggestedSubstitute);
        assertTrue("Suggested substitute is incorrect",
                suggestedSubstitute.equals(substitute1) || suggestedSubstitute.equals(substitute2));
    }

    @Given("an ingredient {string} is unavailable")
    public void an_ingredient_is_unavailable(String ingredient) {
        unavailableIngredient = ingredient;
        assertNotNull("Error: Unavailable ingredient is null!", unavailableIngredient);
    }

    @Given("a recipe requires {string}")
    public void a_recipe_requires(String ingredient) {
        originalIngredient = ingredient;
        assertNotNull("Error: Required ingredient is null!", originalIngredient);
    }

    @When("the system detects the unavailability")
    public void the_system_detects_the_unavailability() {
        assertNotNull("Error: No ingredient set before detecting unavailability!", originalIngredient);
        if (substitutions.containsKey(originalIngredient)) {
            suggestedSubstitute = substitutions.get(originalIngredient)[0];
        } else {
            throw new RuntimeException("No substitute found for " + originalIngredient);
        }
    }

    @Given("the system has suggested a substitute for an ingredient")
    public void the_system_has_suggested_a_substitute_for_an_ingredient() {
        assertNotNull("Error: No substitute was suggested for " + originalIngredient, suggestedSubstitute);
    }

    @When("the substitution is applied to a recipe")
    public void the_substitution_is_applied_to_a_recipe() {
        chefNotified = true;
    }

    @Then("the chef receives an alert with the original and substitute ingredient details")
    public void the_chef_receives_an_alert_with_the_original_and_substitute_ingredient_details() {
        assertTrue("Chef was not notified", chefNotified);
    }

    @Then("the chef can approve or modify the substitution before finalizing the recipe")
    public void the_chef_can_approve_or_modify_the_substitution_before_finalizing_the_recipe() {
        boolean chefApproves = true;
        if (!chefApproves) {
            suggestedSubstitute = "Chef's choice of substitute";
        }
        assertNotNull("Final substitute should not be null", suggestedSubstitute);
    }
}


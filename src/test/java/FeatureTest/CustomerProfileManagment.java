package FeatureTest;
import ProductionCode.Chef; // Import your service class
import ProductionCode.CustomerProfile;
import ProductionCode.CustomerProfileService;
import static org.junit.jupiter.api.Assertions.*;
import io.cucumber.java.en.*;
import java.util.*;

public class CustomerProfileManagment {
    private CustomerProfileService customerProfileService;
    private CustomerProfile customerProfile;
    private Chef chef;

    @Given("a customer with an empty profile")
    public void a_customer_with_an_empty_profile() {
        customerProfileService = new CustomerProfileService();
        customerProfile = null;  // No profile created yet
    }

    @When("the customer inputs dietary preferences {string} and allergies {string}")
    public void the_customer_inputs_dietary_preferences_and_allergies(String preferences, String allergies) {
        List<String> preferenceList = Arrays.asList(preferences.split(","));
        List<String> allergyList = Arrays.asList(allergies.split(","));

        customerProfileService.addCustomerProfile("John Doe", preferenceList, allergyList);
        customerProfile = customerProfileService.getCustomerProfile("John Doe");
    }

    @Then("the system should store the preferences and allergies")
    public void the_system_should_store_the_preferences_and_allergies() {
        assertNotNull(customerProfile, "Customer profile should be created.");
        assertFalse(customerProfile.getDietaryPreferences().isEmpty(), "Preferences should be stored.");
        assertFalse(customerProfile.getAllergies().isEmpty(), "Allergies should be stored.");
    }

    @Given("a customer with dietary preferences {string} and allergies {string}")
    public void a_customer_with_dietary_preferences_and_allergies(String preferences, String allergies) {
        List<String> preferenceList = Arrays.asList(preferences.split(","));
        List<String> allergyList = Arrays.asList(allergies.split(","));

        customerProfileService = new CustomerProfileService();
        customerProfileService.addCustomerProfile("Jane Doe", preferenceList, allergyList);
        customerProfile = customerProfileService.getCustomerProfile("Jane Doe");

        assertNotNull(customerProfile, "Customer profile should exist.");
    }

    @When("a chef views the customer profile")
    public void a_chef_views_the_customer_profile() {
        chef = new Chef();
        chef.viewCustomerProfile(customerProfile);  // Simulating viewing the profile
    }

    @Then("the chef should see {string} as preferences and {string} as allergies")
    public void the_chef_should_see_as_preferences_and_as_allergies(String expectedPreferences, String expectedAllergies) {
        assertEquals(Arrays.asList(expectedPreferences.split(",")), customerProfile.getDietaryPreferences(), "Preferences should match.");
        assertEquals(Arrays.asList(expectedAllergies.split(",")), customerProfile.getAllergies(), "Allergies should match.");
    }
}

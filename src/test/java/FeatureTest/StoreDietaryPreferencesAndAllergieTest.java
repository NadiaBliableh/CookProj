package FeatureTest;
import static production_code.chif_features.ViewCustomerDietaryPreferences.viewCustomerDietaryPreferences;
import static production_code.core.Main.Login;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.saveCustomerMapToFile;
import static production_code.files_manager.FilePaths.CUSTOMER_FILE_NAME;
import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.core.Main;
import production_code.customer_features.EnterDietaryPreferencesAllergies;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StoreDietaryPreferencesAndAllergieTest {
	private Main main;
	private final Customer customer;
	private boolean loginResult;
	private boolean saved;
	
	public StoreDietaryPreferencesAndAllergieTest(Main main) {
		this.main = main;
		this.customer = new Customer("customer", "custpass");
		addCustomer(customer.getUsername(), customer);
		Chef chef = new Chef("chef", "chefpass");
		addChef(chef.getUsername(), chef);
	}
	
	@Given("the customer is logged in")
	public void the_customer_is_logged_in() {
		loginResult = Login("customer", "custpass", getCustomerMap());
		assert loginResult;
	}
	
	@When("the customer enters their dietary preferences as {string} and allergies as {string}")
	public void the_customer_enters_their_dietary_preferences_as_and_allergies_as(String string, String string2) {
		EnterDietaryPreferencesAllergies entry = new EnterDietaryPreferencesAllergies(
				customer.getUsername(), string, string2
		);
		boolean isAdded = entry.addNewCell();
		assert isAdded;
	}
	
	@When("saves the information")
	public void saves_the_information() {
		saved = saveCustomerMapToFile(getCustomerMap(), CUSTOMER_FILE_NAME);
	}
	
	@Then("the system should store the dietary preferences and allergies")
	public void the_system_should_store_the_dietary_preferences_and_allergies() {
		assert !customer.getAllergies().isEmpty();
		assert !customer.getDietaryPreferences().isEmpty();
	}
	
	@Then("confirm that the information was saved successfully")
	public void confirm_that_the_information_was_saved_successfully() {
		assert saved;
	}
	
	@Given("the chef is logged in")
	public void the_chef_is_logged_in() {
		loginResult = Login("chef", "chefpass", getChefMap());
		assert loginResult;
	}
	
	@When("the chef selects a customer profile")
	public void the_chef_selects_a_customer_profile() {
		getCustomer(customer.getUsername());
		assert true;
	}
	
	@Then("the system should display the customer's dietary preferences and allergies")
	public void the_system_should_display_the_customer_s_dietary_preferences_and_allergies() {
		assert viewCustomerDietaryPreferences(customer);
	}
}

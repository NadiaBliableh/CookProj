package FeatureTest;
import static production_code.core.Main.Login;
import static production_code.core.Main.systemIsRunning;
import static production_code.files_manager.DataManager.*;
import production_code.actors.Admin;
import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.actors.KitchenManager;
import production_code.core.Main;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginTest {
	private Main main;
	private final Admin admin;
	private final Chef chef;
	private final KitchenManager kitchenManager;
	private final Customer customer;
	private boolean loginResult;
	private String errorMessage;
	
	public LoginTest(Main main) {
		this.main = main;
		this.admin = new Admin("admin", "admin");
		addAdmin(admin.getUsername(), admin);
		
		this.chef = new Chef("chef", "chefpass");
		addChef(chef.getUsername(), chef);
		
		this.kitchenManager = new KitchenManager("kmanager", "kmanpass");
		addKitchenManager(kitchenManager.getUsername(), kitchenManager);
		
		this.customer = new Customer("customer", "custpass");
		addCustomer(customer.getUsername(), customer);
		this.loginResult = false;
		this.errorMessage = "";
	}
	
	@Given("the system is running")
	public void the_system_is_running() {
		assert systemIsRunning();
	}
	
	@When("the user enters a valid Admin username and password")
	public void the_user_enters_a_valid_admin_username_and_password() {
		loginResult = Login("admin", "admin", getAdminMap());
		assert loginResult;
	}
	
	@Then("the user should be redirected to the Admin dashboard")
	public void the_user_should_be_redirected_to_the_admin_dashboard() {
		admin.login();
		assert admin.isLoggedIn();
	}
	
	@When("the user enters a valid Chef username and password")
	public void the_user_enters_a_valid_chef_username_and_password() {
		loginResult = Login("chef", "chefpass", getChefMap());
		assert loginResult;
	}
	
	@Then("the user should be redirected to the Chef dashboard")
	public void the_user_should_be_redirected_to_the_chef_dashboard() {
		chef.login();
		assert chef.isLoggedIn();
	}
	
	@When("the user enters a valid Customer username and password")
	public void the_user_enters_a_valid_customer_username_and_password() {
		loginResult = Login("customer", "custpass", getCustomerMap());
		assert loginResult;
	}
	
	@Then("the user should be redirected to the Customer dashboard")
	public void the_user_should_be_redirected_to_the_customer_dashboard() {
		customer.login();
		assert customer.isLoggedIn();
	}
	
	@When("the user enters a valid Kitchen Manager username and password")
	public void the_user_enters_a_valid_kitchen_manager_username_and_password() {
		loginResult = Login("kmanager", "kmanpass", getKitchenManagerMap());
		assert loginResult;
	}
	
	@Then("the user should be redirected to the Kitchen Manager dashboard")
	public void the_user_should_be_redirected_to_the_kitchen_manager_dashboard() {
		kitchenManager.login();
		assert kitchenManager.isLoggedIn();
	}
	
	@When("the user enters an invalid Admin username or password")
	public void the_user_enters_an_invalid_admin_username_or_password() {
		loginResult = Login("admin", "wrongpass", getAdminMap());
		if (!loginResult) {
			errorMessage = "Invalid credentials.";
		}
	}
	
	@Then("an error message should be displayed saying {string}")
	public void an_error_message_should_be_displayed_saying(String expectedMessage) {
		assert errorMessage.equals("Invalid credentials.");
	}
	
	@When("the user enters an invalid Chef username or password")
	public void the_user_enters_an_invalid_chef_username_or_password() {
		loginResult = Login("chef", "wrongpass", getChefMap());
		if (!loginResult) {
			errorMessage = "Invalid credentials.";
		}
	}
	
	@When("the user enters an invalid Customer username or password")
	public void the_user_enters_an_invalid_customer_username_or_password() {
		loginResult = Login("customer", "wrongpass", getCustomerMap());
		if (!loginResult) {
			errorMessage = "Invalid credentials.";
		}
	}
	
	@When("the user enters an invalid Kitchen Manager username or password")
	public void the_user_enters_an_invalid_kitchen_manager_username_or_password() {
		loginResult = Login("kmanager", "wrongpass", getKitchenManagerMap());
		if (!loginResult) {
			errorMessage = "Invalid credentials.";
		}
	}
	
	@When("the user leaves the username and password fields empty")
	public void the_user_leaves_the_username_and_password_fields_empty() {
		loginResult = Login("", "", getAdminMap());
		if (!loginResult) {
			errorMessage = "Please enter username and password";
		}
	}
	
	@Then("a validation message should be displayed saying {string}")
	public void a_validation_message_should_be_displayed_saying(String expectedMessage) {
		assert errorMessage.equals("Please enter username and password");
	}
}
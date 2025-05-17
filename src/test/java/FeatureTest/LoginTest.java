package FeatureTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.core.Mainn;
import production_code.actors.User;
import static org.junit.Assert.*;

public class LoginTest {
	private Mainn app;
	private String loginResult;
	private String userRole;

	public LoginTest() {
		app = new Mainn();
	}

	@Given("a user with username {string} and password {string} exists with role {string}")
	public void a_user_with_username_and_password_exists_with_role(String username, String password, String role) {
		app.registerUser(username, password, role);
	}

	@Given("no user exists with username {string}")
	public void no_user_exists_with_username(String username) {
		// No action needed; app starts with empty user list
	}

	@When("the user attempts to log in with username {string} and password {string}")
	public void the_user_attempts_to_log_in_with_username_and_password(String username, String password) {
		loginResult = app.login(username, password);
		if (loginResult.startsWith("Login successful")) {
			userRole = app.getCurrentUserRole();
		}
	}

	@Then("the system should authenticate the user successfully")
	public void the_system_should_authenticate_the_user_successfully() {
		assertTrue("Login should be successful", loginResult.startsWith("Login successful"));
	}

	@Then("the user should have access to {string} functionalities")
	public void the_user_should_have_access_to_functionalities(String role) {
		assertEquals("User should have correct role", role, userRole);
	}

	@Then("the system should deny access")
	public void the_system_should_deny_access() {
		assertFalse("Login should fail", loginResult.startsWith("Login successful"));
	}

	@Then("the user should receive an error message {string}")
	public void the_user_should_receive_an_error_message(String expectedMessage) {
		assertEquals("Error message should match", expectedMessage, loginResult.trim()); // Trim to remove extra characters
	}
}
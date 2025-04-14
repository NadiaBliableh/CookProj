package FeatureTest;



import io.cucumber.java.en.*;
import static org.junit.jupiter.api.Assertions.*;
import ProductionCode.UserService; // Import your service class
import ProductionCode.User; // Import your User class

public class login {

    private UserService userService;
    private boolean loginResult;
    private String loginMessage;

    public login() {
        userService = new UserService(); // Initialize user service
    }

    @Given("a registered user {string} with password {string}")
    public void a_registered_user_with_password(String username, String password) {
        userService.registerUser(username, password);
    }

    @When("the user enters username {string} and password {string}")
    public void the_user_enters_username_and_password(String username, String password) {
        loginResult = userService.login(username, password);
        loginMessage = userService.getLoginMessage();
    }

    @Then("the system should authenticate the user and grant access")
    public void the_system_should_authenticate_the_user_and_grant_access() {
        assertTrue(loginResult);
    }

    @Then("the system should deny access with an {string} message")
    public void the_system_should_deny_access_with_an_message(String expectedMessage) {
        assertFalse(loginResult);
        assertEquals(expectedMessage, loginMessage);
    }

    @Given("no user exists with username {string}")
    public void no_user_exists_with_username(String username) {
        // Ensure that no user is registered with this username
    }

    @Then("the system should deny access with a {string} message")
    public void the_system_should_deny_access_with_a_message(String expectedMessage) {
        assertFalse(loginResult);
        assertEquals(expectedMessage, loginMessage);
    }
}

package FeatureTest;

import ProductionCode.Customer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ProductionCode.*;
import java.util.List;
import static org.junit.Assert.*;
import io.cucumber.java.en.*;


import static org.junit.Assert.*;

public class TrackOrders {
    Customer customer;
    Chef chef;
    Admin admin;
    List<Meal> orderHistory;
    List<Meal> customerOrders;
    List<Meal> databaseOrders;

    @Given("a customer is logged into their account")
    public void a_customer_is_logged_into_their_account() {
        customer = new Customer("testUser");
        assertTrue(customer.isLoggedIn());
    }

    @When("they navigate to the order history section")
    public void they_navigate_to_the_order_history_section() {
        orderHistory = customer.viewOrderHistory();
        assertNotNull(orderHistory);
    }

    @Then("they should see a list of their past meal orders")
    public void they_should_see_a_list_of_their_past_meal_orders() {
        assertFalse(orderHistory.isEmpty());
    }

    @Then("they should have the option to reorder a meal they liked")
    public void they_should_have_the_option_to_reorder_a_meal_they_liked() {
        Meal meal = orderHistory.get(0);
        assertTrue(customer.canReorder(meal));
    }

    @Given("a chef is logged into the system")
    public void a_chef_is_logged_into_the_system() {
        chef = new Chef("chefUser");
        assertTrue(chef.isLoggedIn());
    }

    @When("they access a specific customer’s order history")
    public void they_access_a_specific_customer_s_order_history() {
        customerOrders = chef.viewCustomerOrders("testUser");
        assertNotNull(customerOrders);
    }

    @Then("they should see a list of past meals ordered by the customer")
    public void they_should_see_a_list_of_past_meals_ordered_by_the_customer() {
        assertFalse(customerOrders.isEmpty());
    }

    @Then("they should be able to suggest personalized meal plans based on the order history")
    public void they_should_be_able_to_suggest_personalized_meal_plans_based_on_the_order_history() {
        MealPlan mealPlan = chef.suggestMealPlan(customerOrders);
        assertNotNull(mealPlan);
    }

    @Given("the system administrator has access to the order database")
    public void the_system_administrator_has_access_to_the_order_database() {
        admin = new Admin("adminUser");
        assertTrue(admin.hasDatabaseAccess());
    }

    @When("they query for customer order history")
    public void they_query_for_customer_order_history() {
        databaseOrders = admin.retrieveOrderHistory();
        assertNotNull(databaseOrders);
    }

    @Then("they should be able to retrieve past orders")
    public void they_should_be_able_to_retrieve_past_orders() {
        assertFalse(databaseOrders.isEmpty());
    }

    @Then("they should be able to analyze trends to improve service offerings")
    public void they_should_be_able_to_analyze_trends_to_improve_service_offerings() {
        TrendsReport report = admin.analyzeTrends(databaseOrders);
        assertNotNull(report);
    }
}

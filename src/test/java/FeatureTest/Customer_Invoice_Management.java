package FeatureTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import production_code.actors.Customer;
import production_code.core.Invoice;
import production_code.core.Mainn;
import production_code.core.Meal;
import production_code.core.Order;
import production_code.core.Ingredients;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Step definitions for managing customer invoices.
 */
public class Customer_Invoice_Management {
    private final Mainn app;
    private Customer customer;
    private Invoice lastInvoice;

    public Customer_Invoice_Management() {
        app = new Mainn();
    }

    /**
     * Initializes a customer with the given details and creates an order.
     */
    @Given("a customer {string} has placed an order with the following meals:")
    public void a_customer_has_placed_an_order_with_the_following_meals(String customerName, DataTable dataTable) {
        customer = new Customer(customerName, "Vegetarian", new ArrayList<>());
        app.addCustomer(customer);

        List<Meal> meals = new ArrayList<>();
        List<Map<String, String>> mealsData = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : mealsData) {
            String mealName = row.get("meal");
            List<Ingredients> ingredients = new ArrayList<>();
            ingredients.add(new Ingredients(mealName, true)); // Using meal name, assuming available
            meals.add(new Meal(ingredients));
        }
        Order order = new Order(customer, meals);
        app.createInvoice(customer, order); // Add order indirectly via invoice
    }

    /**
     * Generates an invoice for the customer's order.
     */
    @When("an invoice is generated for the customer")
    public void an_invoice_is_generated_for_the_customer() {
        Order lastOrder = app.getLastOrder();
        lastInvoice = (lastOrder != null) ? app.createInvoice(customer, lastOrder) : null;
        assertNotNull(lastInvoice, "Invoice should be generated if an order exists");
    }

    /**
     * Verifies that the invoice contains the correct total amount.
     */
    @Then("the invoice total should be {double}")
    public void the_invoice_total_should_be(double expectedTotal) {
        assertNotNull(lastInvoice, "Invoice should not be null");
        assertEquals(expectedTotal, lastInvoice.getTotalPrice(), 0.01,
                "Invoice total should match the expected amount");
    }

    /**
     * Verifies that the invoice includes details of all meals.
     */
    @Then("the invoice should include details of all meals")
    public void the_invoice_should_include_details_of_all_meals() {
        Order lastOrder = app.getLastOrder();
        assertNotNull(lastOrder, "Order should not be null");
        assertEquals(lastOrder.getMeals().size(), lastInvoice.getOrder().getMeals().size(),
                "Invoice should include all meals from the order");
    }

    /**
     * Initializes a scenario with no orders placed.
     */
    @Given("no orders have been placed by any customer")
    public void no_orders_have_been_placed_by_any_customer() {
        app.resetLists();
    }

    /**
     * Verifies that no invoice is generated when there are no orders.
     */
    @Then("no invoice should be generated")
    public void no_invoice_should_be_generated() {
        Invoice invoice = app.createInvoice(customer, null);
        assertNull(invoice, "No invoice should be generated when there are no orders");
    }
}
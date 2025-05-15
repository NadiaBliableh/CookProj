package FeatureTest;



import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.actors.Customer;
import production_code.core.Ingredients;
import production_code.core.Meal;
import production_code.core.Order;
import production_code.core.Invoice;
import production_code.core.Mainn;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.*;

public class Customer_Invoice_Management {
    private Mainn app;
    private Customer customer;
    private Order order;
    private Invoice lastGeneratedInvoice;
    private UUID originalInvoiceId; // For regenerating lost invoice

    public Customer_Invoice_Management() {
        app = new Mainn();
    }

    @Given("a customer has completed a purchase")
    public void a_customer_has_completed_a_purchase() {

        customer = new Customer("John Doe", "Vegan", new ArrayList<>());
        app.addCustomer(customer);


        List<Ingredients> ingredients = new ArrayList<>();
        ingredients.add(new Ingredients("Tomato", true));
        ingredients.add(new Ingredients("Lettuce", true));
        Meal meal = new Meal(ingredients);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        order = new Order(meals);
        app.addOrder(order);


        app.addPastOrder(customer, "Salad");
    }

    @When("the system processes the purchase")
    public void the_system_processes_the_purchase() {
        // Use MyApplication to create an invoice
        lastGeneratedInvoice = app.createInvoice(customer, order);
    }

    @Then("an invoice is generated with purchase details")
    public void an_invoice_is_generated_with_purchase_details() {
        assertNotNull("Invoice should be generated", lastGeneratedInvoice);
        assertEquals("Invoice should be linked to the correct customer", customer, lastGeneratedInvoice.getCustomer());
        assertEquals("Invoice should contain the correct order", order, lastGeneratedInvoice.getOrder());
        assertFalse("Invoice items should not be empty", lastGeneratedInvoice.getItems().isEmpty());
        assertEquals("Invoice total price should be correct", 10.0, lastGeneratedInvoice.getTotalPrice(), 0.01);
    }

    @Then("the invoice is sent to the customer's email")
    public void the_invoice_is_sent_to_the_customer_s_email() {

        app.markInvoiceAsSent(lastGeneratedInvoice);
        assertTrue("Invoice should be marked as sent", lastGeneratedInvoice.isSent());
    }

    @Given("a customer has purchased multiple items")
    public void a_customer_has_purchased_multiple_items() {
        // Create a customer profile
        customer = new Customer("Jane Doe", "Vegetarian", new ArrayList<>());
        app.addCustomer(customer);

        // Create multiple meals
        List<Ingredients> saladIngredients = new ArrayList<>();
        saladIngredients.add(new Ingredients("Tomato", true));
        saladIngredients.add(new Ingredients("Lettuce", true));
        Meal salad = new Meal(saladIngredients);

        List<Ingredients> soupIngredients = new ArrayList<>();
        soupIngredients.add(new Ingredients("Carrot", true));
        soupIngredients.add(new Ingredients("Onion", true));
        Meal soup = new Meal(soupIngredients);

        // Create an order with multiple meals
        List<Meal> meals = new ArrayList<>();
        meals.add(salad);
        meals.add(soup);
        order = new Order(meals);
        app.addOrder(order);


        app.addPastOrder(customer, "Salad");
        app.addPastOrder(customer, "Soup");
    }

    @Then("an invoice is generated listing all items with their prices")
    public void an_invoice_is_generated_listing_all_items_with_their_prices() {
        assertNotNull("Invoice should be generated", lastGeneratedInvoice);
        assertEquals("Invoice should contain all ordered meals", order.getMeals().size(), lastGeneratedInvoice.getItems().size());
        assertEquals("Invoice total price should reflect multiple items", 20.0, lastGeneratedInvoice.getTotalPrice(), 0.01); // 4 ingredients * $5
        for (Meal meal : lastGeneratedInvoice.getItems()) {
            assertFalse("Each meal in invoice should have ingredients", meal.getIngredients().isEmpty());
        }
    }

    @Given("a customer has lost their original invoice")
    public void a_customer_has_lost_their_original_invoice() {
        a_customer_has_completed_a_purchase();
        the_system_processes_the_purchase();
        originalInvoiceId = lastGeneratedInvoice.getInvoiceId();
        the_invoice_is_sent_to_the_customer_s_email();
    }

    @When("the customer requests a new invoice")
    public void the_customer_requests_a_new_invoice() {

        Invoice originalInvoice = app.getInvoiceById(originalInvoiceId);
        if (originalInvoice != null) {
            lastGeneratedInvoice = app.createInvoice(originalInvoice.getCustomer(), originalInvoice.getOrder());
        } else {
            throw new IllegalStateException("Original invoice not found");
        }
    }

    @Then("the system regenerates the invoice with the same details")
    public void the_system_regenerates_the_invoice_with_the_same_details() {
        assertNotNull("Regenerated invoice should exist", lastGeneratedInvoice);
        assertEquals("Regenerated invoice should have same customer", customer, lastGeneratedInvoice.getCustomer());
        assertEquals("Regenerated invoice should have same number of items", 1, lastGeneratedInvoice.getItems().size());
        assertEquals("Regenerated invoice should have same total price", 10.0, lastGeneratedInvoice.getTotalPrice(), 0.01);
    }
}

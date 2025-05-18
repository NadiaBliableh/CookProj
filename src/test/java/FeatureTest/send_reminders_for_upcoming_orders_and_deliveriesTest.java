package FeatureTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.core.Mainn;
import production_code.core.Meal;
import production_code.core.Order;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class send_reminders_for_upcoming_orders_and_deliveriesTest {
    private String lastNotification;
    private Mainn app;

    public send_reminders_for_upcoming_orders_and_deliveriesTest() {
        app = new Mainn();
    }

    @Given("a customer has an upcoming meal delivery")
    public void a_customer_has_an_upcoming_meal_delivery() {
        Customer customer = new Customer("John Doe", "Vegan", new ArrayList<>());
        app.addCustomer(customer);

        List<Meal> meals = new ArrayList<>();
        meals.add(new Meal(new ArrayList<>()));
        Order order = new Order(customer, meals);
        app.addOrder(order);

        app.createInvoice(customer, order);
    }

    @When("the system sends a delivery reminder")
    public void the_system_sends_a_delivery_reminder() {
        Customer customer = app.getCustomerByName("John Doe");
        Order order = app.getLastOrder();
        if (customer != null && order != null) {
            lastNotification = app.sendDeliveryReminder(customer, order);
        }
    }

    @Then("the customer is notified with a delivery reminder")
    public void the_customer_is_notified_with_a_delivery_reminder() {
        assertNotNull("Notification should be generated", lastNotification);
        assertTrue("Notification should contain customer's name and delivery info", lastNotification.contains("John Doe"));
    }

    @Given("a chef is assigned a cooking task")
    public void a_chef_is_assigned_a_cooking_task() {
        Chef chef = new Chef("Alice", "Vegan");
        app.addChef(chef);

        Task task = new Task("Cook Vegan Meal");
        app.addTask(task);

        TaskScheduler scheduler = new TaskScheduler();
        app.addTaskScheduler(scheduler);
        app.addChef(scheduler, chef);

        app.assignTask(scheduler, task);
    }

    @When("the system notifies the chef of the task")
    public void the_system_notifies_the_chef_of_the_task() {
        Task task = app.getLastTask();
        Chef chef = app.getChefByName(null, "Alice");
        if (task != null && chef != null) {
            lastNotification = app.notifyChefOfTask(null, task, "Alice");
        }
    }

    @Then("the chef is notified with task details")
    public void the_chef_is_notified_with_task_details() {
        assertNotNull("Notification should be generated", lastNotification);
        assertTrue("Notification should contain chef's name and task", lastNotification.contains("Alice") && lastNotification.contains("Cook Vegan Meal"));
    }
}
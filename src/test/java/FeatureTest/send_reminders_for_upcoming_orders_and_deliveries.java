package FeatureTest;



import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;


import production_code.core.Meal;
import production_code.core.Order;
import production_code.actors.Chef;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;
import production_code.core.Mainn;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import production_code.actors.Customer;
import production_code.core.Ingredients;

public class send_reminders_for_upcoming_orders_and_deliveries {
    private final Mainn app;
    private Customer customer;
    private Order order;
    private Chef chef;
    private Task task;
    private TaskScheduler scheduler;
    private String lastNotification;

    public send_reminders_for_upcoming_orders_and_deliveries() {
        app = new Mainn();
    }

    @Given("a customer has an upcoming meal delivery")
    public void a_customer_has_an_upcoming_meal_delivery() {
        customer = new Customer("John Doe", "Vegan", new ArrayList<>());
        app.addCustomer(customer);

        List<Ingredients> ingredients = new ArrayList<>();
        ingredients.add(new Ingredients("Tomato", true));
        ingredients.add(new Ingredients("Lettuce", true));
        Meal meal = new Meal(ingredients);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        order = new Order(meals);
        order.setDeliveryTime("2025-05-10 12:00");
        app.addOrder(order);

        app.addPastOrder(customer, "Salad");
    }

    @Given("a customer has an order without a delivery time")
    public void a_customer_has_an_order_without_a_delivery_time() {
        customer = new Customer("Jane Doe", "Vegetarian", new ArrayList<>());
        app.addCustomer(customer);

        List<Ingredients> ingredients = new ArrayList<>();
        ingredients.add(new Ingredients("Carrot", true));
        ingredients.add(new Ingredients("Onion", true));
        Meal meal = new Meal(ingredients);

        List<Meal> meals = new ArrayList<>();
        meals.add(meal);
        order = new Order(meals);
        app.addOrder(order);

        app.addPastOrder(customer, "Soup");
    }

    @When("the system sends a delivery reminder")
    public void the_system_sends_a_delivery_reminder() {
        lastNotification = app.sendDeliveryReminder(customer, order);
    }

    @Then("the customer receives a reminder with delivery details")
    public void the_customer_receives_a_reminder_with_delivery_details() {
        assertNotNull("Reminder should be sent", lastNotification);
        assertTrue("Reminder should contain customer name",
                lastNotification.contains(customer.getName()));
        assertTrue("Reminder should contain delivery time",
                lastNotification.contains(order.getDeliveryTime()));
        assertEquals("Reminder should have correct format",
                "Reminder: Dear John Doe, your meal delivery is scheduled for 2025-05-10 12:00.",
                lastNotification);
    }

    @Then("the customer receives a message indicating no delivery time is scheduled")
    public void the_customer_receives_a_message_indicating_no_delivery_time_is_scheduled() {
        assertNotNull("Message should be sent", lastNotification);
        assertTrue("Message should contain customer name",
                lastNotification.contains(customer.getName()));
        assertTrue("Message should indicate no delivery time",
                lastNotification.contains("not scheduled"));
        assertEquals("Message should have correct format",
                "Dear Jane Doe, your meal delivery time is not scheduled.",
                lastNotification);
    }

    @Given("a chef is assigned a cooking task")
    public void a_chef_is_assigned_a_cooking_task() {
        chef = new Chef("Alice", "Vegan Cooking", new ArrayList<Object>());
        app.addChef(chef);

        task = new Task("Prepare Vegan Salad", "Vegan Cooking");
        app.addTask(task);

        scheduler = new TaskScheduler(new ArrayList<>());
        app.addTaskScheduler(scheduler);
        app.addChef(scheduler, chef);
    }

    @When("the system sends a task notification")
    public void the_system_sends_a_task_notification() {
        lastNotification = app.notifyChefOfTask(scheduler, task, chef.getName());
        System.out.println("Last notification: " + lastNotification);
    }

    @Then("the chef receives a notification with task details")
    public void the_chef_receives_a_notification_with_task_details() {
        assertNotNull("Notification should be sent", lastNotification);
        assertTrue("Notification should contain chef name",
                lastNotification.contains(chef.getName()));
        assertTrue("Notification should contain task name",
                lastNotification.contains(task.getName()));
        assertEquals("Notification should have correct format",
                "Notification: Alice, you have a new cooking task: Prepare Vegan Salad.",
                lastNotification);
        System.out.println("Chef tasks after assignment: " + chef.getAssignedTasks().size());
        assertTrue("Task should be assigned to chef",
                chef.getAssignedTasks().contains(task));
    }
}

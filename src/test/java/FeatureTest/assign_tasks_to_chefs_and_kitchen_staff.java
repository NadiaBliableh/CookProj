package FeatureTest;



import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import production_code.actors.Chef;
import production_code.core.Mainn;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class assign_tasks_to_chefs_and_kitchen_staff {
    private Mainn app;
    private TaskScheduler scheduler;
    private Task lastAssignedTask;
    private Map<String, Integer> initialTaskCounts = new HashMap<>();
    private String notification;

    public assign_tasks_to_chefs_and_kitchen_staff(Mainn app) {
        this.app = app;
    }

    @Given("the following chefs are available:")
    public void the_following_chefs_are_available(DataTable dataTable) {
        app.resetLists();
        scheduler = new TaskScheduler();
        app.addTaskScheduler(scheduler);
        List<Map<String, String>> chefsData = dataTable.asMaps(String.class, String.class);
        initialTaskCounts.clear();
        for (Map<String, String> row : chefsData) {
            String name = row.get("Name");
            String expertise = row.get("Expertise");
            int currentTasks = Integer.parseInt(row.get("CurrentTasks"));
            Chef chef = new Chef(name, expertise);
            for (int i = 0; i < currentTasks; i++) {
                Task task = new Task("Placeholder" + i, expertise);
                app.addTask(task);
                app.assignTask(chef, task);
            }
            app.addChef(scheduler, chef);
            initialTaskCounts.put(name, chef.getAssignedTasks().size());
            System.out.println("Setup - Chef: " + name + ", Expertise: " + expertise + ", Tasks: " + chef.getAssignedTasks().size());
        }
    }

    @When("a new {string} task is created with required expertise {string}")
    public void a_new_task_is_created_with_required_expertise(String taskName, String requiredExpertise) {
        lastAssignedTask = new Task(taskName, requiredExpertise);
        app.addTask(lastAssignedTask);
        // Debug: Log chef states before assignment
        System.out.println("Assigning task: " + taskName + ", Required Expertise: " + requiredExpertise);
        for (Chef chef : scheduler.getChefs()) {
            System.out.println("Before - Chef: " + chef.getName() + ", Expertise: " + chef.getExpertise() + ", Tasks: " + chef.getAssignedTasks().size());
        }
        app.assignTask(scheduler, lastAssignedTask);
        // Debug: Log assigned chef
        String assignedChef = app.getAssignedChef(scheduler, lastAssignedTask);
        System.out.println("Task assigned to: " + (assignedChef != null ? assignedChef : "None"));
    }

    @Then("the task should be assigned to {string}")
    public void the_task_should_be_assigned_to(String expectedChef) {
        String assignedChef = app.getAssignedChef(scheduler, lastAssignedTask);
        assertTrue("Expected task to be assigned to " + expectedChef + " but got " + assignedChef,
                assignedChef != null && assignedChef.equals(expectedChef));
    }

    @Then("Alice's task count should increase by {int}")
    public void alice_s_task_count_should_increase_by(Integer increment) {
        Chef chef = app.getChefByName(scheduler, "Alice");
        int initial = initialTaskCounts.get("Alice");
        assertEquals("Alice's task count should increase by " + increment, initial + increment, chef.getAssignedTasks().size());
    }

    @Then("Bob's task count should increase by {int}")
    public void bob_s_task_count_should_increase_by(Integer increment) {
        Chef chef = app.getChefByName(scheduler, "Bob");
        int initial = initialTaskCounts.get("Bob");
        assertEquals("Bob's task count should increase by " + increment, initial + increment, chef.getAssignedTasks().size());
    }

    @When("a new {string} task is assigned to {string}")
    public void a_new_task_is_assigned_to(String taskName, String chefName) {
        Task task = new Task(taskName, "Grilling");
        app.addTask(task);
        notification = app.assignTaskToChef(scheduler, task, chefName);
    }

    @Then("Bob should receive a notification: {string}")
    public void bob_should_receive_a_notification(String expectedNotification) {
        assertEquals("Notification should match expected", expectedNotification, notification);
    }
}

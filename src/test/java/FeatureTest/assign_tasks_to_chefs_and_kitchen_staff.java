package FeatureTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import production_code.actors.Chef;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class assign_tasks_to_chefs_and_kitchen_staff {
    private TaskScheduler scheduler;
    private Task lastAssignedTask;
    private Map<String, Integer> initialTaskCounts;

    @Given("the following chefs are available:")
    public void the_following_chefs_are_available(DataTable dataTable) {
        List<Chef> chefs = new ArrayList<>();
        initialTaskCounts = new HashMap<>();
        List<Map<String, String>> chefsData = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> row : chefsData) {
            String name = row.get("Name");
            String expertise = row.get("Expertise");
            int currentTasks = row.get("CurrentTasks") != null ? Integer.parseInt(row.get("CurrentTasks")) : 0;

            assertNotNull(name, "Chef name cannot be null");
            assertNotNull(expertise, "Chef expertise cannot be null");

            Chef chef = new Chef(name, expertise);
            for (int i = 0; i < currentTasks; i++) {
                chef.assignTask(new Task("Task" + i, expertise));
            }
            chefs.add(chef);
            initialTaskCounts.put(name, chef.getAssignedTasks().size());
        }
        scheduler = new TaskScheduler(chefs);
    }

    @When("a new {string} task is created with required expertise {string}")
    public void a_new_task_is_created_with_required_expertise(String taskName, String requiredExpertise) {
        assertNotNull(taskName, "Task name cannot be null");
        assertNotNull(requiredExpertise, "Required expertise cannot be null");

        lastAssignedTask = new Task(taskName, requiredExpertise);
        boolean assigned = scheduler.assignTask(lastAssignedTask);
        assertTrue(assigned, "Task should be assigned to a chef with matching expertise");
    }

    @Then("the task should be assigned to {string}")
    public void the_task_should_be_assigned_to(String expectedChefName) {
        Chef assignedChef = null;
        for (Chef chef : scheduler.getChefs()) {
            if (chef.getAssignedTasks().contains(lastAssignedTask)) {
                assignedChef = chef;
                break;
            }
        }
        assertNotNull(assignedChef, "Task was not assigned to any chef");
        assertEquals(expectedChefName, assignedChef.getName(), "Task should be assigned to " + expectedChefName);
    }

    @Then("Alice's task count should increase by {int}")
    public void alice_s_task_count_should_increase_by(int increment) {
        Chef chef = getChefByName("Alice");
        int initial = initialTaskCounts.getOrDefault("Alice", 0);
        assertEquals(initial + increment, chef.getAssignedTasks().size(),
                "Alice's task count should increase by " + increment);
    }

    @Then("Bob's task count should increase by {int}")
    public void bob_s_task_count_should_increase_by(int increment) {
        Chef chef = getChefByName("Bob");
        int initial = initialTaskCounts.getOrDefault("Bob", 0);
        assertEquals(initial + increment, chef.getAssignedTasks().size(),
                "Bob's task count should increase by " + increment);
    }

    @When("a new {string} task is assigned to {string}")
    public void a_new_task_is_assigned_to(String taskName, String chefName) {
        assertNotNull(taskName, "Task name cannot be null");
        assertNotNull(chefName, "Chef name cannot be null");

        Task task = new Task(taskName, "Grilling"); // Adjust expertise as needed
        boolean assigned = scheduler.assignTaskToChef(task, chefName);
        assertTrue(assigned, "Task should be assigned to " + chefName);
    }

    @Then("Bob should receive a notification: {string}")
    public void bob_should_receive_a_notification(String expectedNotification) {
        Chef bob = getChefByName("Bob");
        assertNotNull(bob, "Bob should exist");
        String notification = "You have been assigned a new task: " + lastAssignedTask.getName(); // Match expected format
        assertEquals(expectedNotification, notification, "Notification should match expected");
    }

    private Chef getChefByName(String name) {
        return scheduler.getChefs().stream()
                .filter(chef -> chef.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
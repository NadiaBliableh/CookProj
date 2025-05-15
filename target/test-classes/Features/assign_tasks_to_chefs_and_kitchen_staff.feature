Feature: Scheduling and Task Management

  As a kitchen manager,
  I want to assign tasks to chefs based on their workload and expertise
  So that I can ensure balanced workloads and efficiency.

  As a chef,
  I want to receive notifications about my assigned cooking tasks
  So that I can prepare meals on time.

  Background:
    Given the following chefs are available:
      | Name    | Expertise      | CurrentTasks |
      | Alice   | Italian        | 2            |
      | Bob     | Grilling       | 1            |
      | Charlie | Baking         | 3            |

  Scenario: Assign task to chef with matching expertise and least workload
    When a new "Italian Pasta" task is created with required expertise "Italian"
    Then the task should be assigned to "Alice"
    And Alice's task count should increase by 1

  Scenario: Assign task to chef with least workload when no expertise match
    When a new "Vegan Salad" task is created with required expertise "Vegan"
    Then the task should be assigned to "Bob"
    And Bob's task count should increase by 1

  Scenario: Chef receives a notification for a new task
    When a new "Grilled Chicken" task is assigned to "Bob"
    Then Bob should receive a notification: "You have been assigned a new task: Grilled Chicken"

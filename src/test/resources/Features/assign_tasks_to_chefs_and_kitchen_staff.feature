Feature: Manage kitchen tasks

  As a kitchen manager
  I want to assign tasks to chefs based on their workload and expertise
  So that I can ensure balanced workloads and efficiency

  As a chef
  I want to receive notifications about my assigned cooking tasks
  So that I can prepare meals on time

  Scenario: Assign a task to a chef
    Given a list of chefs with their current workload and expertise
    When the manager selects a task to assign
    Then the system should assign the task to the most suitable chef

  Scenario: Notify chef about assigned task
    Given a chef has been assigned a new task
    When the assignment is confirmed
    And the chef receives a notification with the task details
    Then the chef will complete the task
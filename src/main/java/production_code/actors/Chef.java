package production_code.actors;

import production_code.kitchen_manager_features.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chef {
    private final String name;
    private final String expertise;
    private final List<Task> assignedTasks;

    public Chef(String name, String expertise) {
        if (name == null || expertise == null) {
            throw new IllegalArgumentException("Name and expertise cannot be null");
        }
        this.name = name;
        this.expertise = expertise;
        this.assignedTasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getExpertise() {
        return expertise;
    }

    public List<Task> getAssignedTasks() {
        return new ArrayList<>(assignedTasks);
    }

    public void assignTask(Task task) {
        if (task != null && !assignedTasks.contains(task)) {
            assignedTasks.add(task);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chef chef = (Chef) o;
        return name.equals(chef.name) && expertise.equals(chef.expertise);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, expertise);
    }

    @Override
    public String toString() {
        return "Chef{name='" + name + "', expertise='" + expertise + "'}";
    }
}
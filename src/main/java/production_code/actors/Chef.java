
package production_code.actors;
import production_code.kitchen_manager_features.Task;

import java.util.ArrayList;
import java.util.List;

public class Chef {
    private String name;
    private String expertise;
    private List<Task> assignedTasks;

    public Chef(String name, String expertise) {
        this.name = name;
        this.expertise = expertise;
        this.assignedTasks = new ArrayList<>(); // Initialize the list
    }

    public Chef(String name, String expertise, ArrayList<Object> initialTaskCount) {
        this.name = name;
        this.expertise = expertise;
        this.assignedTasks = new ArrayList<>();
    }
    /////
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks != null ? new ArrayList<>(assignedTasks) : new ArrayList<>();
    }
}
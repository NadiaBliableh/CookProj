package production_code.kitchen_manager_features;

import production_code.actors.Chef;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskScheduler {
    private final List<Chef> chefs;

    public TaskScheduler() {
        this.chefs = new ArrayList<>();
    }

    // مُنشئ جديد يقبل قائمة من الطهاة
    public TaskScheduler(List<Chef> chefs) {
        this.chefs = new ArrayList<>();
        if (chefs != null) {
            for (Chef chef : chefs) {
                addChef(chef);
            }
        }
    }

    public void addChef(Chef chef) {
        if (chef != null && !chefs.contains(chef)) {
            chefs.add(chef);
        }
    }

    public List<Chef> getChefs() {
        return new ArrayList<>(chefs);
    }

    /**
     * Assigns a task to a suitable chef based on required expertise.
     *
     * @param task The task to assign, must not be null.
     * @return true if the task was assigned successfully, false otherwise.
     */
    public boolean assignTask(Task task) {
        if (task == null) {
            return false;
        }

        String requiredExpertise = task.getRequiredExpertise();
        if (requiredExpertise == null) {
            return false;
        }

        Chef suitableChef = chefs.stream()
                .filter(chef -> chef.getExpertise() != null && chef.getExpertise().equalsIgnoreCase(requiredExpertise))
                .min((chef1, chef2) -> Integer.compare(chef1.getAssignedTasks().size(), chef2.getAssignedTasks().size()))
                .orElse(null);

        if (suitableChef != null) {
            suitableChef.assignTask(task);
            return true;
        }
        return false;
    }

    /**
     * Assigns a task to a specific chef by name.
     *
     * @param task     The task to assign, must not be null.
     * @param chefName The name of the chef to assign the task to.
     * @return true if the task was assigned successfully, false otherwise.
     */
    public boolean assignTaskToChef(Task task, String chefName) {
        if (task == null || chefName == null) {
            return false;
        }

        Chef chef = chefs.stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(chefName))
                .findFirst()
                .orElse(null);

        if (chef == null) {
            return false;
        }

        // تحقق من تطابق الخبرة
        String requiredExpertise = task.getRequiredExpertise();
        if (requiredExpertise != null && !chef.getExpertise().equalsIgnoreCase(requiredExpertise)) {
            return false; // الخبرة لا تتطابق
        }

        chef.assignTask(task);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskScheduler that = (TaskScheduler) o;
        return chefs.equals(that.chefs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chefs);
    }

    @Override
    public String toString() {
        return "TaskScheduler{chefs=" + chefs.size() + "}";
    }
}
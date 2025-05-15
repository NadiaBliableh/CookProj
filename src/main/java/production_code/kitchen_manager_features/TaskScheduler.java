package production_code.kitchen_manager_features;


import java.util.ArrayList;
import java.util.List;

public class TaskScheduler {
    private List<production_code.actors.Chef> chefs;

    public TaskScheduler() {
        this.chefs = new ArrayList<>();
    }

    public TaskScheduler(List<production_code.actors.Chef> chefs) {
        this.chefs = chefs != null ? new ArrayList<>(chefs) : new ArrayList<>();
    }

    public List<production_code.actors.Chef> getChefs() {
        return new ArrayList<>(chefs);
    }

    public void setChefs(List<production_code.actors.Chef> chefs) {
        this.chefs = chefs != null ? new ArrayList<>(chefs) : new ArrayList<>();
    }

    public void addChef(production_code.actors.Chef chef) {
        if (chef != null) {
            this.chefs.add(chef);
        }
    }
}

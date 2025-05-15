package production_code.core;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import java.util.ArrayList;
import java.util.List;

public class Meal {
    private List<Ingredients> ingredients;

    public Meal(List<Ingredients> ingredients) {
        this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>();
    }

    public List<Ingredients> getIngredients() { return ingredients; }
    public void setIngredients(List<Ingredients> ingredients) { this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>(); }
}


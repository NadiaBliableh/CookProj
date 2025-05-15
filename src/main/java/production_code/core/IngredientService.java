package production_code.core;



import java.util.ArrayList;
import java.util.List;

public class IngredientService {
    private List<Ingredients> availableIngredients;

    public IngredientService() {
        this.availableIngredients = new ArrayList<>();
    }

    public List<Ingredients> getAvailableIngredients() {
        return availableIngredients;
    }

    public void addIngredient(Ingredients ingredient) {
        availableIngredients.add(ingredient);
    }
}

package production_code.core;

import java.util.ArrayList;
import java.util.List;

public class IngredientService {
    private List<Ingredients> availableIngredients;

    public IngredientService() {
        this.availableIngredients = new ArrayList<>();
    }

    public List<Ingredients> getAvailableIngredients() {
        return new ArrayList<>(availableIngredients);
    }

    public void addIngredient(Ingredients ingredient) {
        if (ingredient != null) availableIngredients.add(ingredient);
    }

    public boolean isIngredientAvailable(String name) {
        return availableIngredients.stream().anyMatch(i -> i.getName().equalsIgnoreCase(name) && i.isAvailable());
    }
}
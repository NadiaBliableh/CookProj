package production_code.core;

import java.util.List;

public class Recipe {
    private final String name;
    private final List<String> ingredients;
    private final int preparationTime;
    private final String dietaryRestriction;

    public Recipe(String name, List<String> ingredients, int preparationTime, String dietaryRestriction) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparationTime = preparationTime;
        this.dietaryRestriction = dietaryRestriction;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public String getDietaryRestriction() {
        return dietaryRestriction;
    }
}
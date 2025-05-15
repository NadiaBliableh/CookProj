package production_code.core;



import java.util.List;

public class Recipe {
    private String name;
    private List<String> ingredients;
    private int preparationTime; // in minutes
    private String dietaryRestriction;

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

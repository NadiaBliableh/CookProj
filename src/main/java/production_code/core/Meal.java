package production_code.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Meal {
    private final List<Ingredients> ingredients;

    public Meal(List<Ingredients> ingredients) {
        if (ingredients == null) {
            throw new IllegalArgumentException("Ingredients cannot be null");
        }
        this.ingredients = new ArrayList<>(ingredients);
    }

    public List<Ingredients> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return ingredients.equals(meal.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients);
    }

    @Override
    public String toString() {
        return "Meal{ingredients=" + ingredients + "}";
    }
}
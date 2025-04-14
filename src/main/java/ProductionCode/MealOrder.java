package ProductionCode;
import java.util.ArrayList;
import java.util.List;
public class MealOrder {
    private String mealName;
    private String orderDate;
    private List<String> ingredients;
    private boolean isConfirmed;

    public MealOrder(List<String> ingredients) {
        this.ingredients = new ArrayList<>(ingredients);
        this.isConfirmed = false;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void modifyMeal(String addIngredient, String removeIngredient) {
        if (isConfirmed) {
            throw new IllegalStateException("Meal order cannot be modified after confirmation.");
        }
        ingredients.remove(removeIngredient);
        ingredients.add(addIngredient);
    }

    public void confirmOrder() {
        isConfirmed = true;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
    public MealOrder(String mealName, String orderDate) {
        this.mealName = mealName;
        this.orderDate = orderDate;
    }

    public String getMealName() {
        return mealName;
    }

    public String getOrderDate() {
        return orderDate;
    }
}

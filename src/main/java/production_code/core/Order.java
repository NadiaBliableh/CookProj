package production_code.core;



import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<Meal> meals;
    private String deliveryTime;

    public Order(List<Meal> meals) {
        this.meals = new ArrayList<>(meals);
        this.deliveryTime = null;
    }

    public List<Meal> getMeals() {
        return new ArrayList<>(meals);
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}

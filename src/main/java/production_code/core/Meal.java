package production_code.core;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String mealName;
    private Specialty mealFamilyType;
    private LocalDateTime orderDate;
    private List<String> ingredients;
    private double price;
    private int deliveryTime;
    
    public Meal(
            List<String> ingredients, String mealName,
            Specialty mealFamilyType, int deliveryTime
    ){
        this.ingredients = new ArrayList<>(ingredients);
        this.mealName = mealName;
        this.orderDate = LocalDateTime.now();
        this.mealFamilyType = mealFamilyType;
        this.deliveryTime = deliveryTime;
    }
    
    public String getMealName() {
        return mealName;
    }
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
    
    public Specialty getMealFamilyType() {
        return mealFamilyType;
    }
    public void setMealFamilyType(Specialty mealFamilyType) {
        this.mealFamilyType = mealFamilyType;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getDeliveryTime() {
        return deliveryTime;
    }
    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
    
    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    
    @Override
    public String toString() {
        return mealName;
    }
}

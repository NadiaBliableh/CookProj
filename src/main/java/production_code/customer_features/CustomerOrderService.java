package production_code.customer_features;

import production_code.actors.Customer;
import production_code.core.IngredientService;
import production_code.core.Order;

import java.util.ArrayList;

public class CustomerOrderService {
    private IngredientService ingredientService;
    private String dietaryPreference;
    private Order order;

    public CustomerOrderService(Customer customerProfile, IngredientService ingredientService) {
        this.ingredientService = ingredientService;
        this.dietaryPreference = customerProfile.getDietaryPreference();
        this.order = new Order(new ArrayList<>());
    }

    public IngredientService getIngredientService() { return ingredientService; }
    public void setIngredientService(IngredientService ingredientService) { this.ingredientService = ingredientService; }
    public String getDietaryPreference() { return dietaryPreference; }
    public void setDietaryPreference(String dietaryPreference) { this.dietaryPreference = dietaryPreference; }
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }
}
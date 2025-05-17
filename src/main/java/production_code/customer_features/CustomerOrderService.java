package production_code.customer_features;

import production_code.actors.Customer;
import production_code.core.IngredientService;
import production_code.core.Order;
import production_code.core.Meal;

import java.util.ArrayList;
import java.util.List;

public class CustomerOrderService {
    private IngredientService ingredientService;
    private String dietaryPreference;
    private Order order;
    private final Customer customer;


    public CustomerOrderService(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
    }


    public CustomerOrderService(Customer customerProfile, IngredientService ingredientService) {
        if (customerProfile == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (ingredientService == null) {
            throw new IllegalArgumentException("IngredientService cannot be null");
        }
        this.customer = customerProfile;
        this.ingredientService = ingredientService;
        this.dietaryPreference = customerProfile.getDietaryPreference();
        this.order = new Order(customerProfile, new ArrayList<>());
    }

    public Customer getCustomer() {
        return customer;
    }

    public IngredientService getIngredientService() {
        return ingredientService;
    }

    public void setIngredientService(IngredientService ingredientService) {
        if (ingredientService == null) {
            throw new IllegalArgumentException("IngredientService cannot be null");
        }
        this.ingredientService = ingredientService;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(String dietaryPreference) {
        this.dietaryPreference = dietaryPreference;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        this.order = order;
    }
}
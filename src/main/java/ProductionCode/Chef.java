package ProductionCode;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class Chef {
    public Chef() {

    }

    public void viewCustomerProfile(CustomerProfile profile) {
        if (profile != null) {
            System.out.println("Customer: " + profile.getCustomerName());
            System.out.println("Dietary Preferences: " + profile.getDietaryPreferences());
            System.out.println("Allergies: " + profile.getAllergies());
        } else {
            System.out.println("No customer profile found.");
        }
    }

    private boolean loggedIn;
    private String username;
    public Chef(String username) {
        this.username = username;
        this.loggedIn = true; // Simulate login
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }


    public List<Meal> viewCustomerOrders(String customerUsername) {
        return new ArrayList<>(Arrays.asList(new Meal("Pizza"), new Meal("Pasta")));
    }

    public MealPlan suggestMealPlan(List<Meal> customerOrders) {
        return new MealPlan("Custom Healthy Meal Plan");
    }
}
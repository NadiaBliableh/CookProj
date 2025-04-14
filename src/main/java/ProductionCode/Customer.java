package ProductionCode;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

import java.util.List;

import java.util.Arrays;
import java.util.ArrayList;
public class Customer {
    private String username;
    private boolean loggedIn;

    public Customer(String username) {
        this.username = username;
        this.loggedIn = true; // Simulate login
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }


    public List<Meal> viewOrderHistory() {
        return new ArrayList<>(Arrays.asList(new Meal("Pizza"), new Meal("Pasta")));
    }


    public boolean canReorder(Meal meal) {
        return true; // Assume reordering is always possible
    }
    private String dietaryPreferences;
    private String allergies;

    // Constructor
    public Customer(String dietaryPreferences, String allergies) {
        this.dietaryPreferences = dietaryPreferences;
        this.allergies = allergies;
    }

    // Getters and Setters
    public String getDietaryPreferences() {
        return dietaryPreferences;
    }

    public void setDietaryPreferences(String dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }
}

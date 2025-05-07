package production_code.actors;
import production_code.core.Meal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Customer implements Users {
    private String username;
    private String password;
    private Map<LocalDateTime, Meal> orderHistory;
    private Map<Meal, Boolean> orderStatus;
    private List<String> dietaryPreferences;
    private List<String> allergies;
    private boolean loggedIn;
    
    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
        this.loggedIn = true;
        this.orderHistory = new HashMap<>();
        this.orderStatus = new HashMap<>();
        this.dietaryPreferences = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }
    
    public Customer() {}
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    public Map<LocalDateTime, Meal> viewOrderHistory() {
        return orderHistory;
    }
    public void addOrderToHistory(LocalDateTime timeNow, Meal meal) {
        if (orderHistory == null) {
            orderHistory = new HashMap<>();
        }
        orderHistory.put(timeNow, meal);
    }
    
    public Map<Meal, Boolean> getOrderStatusMap() {
        return orderStatus;
    }
    public void setOrderStatusMap(Map<Meal, Boolean> orderStatus) {
        this.orderStatus = orderStatus;
    }
    public void updateOrderStatus(Meal meal, boolean status) {
        orderStatus.put(meal, status);
    }
    public boolean isOrderConfirmed(Meal meal) {
        return orderStatus.getOrDefault(meal, false);
    }
    public void cancelOrder(Meal meal) {
        orderStatus.remove(meal);
    }
    
    public List<String> getDietaryPreferences() {
        return dietaryPreferences;
    }
    public void setDietaryPreferences(List<String> dietaryPreferences) {
        this.dietaryPreferences = dietaryPreferences;
    }
    public void addDietaryPreference(String dietaryPreference) {
        if (dietaryPreferences == null) {
            dietaryPreferences = new ArrayList<>();
        }
        dietaryPreferences.add(dietaryPreference);
    }
    
    public List<String> getAllergies() {
        return allergies;
    }
    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
    public void addAllergy(String allergy) {
        if (allergies == null) {
            allergies = new ArrayList<>();
        }
        allergies.add(allergy);
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void logout() {
        this.loggedIn = false;
    }
    public void login() {
        this.loggedIn = true;
    }
}
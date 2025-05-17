package production_code.actors;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Customer {
    private final String name;
    private String dietaryPreference;
    private List<String> allergies;  // تعديل من String إلى List<String>
    private final List<String> pastOrders;
    private List<String> suggestedMeals;

    public Customer(String name, String dietaryPreference, List<String> pastOrders) {
        if (name == null || dietaryPreference == null || pastOrders == null) {
            throw new IllegalArgumentException("Name, dietary preference, and past orders cannot be null");
        }
        this.name = name;
        this.dietaryPreference = dietaryPreference;
        this.pastOrders = new ArrayList<>(pastOrders);
        this.allergies = new ArrayList<>();  // تهيئة القائمة
        this.suggestedMeals = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDietaryPreference() {
        return dietaryPreference;
    }

    public void setDietaryPreference(String dietaryPreference) {
        if (dietaryPreference != null) {
            this.dietaryPreference = dietaryPreference;
        }
    }

    public List<String> getAllergies() {
        return new ArrayList<>(allergies);
    }

    public void setAllergies(List<String> allergies) {
        if (allergies != null) {
            this.allergies = new ArrayList<>(allergies);
        }
    }

    public List<String> getPastOrders() {
        return new ArrayList<>(pastOrders);
    }

    public void addPastOrder(String order) {
        if (order != null && !pastOrders.contains(order)) {
            pastOrders.add(order);
        }
    }

    public List<String> getSuggestedMeals() {
        return new ArrayList<>(suggestedMeals);
    }

    public void setSuggestedMeals(List<String> suggestedMeals) {
        if (suggestedMeals != null) {
            this.suggestedMeals = new ArrayList<>(suggestedMeals);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name) && dietaryPreference.equals(customer.dietaryPreference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dietaryPreference);
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + "', dietaryPreference='" + dietaryPreference + "', allergies=" + allergies + "}";
    }
}
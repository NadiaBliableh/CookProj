package ProductionCode;

import java.util.List;

public class CustomerProfile {
    private String customerName;
    private List<String> dietaryPreferences;
    private List<String> allergies;

    public CustomerProfile(String customerName, List<String> dietaryPreferences, List<String> allergies) {
        this.customerName = customerName;
        this.dietaryPreferences = dietaryPreferences;
        this.allergies = allergies;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getDietaryPreferences() {
        return dietaryPreferences;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void updatePreferences(List<String> newPreferences) {
        this.dietaryPreferences = newPreferences;
    }

    public void updateAllergies(List<String> newAllergies) {
        this.allergies = newAllergies;
    }
}

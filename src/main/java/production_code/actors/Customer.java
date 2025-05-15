package production_code.actors;
import java.util.ArrayList;
import java.util.List;





public class Customer {
    private String name;
    private String dietaryPreference;
    private String allergy;
    private List<String> suggestedMeals;
    private List<String> pastOrders;

    public Customer(String name, String dietaryPreference, List<String> allergies) {
        this.name = name;
        this.dietaryPreference = dietaryPreference;
        this.allergy = allergies.isEmpty() ? "" : allergies.get(0);
        this.suggestedMeals = new ArrayList<>();
        this.pastOrders = new ArrayList<>();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDietaryPreference() { return dietaryPreference; }
    public void setDietaryPreference(String dietaryPreference) { this.dietaryPreference = dietaryPreference; }
    public String getAllergy() { return allergy; }
    public void setAllergy(String allergy) { this.allergy = allergy; }
    public List<String> getSuggestedMeals() { return suggestedMeals; }
    public void setSuggestedMeals(List<String> suggestedMeals) { this.suggestedMeals = suggestedMeals; }
    public List<String> getPastOrders() { return pastOrders; }
    public void setPastOrders(List<String> pastOrders) { this.pastOrders = pastOrders; }
}
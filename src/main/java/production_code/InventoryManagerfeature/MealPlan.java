package production_code.InventoryManagerfeature;
import java.util.List;

public class MealPlan {
	private final String name;
	private final String description;
	private final List<String> meals;
	private final String targetAudience;
	
	public MealPlan(String name, String description, List<String> meals, String targetAudience) {
		this.name = name;
		this.description = description;
		this.meals = meals;
		this.targetAudience = targetAudience;
	}
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public List<String> getMeals() {
		return meals;
	}
	public String getTargetAudience() {
		return targetAudience;
	}
	
	void displayPlanDetails() {
		System.out.println("Meal Plan: " + name);
		System.out.println("Description: " + description);
		System.out.println("Meals: ");
		meals.forEach(meal -> System.out.println("- " + meal));
		System.out.println("Target Audience: " + targetAudience);
		System.out.println("--------------------------");
	}
}
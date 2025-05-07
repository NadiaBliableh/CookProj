package production_code.customer_features;
import static production_code.core.Ingredients.getIngredients;

import java.util.List;
import java.util.Map;

public class SelectIngredients {
	public static void selectIngredients() {
		System.out.println("Ingredient Families and Their Options:");
		for (Map.Entry<String, List<String>> entry : getIngredients().entrySet()) {
			String ingredient = entry.getKey();
			List<String> alternatives = entry.getValue();
			
			System.out.println(ingredient + " options: " + String.join(", ", alternatives));
		}
		
		System.out.println("Select ingredients by typing their names separated by commas.");
		System.out.println("Example: Milk, Oil, Cheese");
		System.out.println("Press Enter to continue...");
	}
}
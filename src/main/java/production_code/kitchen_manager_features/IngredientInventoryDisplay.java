package production_code.kitchen_manager_features;
import static production_code.core.Ingredients.getAvailableQuantityOfIngredients;
import static production_code.core.Ingredients.getIngredientPrice;

import java.util.Map;

public class IngredientInventoryDisplay {
	public static boolean displayAvailableIngredients() {
		Map<String, Integer> availableIngredients = getAvailableQuantityOfIngredients();
		System.out.printf("%-20s | %-10s | %-20s%n", "Ingredient", "Quantity", "Note");
		System.out.println("--------------------------------------------------------------");
		
		for (Map.Entry<String, Integer> entry : availableIngredients.entrySet()) {
			String ingredient = entry.getKey();
			int quantity = entry.getValue();
			String note = "";
			
			if (quantity == 0) {
				note = "⚠ Out of Stock";
			} else if (quantity < 5) {
				note = "⚠ Low Stock";
			}
			System.out.printf("%-20s | %-10d | %-20s%n", ingredient, quantity, note);
		}
		return true;
	}
	
	public static boolean displayIngredientPrices() {
		Map<String, Integer> ingredientPrices = getIngredientPrice();
		System.out.printf("%-20s | %-10s%n", "Ingredient", "Price (USD)");
		System.out.println("---------------------------------------------");
		
		for (Map.Entry<String, Integer> entry : ingredientPrices.entrySet()) {
			String ingredient = entry.getKey();
			int price = entry.getValue();
			System.out.printf("%-20s | %-10s%n", ingredient, price + " $");
		}
		return true;
	}
}

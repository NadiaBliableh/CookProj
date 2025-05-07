package production_code.core;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static production_code.files_manager.DataSaver.*;
import static production_code.files_manager.FilePaths.*;

public class Ingredients {
	private static final Map<String, List<String>> INGREDIENTS = new HashMap<>();
	private static final Map<String, Integer> AVAILABLE_QUANTITY_OF_INGREDIENTS = new HashMap<>();
	private static final Map<String, Integer> INGREDIENT_PRICE = new HashMap<>();
	
	public static void initializeIngredients() {
		INGREDIENTS.put("Milk", List.of("Almond Milk", "Soy Milk", "Oat Milk", "Coconut Milk", "Cashew Milk"));
		INGREDIENTS.put("Eggs", List.of("Chia Seeds", "Flaxseeds", "Applesauce", "Mashed Banana", "Yogurt"));
		INGREDIENTS.put("Flour", List.of("Almond Flour", "Coconut Flour", "Oat Flour", "Chickpea Flour", "Rice Flour"));
		INGREDIENTS.put("Butter", List.of("Coconut Oil", "Olive Oil", "Avocado", "Ghee", "Vegan Butter"));
		INGREDIENTS.put("Sugar", List.of("Honey", "Maple Syrup", "Stevia", "Agave Nectar", "Coconut Sugar"));
		INGREDIENTS.put("Salt", List.of("Seaweed", "Celery Powder", "Low-Sodium Salt Alternatives"));
		INGREDIENTS.put("Oil", List.of("Avocado Oil", "Coconut Oil", "Olive Oil", "Grapeseed Oil"));
		INGREDIENTS.put("Cheese", List.of("Nutritional Yeast", "Vegan Cheese", "Cashew Cheese", "Tofu"));
		INGREDIENTS.put("Cream", List.of("Coconut Cream", "Cashew Cream", "Soy Cream"));
		INGREDIENTS.put("Bread", List.of("Lettuce Wraps", "Rice Cakes", "Gluten-Free Bread"));
		INGREDIENTS.put("Pasta", List.of("Zucchini Noodles", "Spaghetti Squash", "Chickpea Pasta"));
		INGREDIENTS.put("Rice", List.of("Cauliflower Rice", "Quinoa", "Barley"));
		INGREDIENTS.put("Beef", List.of("Mushrooms", "Lentils", "Jackfruit"));
		INGREDIENTS.put("Chicken", List.of("Tofu", "Tempeh", "Seitan"));
	}
	
	public static void initializeAvailableQuantityOfIngredients() {
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Milk", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Eggs", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Flour", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Butter", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Sugar", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Salt", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Oil", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Cheese", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Cream", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Bread", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Pasta", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Rice", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Beef", 10);
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put("Chicken", 10);
	}
	
	public static void initializeIngredientPrice() {
		INGREDIENT_PRICE.put("Milk", 15);
		INGREDIENT_PRICE.put("Eggs", 10);
		INGREDIENT_PRICE.put("Flour", 8);
		INGREDIENT_PRICE.put("Butter", 20);
		INGREDIENT_PRICE.put("Sugar", 7);
		INGREDIENT_PRICE.put("Salt", 5);
		INGREDIENT_PRICE.put("Oil", 18);
		INGREDIENT_PRICE.put("Cheese", 25);
		INGREDIENT_PRICE.put("Cream", 22);
		INGREDIENT_PRICE.put("Bread", 12);
		INGREDIENT_PRICE.put("Pasta", 14);
		INGREDIENT_PRICE.put("Rice", 13);
		INGREDIENT_PRICE.put("Beef", 40);
		INGREDIENT_PRICE.put("Chicken", 30);
	}
	
	public Ingredients(String ingredient, List<String> alternatives, int quantity, int price) {
		addNewIngredient(ingredient, alternatives);
		addNewIngredientQuantity(ingredient, quantity);
		addNewIngredientPrice(ingredient, price);
	}
	
	public static Map<String, List<String>> getIngredients() {
		return INGREDIENTS;
	}
	public static void addNewIngredient(String ingredient, List<String> alternatives) {
		INGREDIENTS.put(ingredient, alternatives);
		saveIngredientsToFile(INGREDIENTS, INGREDIENTS_FILE_NAME);
	}
	
	public static Map<String, Integer> getAvailableQuantityOfIngredients() {
		return AVAILABLE_QUANTITY_OF_INGREDIENTS;
	}
	public static void addNewIngredientQuantity(String ingredient, int quantity) {
		AVAILABLE_QUANTITY_OF_INGREDIENTS.put(ingredient, quantity);
		saveAvailableQuantityOfIngredientsToFile(AVAILABLE_QUANTITY_OF_INGREDIENTS, AVAILABLE_QUANTITY_OF_INGREDIENTS_FILE_NAME);
	}
	public static void decreaseIngredient(String ingredient, int decreasedQuantity) {
		try {
			int availableQuantity = AVAILABLE_QUANTITY_OF_INGREDIENTS.get(ingredient);
			AVAILABLE_QUANTITY_OF_INGREDIENTS.compute(ingredient, (k, quantity) -> availableQuantity - decreasedQuantity);
			if(AVAILABLE_QUANTITY_OF_INGREDIENTS.get(ingredient) < 0) {
				AVAILABLE_QUANTITY_OF_INGREDIENTS.put(ingredient, 0);
			}
			saveAvailableQuantityOfIngredientsToFile(AVAILABLE_QUANTITY_OF_INGREDIENTS, AVAILABLE_QUANTITY_OF_INGREDIENTS_FILE_NAME);
		} catch (Exception e) {
			System.out.println("Ingredient " + ingredient + " is not available.");
		}
	}
	
	public static Map<String, Integer> getIngredientPrice() {
		return INGREDIENT_PRICE;
	}
	public static void addNewIngredientPrice(String ingredient, int price) {
		INGREDIENT_PRICE.put(ingredient, price);
		saveIngredientPriceToFile(INGREDIENT_PRICE, INGREDIENT_PRICE_FILE_NAME);
	}
}
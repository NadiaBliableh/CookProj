package production_code.core;
import production_code.InventoryManagerfeature.MealPlan;

import java.util.List;

public class Plans {

	public static final MealPlan VEGAN = new MealPlan(
			"Vegan",
			"Plant-based meals with no animal products.",
			List.of("Vegan Burger", "Tofu Salad", "Vegan Smoothie", "Grilled Vegetables"),
			"For customers who follow a plant-based diet without any animal-derived products."
	);
	
	public static final MealPlan KETO = new MealPlan(
			"Keto",
			"Low-carb, high-fat meals to put the body in ketosis.",
			List.of("Keto Salad", "Grilled Chicken", "Cheese Omelette", "Avocado Smoothie"),
			"For customers following a ketogenic diet to promote fat burning."
	);
	
	public static final MealPlan GLUTEN_FREE = new MealPlan(
			"Gluten-Free",
			"Meals without gluten, perfect for people with gluten intolerance.",
			List.of("Gluten-Free Pancakes", "Grilled Salmon", "Quinoa Salad", "Rice Cakes"),
			"For customers who are sensitive to gluten or have gluten allergies."
	);
	
	public static final MealPlan GENERAL = new MealPlan(
			"General",
			"Balanced meals for a healthy and diverse diet.",
			List.of("Chicken Salad", "Vegetable Soup", "Steak with Potatoes", "Fruit Salad"),
			"For customers who want a balanced and healthy meal plan."
	);
	
	public static final MealPlan PALEO = new MealPlan(
			"Paleo",
			"Meals based on foods presumed to have been eaten by early humans.",
			List.of("Grilled Beef", "Sweet Potato Mash", "Mixed Berries", "Baked Fish"),
			"For customers who prefer natural, unprocessed foods."
	);
	
	public static final MealPlan MEDITERRANEAN = new MealPlan(
			"Mediterranean",
			"Meals inspired by the Mediterranean diet, rich in fruits, vegetables, and healthy fats.",
			List.of("Greek Salad", "Grilled Chicken with Hummus", "Seafood Paella", "Olive Tapenade"),
			"For customers seeking heart-healthy and flavorful meals."
	);
	
	public static final MealPlan HIGH_PROTEIN = new MealPlan(
			"High-Protein",
			"Meals packed with protein to support muscle building and recovery.",
			List.of("Protein Smoothie", "Grilled Turkey Breast", "Egg Muffins", "Beef Stir Fry"),
			"For customers focused on fitness and muscle gain."
	);
	
	public static final MealPlan LOW_SODIUM = new MealPlan(
			"Low-Sodium",
			"Meals with reduced salt content for better heart health.",
			List.of("Herb Grilled Chicken", "Steamed Vegetables", "Brown Rice", "Fresh Fruit Bowl"),
			"For customers needing to manage blood pressure or heart conditions."
	);
	
	public static final MealPlan DIABETIC_FRIENDLY = new MealPlan(
			"Diabetic-Friendly",
			"Meals designed to maintain stable blood sugar levels.",
			List.of("Grilled Fish", "Quinoa Salad", "Avocado Toast", "Berry Parfait"),
			"For customers managing diabetes or blood sugar concerns."
	);
	
	public static final MealPlan WEIGHT_LOSS = new MealPlan(
			"Weight Loss",
			"Portion-controlled meals focused on reducing calorie intake.",
			List.of("Zucchini Noodles", "Grilled Shrimp", "Cauliflower Rice", "Green Smoothie"),
			"For customers aiming to lose weight through controlled diet plans."
	);
}
package production_code.chif_features;
import production_code.actors.Customer;
import production_code.core.Meal;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuggestPersonalizedMealPlans {
	public static boolean viewCustomerOrderHistory(List<Customer> customers) {
		System.out.println("+-----+----------------+-------------------------+------------+------------+");
		System.out.println("| #   | Username        | Meal Name                | Status     | Order Date |");
		System.out.println("+-----+----------------+-------------------------+------------+------------+");
		
		for (Customer customer : customers) {
			int count = 1;
			if (customer == null) {
				continue;
			} if (customer.viewOrderHistory().isEmpty()) {
				continue;
			} if (customer.getOrderStatusMap().isEmpty()) {
				continue;
			}
			
			for (LocalDateTime dateTime : customer.viewOrderHistory().keySet()) {
				Meal order = customer.viewOrderHistory().get(dateTime);
				if (dateTime.isAfter(LocalDateTime.now()) && dateTime.isBefore(LocalDateTime.now().plusMinutes(10))) {
					System.out.printf("| %-3d | %-14s | %-23s | %-10s | %-10s |\n",
							count,
							customer.getUsername(),
							order.getMealName(),
							customer.getOrderStatusMap().get(order) ? "Confirmed" : "Pending",
							order.getOrderDate()
					);
					count++;
				}
			}
			System.out.println("+-----+----------------+-------------------------+------------+------------+");
		}
		return true;
	}
	
	public static String suggestMealPlan(Customer customer) {
		Map<String, Integer> mealFrequency = new HashMap<>();
		
		for (LocalDateTime dateTime : customer.viewOrderHistory().keySet()) {
			Meal order = customer.viewOrderHistory().get(dateTime);
			String mealName = order.getMealName();
			mealFrequency.put(mealName, mealFrequency.getOrDefault(mealName, 0) + 1);
		}
		return compatibleMealPlan(mealFrequency);
	}
	
	public static String compatibleMealPlan(Map<String, Integer> mealFrequency) {
		if (mealFrequency.containsKey("Vegan Burger") ||
				mealFrequency.containsKey("Tofu Salad") ||
				mealFrequency.containsKey("Vegan Smoothie") ||
				mealFrequency.containsKey("Grilled Vegetables")) {
			return "Vegan Meal Plan";
		} else if (mealFrequency.containsKey("Keto Salad") ||
				mealFrequency.containsKey("Grilled Chicken") ||
				mealFrequency.containsKey("Cheese Omelette") ||
				mealFrequency.containsKey("Avocado Smoothie")) {
			return "Keto Meal Plan";
		} else if (mealFrequency.containsKey("Gluten-Free Pancakes") ||
				mealFrequency.containsKey("Grilled Salmon") ||
				mealFrequency.containsKey("Rice Cakes")) {
			return "Gluten-Free Meal Plan";
		} else if (mealFrequency.containsKey("Chicken Salad") ||
				mealFrequency.containsKey("Vegetable Soup") ||
				mealFrequency.containsKey("Steak with Potatoes") ||
				mealFrequency.containsKey("Fruit Salad")) {
			return "General Meal Plan";
		} else if (mealFrequency.containsKey("Grilled Beef") ||
				mealFrequency.containsKey("Sweet Potato Mash") ||
				mealFrequency.containsKey("Mixed Berries") ||
				mealFrequency.containsKey("Baked Fish")) {
			return "Paleo Meal Plan";
		} else if (mealFrequency.containsKey("Greek Salad") ||
				mealFrequency.containsKey("Grilled Chicken with Hummus") ||
				mealFrequency.containsKey("Seafood Paella") ||
				mealFrequency.containsKey("Olive Tapenade")) {
			return "Mediterranean Meal Plan";
		} else if (mealFrequency.containsKey("Protein Smoothie") ||
				mealFrequency.containsKey("Grilled Turkey Breast") ||
				mealFrequency.containsKey("Egg Muffins") ||
				mealFrequency.containsKey("Beef Stir Fry")) {
			return "High-Protein Meal Plan";
		} else if (mealFrequency.containsKey("Herb Grilled Chicken") ||
				mealFrequency.containsKey("Steamed Vegetables") ||
				mealFrequency.containsKey("Brown Rice") ||
				mealFrequency.containsKey("Fresh Fruit Bowl")) {
			return "Low-Sodium Meal Plan";
		} else if (mealFrequency.containsKey("Grilled Fish") ||
				mealFrequency.containsKey("Quinoa Salad") ||
				mealFrequency.containsKey("Avocado Toast") ||
				mealFrequency.containsKey("Berry Parfait")) {
			return "Diabetic-Friendly Meal Plan";
		} else if (mealFrequency.containsKey("Zucchini Noodles") ||
				mealFrequency.containsKey("Grilled Shrimp") ||
				mealFrequency.containsKey("Cauliflower Rice") ||
				mealFrequency.containsKey("Green Smoothie")) {
			return "Weight Loss Meal Plan";
		} else {
			return "General Meal Plan";
		}
	}
}
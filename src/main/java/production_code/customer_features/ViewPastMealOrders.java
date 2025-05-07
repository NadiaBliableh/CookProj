package production_code.customer_features;

import production_code.actors.Customer;
import production_code.core.Meal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ViewPastMealOrders {
	public static boolean printOrderHistory(Customer customer) {
		if (customer.viewOrderHistory().isEmpty()) {
			System.out.println("No orders found in history.");
			return true;
		}
		
		System.out.println("===========================================================================================");
		System.out.printf("| %-20s | %-20s | %-40s |\n", "Order Date", "Meal Name", "Ingredients");
		System.out.println("===========================================================================================");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		for (Map.Entry<LocalDateTime, Meal> entry : customer.viewOrderHistory().entrySet()) {
			String date = entry.getKey().format(formatter);
			String mealName = entry.getValue().getMealName();
			String mealIngredients = String.join(", ", entry.getValue().getIngredients());
			System.out.printf("| %-20s | %-20s | %-40s |\n", date, mealName, mealIngredients);
		}
		
		System.out.println("===========================================================================================");
		return true;
	}
}
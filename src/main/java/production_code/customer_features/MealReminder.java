package production_code.customer_features;
import production_code.actors.Customer;
import production_code.core.Meal;

import java.time.LocalDateTime;
import java.util.Map;

public class MealReminder {
	public static boolean remindCustomer(Customer customer, Map<LocalDateTime, Meal> orderHistory) {
		for(LocalDateTime entry : orderHistory.keySet()){
			Meal meal = orderHistory.get(entry);
			if(entry.isBefore(LocalDateTime.now().plusMinutes(meal.getDeliveryTime() - 5))){
				System.out.println("Reminder: Your '" + meal.getMealName() + "' will arrive soon !");
			}
		}
		return true;
	}
}
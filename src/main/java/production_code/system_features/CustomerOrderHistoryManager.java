package production_code.system_features;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.saveCustomersOrdersToFile;
import static production_code.files_manager.DataSaver.saveMealRepetitionCountToFile;
import static production_code.files_manager.FilePaths.MEAL_REPETITION_COUNT_FILE_NAME;
import static production_code.files_manager.FilePaths.ORDER_STATUS_FILE_NAME;

import production_code.actors.Customer;
import production_code.core.Meal;

import java.util.List;
import java.util.Map;

public class CustomerOrderHistoryManager {
	public static void saveOrderHistory() {
		saveCustomersOrdersToFile(getCustomersOrders(), ORDER_STATUS_FILE_NAME);
	}
	
	public static void printOrderHistory() {
		Map<Customer, List<Meal>> customersOrders = getCustomersOrders();
		if (customersOrders.isEmpty()) {
			System.out.println("No customer orders found.");
			return;
		}
		
		for (Customer customer : customersOrders.keySet()) {
			List<Meal> meals = customersOrders.get(customer);
			
			System.out.println("\nOrder History for Customer: " + customer.getUsername());
			System.out.println("----------------------------------------------------");
			System.out.printf("%-5s | %-20s | %-20s%n", "No.", "Meal Name", "Order Date");
			System.out.println("----------------------------------------------------");
			
			int counter = 1;
			for (Meal meal : meals) {
				System.out.printf("%-5d | %-20s | %-20s%n", counter++, meal.getMealName(), meal.getOrderDate());
			}
			
			System.out.println("----------------------------------------------------\n");
		}
	}
	
	public static void calculateTop5MealsOrdered(
			Map<Meal, Integer> mealRepetitionCount,
			Map<Customer, List<Meal>> customersOrders
	){
		for (List<Meal> meals : customersOrders.values()) {
			for (Meal meal : meals) {
				if (meal != null) {
					mealRepetitionCount.put(meal, mealRepetitionCount.getOrDefault(meal, 0) + 1);
				}
			}
		}


		if(mealRepetitionCount == null || mealRepetitionCount.isEmpty()){
			System.out.println("No orders found in history.");
			return;
		}
		
		mealRepetitionCount.entrySet().stream()
				.filter(entry -> entry.getKey() != null)
				.sorted(Map.Entry.<Meal, Integer>comparingByValue().reversed())
				.limit(5)
				.forEach(entry -> System.out.println(
						entry.getKey().toString() + ": " + entry.getValue() + " orders"
				));
		saveMealRepetitionCountToFile(mealRepetitionCount, MEAL_REPETITION_COUNT_FILE_NAME);
	}
	
	public static boolean printTop5MealsOrdered(Map<Meal, Integer> mealRepetitionCount) {
		System.out.println("\nTop 5 Ordered Meals:");
		System.out.println("-------------------------------------");
		System.out.printf("%-5s | %-20s | %-10s%n", "No.", "Meal Name", "Orders");
		System.out.println("-------------------------------------");
		
		int counter = 1;
		for (Map.Entry<Meal, Integer> entry : mealRepetitionCount.entrySet()) {
			System.out.printf("%-5d | %-20s | %-10d%n", counter++, entry.getKey().getMealName(), entry.getValue());
		}
		System.out.println("-------------------------------------");
		return true;
	}
}
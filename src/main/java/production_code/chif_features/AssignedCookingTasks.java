package production_code.chif_features;
import static production_code.core.Ingredients.decreaseIngredient;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.*;
import static production_code.files_manager.FilePaths.*;

import production_code.actors.Customer;
import production_code.core.Meal;
import production_code.core.Specialty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssignedCookingTasks {
	public static boolean isConfirmed = false;
	
	public static boolean displayAssignedCookingTasks(
			Specialty specialty, Map<Specialty, Map<Meal, List<Customer>>> specialtyMealMap
	){
		Map<Meal, List<Customer>> meals = specialtyMealMap.get(specialty);
		if (meals == null || meals.isEmpty()) {
			System.out.println("No meals assigned for this specialty: " + specialty);
			return false;
		}
		
		System.out.println("➤ Specialty: " + specialty);
		System.out.println("----------------------------");
		int counter = 1;
		for (Meal meal : meals.keySet()) {
			System.out.println(counter++ + ". " + meal.getMealName() + " (" + meal.getIngredients() + ")");
		}
		System.out.println();
		return true;
	}
	
	public static boolean viewOrderDeliveryDates(
			Specialty specialty, Map<Specialty, Map<Meal, List<Customer>>> specialtyMealMap
	) {
		Map<Meal, List<Customer>> mealCustomersMap = specialtyMealMap.get(specialty);
		if (mealCustomersMap == null || mealCustomersMap.isEmpty()) {
			System.out.println("No meals found for specialty: " + specialty);
			AssignedCookingTasks.isConfirmed = false;
			return false;
		}
		
		System.out.println("=== Viewing Order Delivery Dates for Specialty: " + specialty + " ===\n");
		for (Meal meal : mealCustomersMap.keySet()) {
			List<Customer> customers = mealCustomersMap.get(meal);
			System.out.println("Meal: " + meal.getMealName() + " | Ingredients: " + meal.getIngredients());
			System.out.println("Ordered by:");
			boolean hasOrders = false;
			
			for (Customer customer : customers) {
				Boolean hasOrdered = customer.getOrderStatusMap().get(meal);
				
				if (hasOrdered != null && hasOrdered) {
					Set<LocalDateTime> orderTimes = customer.viewOrderHistory().keySet();
					for (LocalDateTime orderTime : orderTimes) {
						Meal orderedMeal = customer.viewOrderHistory().get(orderTime);
						if (orderedMeal.equals(meal)) {
							System.out.println(
									" - Customer: " + customer.getUsername() +
											" | Delivery Time: " + orderedMeal.getDeliveryTime() + " minutes" +
											" | Ordered At: " + orderTime
							);
							hasOrders = true;
						}
					}
				}
			}
			
			if (!hasOrders) {
				System.out.println(" - No customers have ordered this meal yet.");
			}
			System.out.println("--------------------------------------------\n");
		}
		
		AssignedCookingTasks.isConfirmed = true;
		return true;
	}
	
	public static boolean finishCookingTask(Meal meal, Map<Meal, Map<Specialty, List<Customer>>> chefSpeciality) {
		Map<Specialty, List<Customer>> orders = chefSpeciality.get(meal);
		if (orders == null || orders.isEmpty()) {
			System.out.println("No orders found for meal: " + meal);
			return false;
		}
		Set<Specialty> specialties = orders.keySet();
		Specialty specialty = specialties.iterator().next();
		
		List<Customer> customers = orders.get(specialty);
		if (customers == null || customers.isEmpty()) {
			System.out.println("No customers found for specialty: " + specialty);
			return false;
		}
		
		List<String> ingredients = meal.getIngredients();
		for (String ingredient : ingredients) {
			decreaseIngredient(ingredient, customers.size());
		}
		
		addOrderStatusMap(meal, true);
		removeChefSpeciality(meal);
		removeSpecialtyMeals(specialty);
		saveOrderStatusMapToFile(getOrderStatusMap(), ORDER_STATUS_FILE_NAME);
		saveChefSpecialityToFile(getChefSpeciality(), CHEF_SPECIALITY_FILE_NAME);
		saveSpecialtyMealsToFile(getSpecialtyMeals(), SPECIALTY_MEALS_FILE_NAME);
		return true;
	}
}
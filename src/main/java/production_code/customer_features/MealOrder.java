package production_code.customer_features;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.saveChefSpecialityToFile;
import static production_code.files_manager.DataSaver.saveCustomersOrdersToFile;
import static production_code.files_manager.FilePaths.*;

import production_code.actors.Customer;
import production_code.core.Meal;
import production_code.core.Specialty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealOrder {
	private final String customerName;
	private final Meal meal;
	private final List<String> ingredients = new ArrayList<>();
	
	public MealOrder(String customerName, Meal meal) {
		this.customerName = customerName;
		this.meal = meal;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public Meal getMeal() {
		return meal;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	
	public boolean confirmOrder(
			MealOrder order, Map<String, Meal> mealMap, Customer customer,
			Map<Meal, Map<Specialty, List<Customer>>> chefSpeciality
	){
		if(order.getCustomerName() == null || order.getMeal() == null){
			return false;
		}
		customer.addOrderToHistory(LocalDateTime.now(), mealMap.get(order.getMeal().getMealName()));
		customer.updateOrderStatus(mealMap.get(order.getMeal().getMealName()), true);
		addCustomersOrder(customer, new ArrayList<>(customer.getOrderStatusMap().keySet()));
		
		Meal meal = mealMap.get(order.getMeal().getMealName());
		if (meal == null) {
			System.err.println("Meal not found in DataManager for name: " + order.getMeal().getMealName());
			return false;
		}
		
		Specialty specialty = meal.getMealFamilyType();
		if(specialty != null){
			Map<Specialty, List<Customer>> orders = chefSpeciality.get(mealMap.get(order.getMeal().getMealName()));
			List<Customer> customers;
			if(orders == null){
				customers = new ArrayList<>();
				customers.add(customer);
				orders = new HashMap<>();
				orders.put(specialty, customers);
			} else {
				customers = orders.get(specialty);
				if(customers == null){
					customers = new ArrayList<>();
				}
				customers.add(customer);
				orders.put(specialty, customers);
			}
			chefSpeciality.put(mealMap.get(order.getMeal().getMealName()), orders);
		} else if(mealMap.get(order.getMeal().getMealName()).getMealFamilyType() != null){
			System.out.println("Specialty not found for meal '" + order.getMeal() + "'");
		}
		
		printMessage(order, mealMap);
		saveChefSpecialityToFile(chefSpeciality, CHEF_FILE_NAME);
		saveCustomersOrdersToFile(getCustomersOrders(), CUSTOMERS_ORDERS_FILE_NAME);
		return true;
	}
	
	private void printMessage(MealOrder order, Map<String, Meal> mealMap){
		System.out.println("Order confirmed for '" + order.getMeal() + "' by '" + order.getCustomerName() + "'");
		System.out.println("Your order will arrive in " + mealMap.get(order.getMeal().getMealName()).getDeliveryTime() + " minutes !");
	}
}
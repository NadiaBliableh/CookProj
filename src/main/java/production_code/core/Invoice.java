package production_code.core;
import production_code.actors.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Invoice {
	private double totalPrice;
	private List<String> orders;
	public static boolean isInvoiceCreated = false;
	
	public Invoice(Customer customer) {
		if(customer == null){
			isInvoiceCreated = false;
			return;
		}
		orders = new ArrayList<>();
		totalPrice = 0.0;
		
		Map<Meal, Boolean> confirmedOrders = customer.getOrderStatusMap();
		for(Meal meal : confirmedOrders.keySet()){
			if(confirmedOrders.get(meal) == true){
				totalPrice += meal.getPrice();
				orders.add(meal.getMealName());
			}
		}
		isInvoiceCreated = true;
	}
	
	public double getTotalPrice() {
		return totalPrice;
	}
	public List<String> getOrders() {
		return orders;
	}
}
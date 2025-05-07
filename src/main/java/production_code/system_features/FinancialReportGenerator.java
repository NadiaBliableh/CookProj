package production_code.system_features;
import static production_code.files_manager.DataManager.getCustomersOrders;
import production_code.actors.Customer;
import production_code.core.Meal;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class FinancialReportGenerator {
	public static boolean calculateAndPrintRevenue() {
		Map<Customer, List<Meal>> customersOrders = getCustomersOrders();
		RevenuePeriods periods = calculatePeriods();
		Map<String, Double> revenueMap = calculateRevenue(customersOrders, periods);
		return printRevenue(revenueMap);
	}
	
	private static RevenuePeriods calculatePeriods() {
		LocalDate today = LocalDate.now();
		
		LocalDate thisWeekStart = today;
		while (thisWeekStart.getDayOfWeek() != DayOfWeek.SATURDAY) {
			thisWeekStart = thisWeekStart.minusDays(1);
		}
		LocalDate lastWeekStart = thisWeekStart.minusWeeks(1);
		LocalDate lastWeekEnd = thisWeekStart.minusDays(1);
		
		LocalDate thisMonthStart = today.withDayOfMonth(1);
		LocalDate lastMonthStart = thisMonthStart.minusMonths(1);
		LocalDate lastMonthEnd = thisMonthStart.minusDays(1);
		
		return new RevenuePeriods(today, thisWeekStart, lastWeekStart, lastWeekEnd, thisMonthStart, lastMonthStart, lastMonthEnd);
	}
	
	private static Map<String, Double> calculateRevenue(Map<Customer, List<Meal>> customersOrders, RevenuePeriods periods) {
		double lastWeekTotal = 0;
		double thisWeekTotal = 0;
		double lastMonthTotal = 0;
		double thisMonthTotal = 0;
		
		for (Map.Entry<Customer, List<Meal>> entry : customersOrders.entrySet()) {
			Customer customer = entry.getKey();
			Set<LocalDateTime> orderTimes = customer.viewOrderHistory().keySet();
			for (LocalDateTime orderDateTime : orderTimes) {
				Meal meal = customer.viewOrderHistory().get(orderDateTime);
				LocalDate orderDate = orderDateTime.toLocalDate();
				
				if (!orderDate.isBefore(periods.thisWeekStart) && !orderDate.isAfter(periods.today)) {
					thisWeekTotal += meal.getPrice();
				} else if (!orderDate.isBefore(periods.lastWeekStart) && !orderDate.isAfter(periods.lastWeekEnd)) {
					lastWeekTotal += meal.getPrice();
				}
				
				if (!orderDate.isBefore(periods.thisMonthStart) && !orderDate.isAfter(periods.today)) {
					thisMonthTotal += meal.getPrice();
				} else if (!orderDate.isBefore(periods.lastMonthStart) && !orderDate.isAfter(periods.lastMonthEnd)) {
					lastMonthTotal += meal.getPrice();
				}
			}
		}
		
		Map<String, Double> revenueMap = new LinkedHashMap<>();
		revenueMap.put("Last Month", lastMonthTotal);
		revenueMap.put("This Month", thisMonthTotal);
		revenueMap.put("Last Week", lastWeekTotal);
		revenueMap.put("This Week", thisWeekTotal);
		return revenueMap;
	}
	
	private static boolean printRevenue(Map<String, Double> revenueMap) {
		System.out.println("-----------------------------------");
		for (Map.Entry<String, Double> entry : revenueMap.entrySet()) {
			System.out.printf("%-15s : %.2f%n", entry.getKey(), entry.getValue());
		}
		System.out.println("-----------------------------------");
		return true;
	}
	
	private static class RevenuePeriods {
		LocalDate today;
		LocalDate thisWeekStart;
		LocalDate lastWeekStart;
		LocalDate lastWeekEnd;
		LocalDate thisMonthStart;
		LocalDate lastMonthStart;
		LocalDate lastMonthEnd;
		
		RevenuePeriods(
				LocalDate today, LocalDate thisWeekStart, LocalDate lastWeekStart, LocalDate lastWeekEnd,
				LocalDate thisMonthStart, LocalDate lastMonthStart, LocalDate lastMonthEnd
		){
			this.today = today;
			this.thisWeekStart = thisWeekStart;
			this.lastWeekStart = lastWeekStart;
			this.lastWeekEnd = lastWeekEnd;
			this.thisMonthStart = thisMonthStart;
			this.lastMonthStart = lastMonthStart;
			this.lastMonthEnd = lastMonthEnd;
		}
	}
}
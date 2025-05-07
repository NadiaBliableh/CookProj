package production_code.files_manager;
import production_code.actors.*;
import production_code.core.Meal;
import production_code.core.Specialty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
	private static final Map<String, Admin> ADMIN_MAP = new HashMap<>();
	private static final Map<String, KitchenManager> KITCHEN_MANAGER_MAP = new HashMap<>();
	private static final Map<String, Chef> CHEF_MAP = new HashMap<>();
	private static final Map<String, Customer> CUSTOMER_MAP = new HashMap<>();
	private static final Map<Customer, List<Meal>> CUSTOMERS_ORDERS = new HashMap<>();
	private static final Map<String, Meal> MEAL_MAP = new HashMap<>();
	private static final Map<Meal, Map<Specialty, List<Customer>>> CHEF_SPECIALITY = new HashMap<>();
	private static final Map<Specialty, Map<Meal, List<Customer>>> SPECIALTY_MEALS = new HashMap<>();
	private static final Map<Meal, Boolean> ORDER_STATUS_MAP = new HashMap<>();
	private static final Map<Meal, Integer> MEAL_REPETITION_COUNT = new HashMap<>();
	
	public static Map<String, Admin> getAdminMap() {
		return ADMIN_MAP;
	}
	public static void addAdmin(String adminName, Admin admin) {
		ADMIN_MAP.put(adminName, admin);
	}
	public static void replaceAdmin(String adminName, Admin admin) {
		ADMIN_MAP.replace(adminName, admin);
	}
	
	public static Map<String, KitchenManager> getKitchenManagerMap() {
		return KITCHEN_MANAGER_MAP;
	}
	public static void addKitchenManager(String kitchenManagerName, KitchenManager kitchenManager) {
		KITCHEN_MANAGER_MAP.put(kitchenManagerName, kitchenManager);
	}
	public static boolean thereIsKitchenManager(String kitchenManagerName) {
		return KITCHEN_MANAGER_MAP.containsKey(kitchenManagerName);
	}
	public static void replaceKitchenManager(String kitchenManagerName, KitchenManager kitchenManager) {
		KITCHEN_MANAGER_MAP.replace(kitchenManagerName, kitchenManager);
	}
	
	public static Map<String, Chef> getChefMap() {
		return CHEF_MAP;
	}
	public static void addChef(String chefName, Chef chef) {
		CHEF_MAP.put(chefName, chef);
	}
	public static boolean thereIsChef(String chefName) {
		return CHEF_MAP.containsKey(chefName);
	}
	public static void replaceChef(String chefName, Chef chef) {
		CHEF_MAP.replace(chefName, chef);
	}
	
	public static Map<String, Customer> getCustomerMap() {
		return CUSTOMER_MAP;
	}
	public static Customer getCustomer(String username) {
		return CUSTOMER_MAP.get(username);
	}
	public static void replaceCustomer(String customerName, Customer customer) {
		CUSTOMER_MAP.replace(customerName, customer);
	}
	public static void addCustomer(String customerName, Customer customer) {
		CUSTOMER_MAP.put(customerName, customer);
	}
	public static boolean thereIsCustomer(String customerName) {
		return CUSTOMER_MAP.containsKey(customerName);
	}
	public static void removeCustomer(String customerName) {
		CUSTOMER_MAP.remove(customerName);
	}
	
	public static Map<Customer, List<Meal>> getCustomersOrders() {
		return CUSTOMERS_ORDERS;
	}
	public static void addCustomersOrder(Customer customer, List<Meal> meals) {
		CUSTOMERS_ORDERS.put(customer, meals);
	}
	public static boolean thereIsCustomersOrder(Customer customer) {
		return CUSTOMERS_ORDERS.containsKey(customer);
	}
	public static void removeCustomersOrder(Customer customer) {
		CUSTOMERS_ORDERS.remove(customer);
	}
	
	public static Map<String, Meal> getMealMap() {
		return MEAL_MAP;
	}
	public static Meal getMeal(String mealName) {
		return MEAL_MAP.get(mealName);
	}
	public static void addMeal(String mealName, Meal meal) {
		MEAL_MAP.put(mealName, meal);
	}
	public static boolean thereIsMeal(String mealName) {
		return MEAL_MAP.containsKey(mealName);
	}
	public static void removeMeal(String mealName) {
		MEAL_MAP.remove(mealName);
	}
	
	public static Map<Meal, Map<Specialty, List<Customer>>> getChefSpeciality() {
		return CHEF_SPECIALITY;
	}
	public static void addChefSpeciality(Meal meal, Map<Specialty, List<Customer>> orders) {
		CHEF_SPECIALITY.put(meal, orders);
	}
	public static boolean thereIsChefSpeciality(Meal meal) {
		return CHEF_SPECIALITY.containsKey(meal);
	}
	public static void removeChefSpeciality(Meal meal) {
		CHEF_SPECIALITY.remove(meal);
	}
	
	public static Map<Specialty, Map<Meal, List<Customer>>> getSpecialtyMeals() {
		return SPECIALTY_MEALS;
	}
	public static void addSpecialtyMeals(Specialty specialty, Map<Meal, List<Customer>> orders) {
		SPECIALTY_MEALS.put(specialty, orders);
	}
	public static boolean thereIsSpecialtyMeals(Specialty specialty) {
		return SPECIALTY_MEALS.containsKey(specialty);
	}
	public static void removeSpecialtyMeals(Specialty specialty) {
		SPECIALTY_MEALS.remove(specialty);
	}
	
	public static Map<Meal, Boolean> getOrderStatusMap() {
		return ORDER_STATUS_MAP;
	}
	public static void addOrderStatusMap(Meal meal, Boolean status) {
		ORDER_STATUS_MAP.put(meal, status);
	}
	public static boolean thereIsOrderStatusMap(Meal meal) {
		return ORDER_STATUS_MAP.containsKey(meal);
	}
	
	public static Map<Meal, Integer> getMealRepetitionCount() {
		return MEAL_REPETITION_COUNT;
	}
}
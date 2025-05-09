package production_code.files_manager;

import production_code.actors.*;
import production_code.core.Meal;
import production_code.core.Specialty;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataLoader {
	
	public static void loadAdminMapFromFile(Map<String, Admin> adminMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("Admin file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Admin> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, Admin>>() {});
			adminMap.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading Admin map: " + e.getMessage());
		}
	}
	
	public static void loadKitchenManagerMapFromFile(Map<String, KitchenManager> kitchenManagerMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("KitchenManager file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, KitchenManager> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, KitchenManager>>() {});
			kitchenManagerMap.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading KitchenManager map: " + e.getMessage());
		}
	}
	
	public static void loadChefMapFromFile(Map<String, Chef> chefMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("Chef file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Chef> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, Chef>>() {});
			chefMap.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading Chef map: " + e.getMessage());
		}
	}


	public static void loadCustomerMapFromFile(Map<String, Customer> customerMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("Customer file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Customer> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, Customer>>() {});
			customerMap.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading Customer map: " + e.getMessage());
		}
	}
	
	public static void loadCustomersOrdersFromFile(Map<Customer, List<Meal>> customersOrders, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("CustomersOrders file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Customer, List<Meal>> loadedMap = objectMapper.readValue(file, new TypeReference<Map<Customer, List<Meal>>>() {});
			customersOrders.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading CustomersOrders map: " + e.getMessage());
		}
	}
	
	public static void loadMealMapFromFile(Map<String, Meal> mealMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("Meal file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Meal> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, Meal>>() {});
			mealMap.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading Meal map: " + e.getMessage());
		}
	}
	
	public static void loadChefSpecialityFromFile(Map<Meal, Map<Specialty, List<Customer>>> chefSpeciality, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("ChefSpeciality file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Meal, Map<Specialty, List<Customer>>> loadedMap = objectMapper.readValue(file, new TypeReference<Map<Meal, Map<Specialty, List<Customer>>>>() {});
			chefSpeciality.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading ChefSpeciality map: " + e.getMessage());
		}
	}
	
	public static void loadSpecialtyMealsFromFile(Map<Specialty, Map<Meal, List<Customer>>> specialtyMeals, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("SpecialtyMeals file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Specialty, Map<Meal, List<Customer>>> loadedMap = objectMapper.readValue(file, new TypeReference<Map<Specialty, Map<Meal, List<Customer>>>>() {});
			specialtyMeals.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading SpecialtyMeals map: " + e.getMessage());
		}
	}
	
	public static void loadOrderStatusMapFromFile(Map<Meal, Boolean> orderStatusMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("OrderStatus file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Meal, Boolean> loadedMap = objectMapper.readValue(file, new TypeReference<Map<Meal, Boolean>>() {});
			orderStatusMap.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading OrderStatus map: " + e.getMessage());
		}
	}
	
	public static void loadMealRepetitionCountFromFile(Map<Meal, Integer> mealRepetitionCount, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("MealRepetitionCount file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<Meal, Integer> loadedMap = objectMapper.readValue(file, new TypeReference<Map<Meal, Integer>>() {});
			mealRepetitionCount.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading MealRepetitionCount map: " + e.getMessage());
		}
	}
	
	public static void loadIngredientsFromFile(Map<String, List<String>> ingredients, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("Ingredients file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, List<String>> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, List<String>>>() {});
			ingredients.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading Ingredients map: " + e.getMessage());
		}
	}
	
	public static void loadAvailableQuantityOfIngredientsFromFile(Map<String, Integer> availableQuantity, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("AvailableQuantity file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Integer> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, Integer>>() {});
			availableQuantity.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading AvailableQuantity map: " + e.getMessage());
		}
	}
	
	public static void loadIngredientPriceFromFile(Map<String, Integer> ingredientPrice, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("IngredientPrice file did not exist. Created new one.");
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Integer> loadedMap = objectMapper.readValue(file, new TypeReference<Map<String, Integer>>() {});
			ingredientPrice.putAll(loadedMap);
		} catch (IOException e) {
			System.err.println("Error loading IngredientPrice map: " + e.getMessage());
		}
	}
}
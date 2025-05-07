package production_code.files_manager;

import production_code.actors.*;
import production_code.core.Meal;
import production_code.core.Specialty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DataSaver {
	public static boolean saveAdminMapToFile(Map<String, Admin> adminMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, adminMap);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Admin map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveKitchenManagerMapToFile(Map<String, KitchenManager> kitchenManagerMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, kitchenManagerMap);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving KitchenManager map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveChefMapToFile(Map<String, Chef> chefMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, chefMap);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Chef map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveCustomerMapToFile(Map<String, Customer> customerMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, customerMap);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Customer map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveCustomersOrdersToFile(Map<Customer, List<Meal>> customersOrders, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, customersOrders);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Customers Orders map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveMealMapToFile(Map<String, Meal> mealMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, mealMap);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Meal map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveChefSpecialityToFile(Map<Meal, Map<Specialty, List<Customer>>> chefSpeciality, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, chefSpeciality);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Chef Speciality map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveSpecialtyMealsToFile(Map<Specialty, Map<Meal, List<Customer>>> specialtyMeals, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, specialtyMeals);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Specialty Meals map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveOrderStatusMapToFile(Map<Meal, Boolean> orderStatusMap, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, orderStatusMap);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Order Status map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveMealRepetitionCountToFile(Map<Meal, Integer> mealRepetitionCount, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, mealRepetitionCount);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Meal Repetition Count map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveIngredientsToFile(Map<String, List<String>> ingredients, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, ingredients);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Ingredients map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveAvailableQuantityOfIngredientsToFile(Map<String, Integer> availableQuantity, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, availableQuantity);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Available Quantity map: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean saveIngredientPriceToFile(Map<String, Integer> ingredientPrice, String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (file.createNewFile()) {
					System.out.println("The file did not exist. A new file was created: " + filePath);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.writeValue(file, ingredientPrice);
			return true;
		} catch (IOException e) {
			System.err.println("Error saving Ingredient Price map: " + e.getMessage());
			return false;
		}
	}
}
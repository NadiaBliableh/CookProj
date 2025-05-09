package production_code.core;
import production_code.actors.*;
import production_code.files_manager.DataLoader;

import java.util.Map;

import static production_code.core.Ingredients.*;
import static production_code.core.Ingredients.getIngredients;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.*;
import static production_code.files_manager.FilePaths.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        systemIsRunning();
    }
    
    public static boolean systemIsRunning(){

        DataLoader.loadAdminMapFromFile(getAdminMap(), ADMIN_FILE_NAME);
        DataLoader.loadKitchenManagerMapFromFile(getKitchenManagerMap(), KITCHEN_MANAGER_FILE_NAME);
        DataLoader.loadChefMapFromFile(getChefMap(), CHEF_FILE_NAME);
        DataLoader.loadCustomerMapFromFile(getCustomerMap(), CUSTOMER_FILE_NAME);
        DataLoader.loadCustomersOrdersFromFile(getCustomersOrders(), CUSTOMERS_ORDERS_FILE_NAME);
        DataLoader.loadMealMapFromFile(getMealMap(), MEAL_FILE_NAME);
        DataLoader.loadChefSpecialityFromFile(getChefSpeciality(), CHEF_SPECIALITY_FILE_NAME);
        DataLoader.loadSpecialtyMealsFromFile(getSpecialtyMeals(), SPECIALTY_MEALS_FILE_NAME);
        DataLoader.loadOrderStatusMapFromFile(getOrderStatusMap(), ORDER_STATUS_FILE_NAME);
        DataLoader.loadMealRepetitionCountFromFile(getMealRepetitionCount(), MEAL_REPETITION_COUNT_FILE_NAME);
        DataLoader.loadIngredientsFromFile(getIngredients(), INGREDIENTS_FILE_NAME);
        DataLoader.loadAvailableQuantityOfIngredientsFromFile(getAvailableQuantityOfIngredients(), AVAILABLE_QUANTITY_OF_INGREDIENTS_FILE_NAME);
        DataLoader.loadIngredientPriceFromFile(getIngredientPrice(), INGREDIENT_PRICE_FILE_NAME);
        
        if(getIngredients().isEmpty()){
            Ingredients.initializeIngredients();
            saveIngredientsToFile(getIngredients(), INGREDIENTS_FILE_NAME);
        } if(getAvailableQuantityOfIngredients().isEmpty()){
            Ingredients.initializeAvailableQuantityOfIngredients();
            saveAvailableQuantityOfIngredientsToFile(getAvailableQuantityOfIngredients(), AVAILABLE_QUANTITY_OF_INGREDIENTS_FILE_NAME);
        } if(getIngredientPrice().isEmpty()){
            Ingredients.initializeIngredientPrice();
            saveIngredientPriceToFile(getIngredientPrice(), INGREDIENT_PRICE_FILE_NAME);
        }
        return true;
    }
    
    public static <T extends Users> boolean Login(String username, String password, Map<String, T> mapOfUsers) {
        if (mapOfUsers.containsKey(username)) {
            Users user = mapOfUsers.get(username);
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }
    
    public static void register(String username, String password, String accountType) {
        if(getAdminMap().containsKey(username) ||
            getKitchenManagerMap().containsKey(username) ||
            getChefMap().containsKey(username) ||
            getCustomerMap().containsKey(username)
        ){
            System.out.println("Username already taken!");
        } else {
            switch (accountType) {
                case "ADMIN" -> addAdmin(username, new Admin(username, password));
                case "KITCHEN_MANAGER" -> addKitchenManager(username, new KitchenManager(username, password));
                case "CHEF" -> addChef(username, new Chef(username, password));
                case "CUSTOMER" -> addCustomer(username, new Customer(username, password));
                default -> System.out.println("Account type not found!");
            }
        }
    }
    
    public static void logout() {
        System.out.println("Logging out...");
        saveAdminMapToFile(getAdminMap(), ADMIN_FILE_NAME);
        saveKitchenManagerMapToFile(getKitchenManagerMap(), KITCHEN_MANAGER_FILE_NAME);
        saveChefMapToFile(getChefMap(), CHEF_FILE_NAME);
        saveCustomerMapToFile(getCustomerMap(), CUSTOMER_FILE_NAME);
        saveCustomersOrdersToFile(getCustomersOrders(), CUSTOMERS_ORDERS_FILE_NAME);
        saveMealMapToFile(getMealMap(), MEAL_FILE_NAME);
    }
}

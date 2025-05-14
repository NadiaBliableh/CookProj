package production_code.core;
import production_code.actors.*;
import production_code.console_mediator.AdminMenu;
import production_code.console_mediator.ChefMenu;
import production_code.console_mediator.CustomerMenu;
import production_code.console_mediator.MahagerMenu;
import production_code.files_manager.DataLoader;
import java.util.*;

import java.time.Clock;
import java.util.Map;

import static production_code.core.Ingredients.*;
import static production_code.core.Ingredients.getIngredients;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.*;
import static production_code.files_manager.FilePaths.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        systemIsRunning();
        System.out.println("_____________WELCOME TO OUR SYSTEM____________");
        System.out.println("Main Menu:");
        System.out.println("1. Admin");
        System.out.println("2. Chef ");
        System.out.println("3. Customer");
        System.out.println("4. Kitchen Manager ");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice){
            case 1:
                AdminMenu adminMenu = new AdminMenu();
                adminMenu.showAdminMenu();
                break;
            case 2:
                ChefMenu chefMenu = new ChefMenu();
                chefMenu.showChefMenu();
                break;
            case 3:
                CustomerMenu customerMenu = new CustomerMenu();
                customerMenu.showCustomerMenu();
                break;
            case 4:
                MahagerMenu managerMenu = new MahagerMenu();
                managerMenu.showManagerMenu();
                break;
        }
    }

    public static boolean systemIsRunning() {

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

        if (getIngredients().isEmpty()) {
            Ingredients.initializeIngredients();
            saveIngredientsToFile(getIngredients(), INGREDIENTS_FILE_NAME);
        }
        if (getAvailableQuantityOfIngredients().isEmpty()) {
            Ingredients.initializeAvailableQuantityOfIngredients();
            saveAvailableQuantityOfIngredientsToFile(getAvailableQuantityOfIngredients(), AVAILABLE_QUANTITY_OF_INGREDIENTS_FILE_NAME);
        }
        if (getIngredientPrice().isEmpty()) {
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
        if (getAdminMap().containsKey(username) ||
                getKitchenManagerMap().containsKey(username) ||
                getChefMap().containsKey(username) ||
                getCustomerMap().containsKey(username)
        ) {
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





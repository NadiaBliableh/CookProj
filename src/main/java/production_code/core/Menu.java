package production_code.core;
import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.actors.InventoryManager;
import production_code.actors.User;
import production_code.customer_features.CustomerOrderService;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;
import production_code.system_features.FinancialReportGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Menu {
    private Mainn app;
    private Scanner scanner;
    private User currentUser;

    public Menu() {
        app = new Mainn();
        scanner = new Scanner(System.in);
        initializeTestData();
    }

    private void initializeTestData() {
        app.registerUser("john_doe", "Pass1234", "Customer");
        app.registerUser("chef_alice", "Chef1234", "Chef");
        app.registerUser("admin", "Admin1234", "Admin");

        // Align ingredient names with MyApplication recipes and LLM prompt
        Ingredients lettuce = new Ingredients("Milk", true);
        Ingredients tomatoes = new Ingredients("Eggs", true);
        Ingredients tofu = new Ingredients("Tofu", true);
        Ingredients cheese = new Ingredients("Cheese", true);
        Ingredients chicken = new Ingredients("Chicken", false);
        Ingredients basil = new Ingredients("basil", true);
        Ingredients pasta = new Ingredients("pasta", true);
        Ingredients oliveOil = new Ingredients("olive oil", true);
        Ingredients garlic = new Ingredients("garlic", true);
        app.setAvailableIngredients(Arrays.asList(lettuce, tomatoes, tofu, cheese, chicken, basil, pasta, oliveOil, garlic));

        Customer customer = new Customer("John Doe", "basil", List.of("Peanut"));
        app.addCustomer(customer);
        app.addPastOrder(customer, "basil Salad");

        Chef chef = new Chef("Alice", "basil");
        app.addChef(chef);
        TaskScheduler scheduler = new TaskScheduler();
        app.addTaskScheduler(scheduler);
        app.addChef(scheduler, chef);

        InventoryManager inventoryManager = new InventoryManager();
        app.addInventoryManager(inventoryManager);
        app.addIngredient(inventoryManager, "Onions", 10);
        app.addIngredient(inventoryManager, "Garlic", 5);
        app.addIngredient(inventoryManager, "Tomatoes", 50);
        app.setCriticalStockLevel(15);

        SupplierService freshFarms = new SupplierService("FreshFarms");
        SupplierService greenGrocers = new SupplierService("GreenGrocers");
        app.addSupplierService(freshFarms);
        app.addSupplierService(greenGrocers);
        app.addPriceToSupplier(freshFarms, "Onions", 0.50);
        app.addPriceToSupplier(freshFarms, "Garlic", 0.30);
        app.addPriceToSupplier(greenGrocers, "Onions", 0.45);
        app.addPriceToSupplier(greenGrocers, "Garlic", 0.35);
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                displayLoginMenu();
            } else {
                switch (currentUser.getRole()) {
                    case "Customer":
                        displayCustomerMenu();
                        break;
                    case "Chef":
                        displayChefMenu();
                        break;
                    case "Admin":
                        displayAdminMenu();
                        break;
                    default:
                        System.out.println("Invalid role!");
                        app.logout();
                }
            }
        }
    }

    private void displayLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegister();
                break;
            case 3:
                System.out.println("Exiting application...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String result = app.login(username, password);
        System.out.println(result);
        if (result.startsWith("Login successful")) {
            currentUser = new User(username, password, app.getCurrentUserRole());
        }
    }

    private void handleRegister() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (Customer/Chef/Admin): ");
        String role = scanner.nextLine();

        String result = app.registerUser(username, password, role);
        System.out.println(result);
    }

    private void displayCustomerMenu() {
        System.out.println("\n=== Customer Menu ===");
        System.out.println("1. Create Custom Meal");
        System.out.println("2. View Past Orders");
        System.out.println("3. Reorder Meal");
        System.out.println("4. Get Recipe Recommendation");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                handleCreateCustomMeal();
                break;
            case 2:
                handleViewPastOrders();
                break;
            case 3:
                handleReorderMeal();
                break;
            case 4:
                handleRecipeRecommendation();
                break;
            case 5:
                app.logout();
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void displayChefMenu() {
        System.out.println("\n=== Chef Menu ===");
        System.out.println("1. View Customer Dietary Profile");
        System.out.println("2. Assign Cooking Task");
        System.out.println("3. View Task Notifications");
        System.out.println("4. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                handleViewCustomerProfile();
                break;
            case 2:
                handleAssignTask();
                break;
            case 3:
                handleViewTaskNotifications();
                break;
            case 4:
                app.logout();
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void displayAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Generate Financial Report");
        System.out.println("2. Check Inventory Levels");
        System.out.println("3. View Customer Order History");
        System.out.println("4. Generate Low Stock Alert");
        System.out.println("5. Fetch Supplier Prices");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                handleGenerateFinancialReport();
                break;
            case 2:
                handleCheckInventory();
                break;
            case 3:
                handleViewOrderHistory();
                break;
            case 4:
                handleLowStockAlert();
                break;
            case 5:
                handleFetchSupplierPrices();
                break;
            case 6:
                app.logout();
                currentUser = null;
                System.out.println("Logged out successfully.");
                break;
            default:
                System.out.println("Invalid option. Try again.");
        }
    }

    private void handleCreateCustomMeal() {
        Customer customer = app.getCustomerByName("John Doe");
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        IngredientService ingredientService = new IngredientService();
        app.addIngredientService(ingredientService);
        app.setAvailableIngredientsForService(ingredientService, app.getAvailableIngredients());

        CustomerOrderService service = new CustomerOrderService(customer, ingredientService);
        app.addCustomerOrderService(service);

        System.out.println("Available ingredients: ");
        for (Ingredients ingredient : app.getAvailableIngredients()) {
            System.out.println("- " + ingredient.getName() + (ingredient.isAvailable() ? " (Available)" : " (Unavailable)"));
        }

        System.out.println("Enter ingredients (one per line, type 'done' to finish): ");
        List<Ingredients> selectedIngredients = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) {
                break;
            }

            Ingredients selected = null;
            for (Ingredients ingredient : app.getAvailableIngredients()) {
                if (ingredient.getName().equalsIgnoreCase(input)) {
                    selected = ingredient;
                    break;
                }
            }

            if (selected != null) {
                selectedIngredients.add(selected);
            } else {
                System.out.println("Ingredient not found. Try again.");
            }
        }

        String result = app.createCustomMeal(service, selectedIngredients);
        System.out.println(result);
    }

    private void handleViewPastOrders() {
        Customer customer = app.getCustomerByName("John Doe");
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        List<String> pastOrders = customer.getPastOrders();
        if (pastOrders.isEmpty()) {
            System.out.println("No past orders found.");
        } else {
            System.out.println("Past orders:");
            for (String order : pastOrders) {
                System.out.println("- " + order);
            }
        }
    }

    private void handleReorderMeal() {
        Customer customer = app.getCustomerByName("John Doe");
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        handleViewPastOrders();
        System.out.print("Enter meal to reorder: ");
        String meal = scanner.nextLine();

        String result = app.reorderMeal(customer, meal);
        if (result != null) {
            System.out.println("Meal reordered: " + result);
        } else {
            System.out.println("Meal not found in past orders.");
        }
    }

    private void handleRecipeRecommendation() {
        Customer customer = app.getCustomerByName("John Doe");
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        // Get available ingredients
        List<String> ingredientNames = app.getAvailableIngredients().stream()
                .filter(Ingredients::isAvailable)
                .map(Ingredients::getName)
                .collect(Collectors.toList());

        System.out.print("Enter available time (minutes): ");
        int timeAvailable = getIntInput();
        if (timeAvailable <= 0) {
            System.out.println("Invalid time entered.");
            return;
        }

        // Simulate LLM recommendation
        Map<String, String> recommendation = simulateLLMRecommendation(
                customer.getDietaryPreference(),
                ingredientNames,
                timeAvailable
        );

        if (recommendation != null && !recommendation.get("recipe").isEmpty()) {
            System.out.println("Recommended Recipe: " + recommendation.get("recipe"));
            System.out.println("Explanation: " + recommendation.get("explanation"));
        } else {
            System.out.println("No suitable recipe found.");
            System.out.println("Explanation: " + recommendation.get("explanation"));
        }
    }

    private Map<String, String> simulateLLMRecommendation(String dietaryRestriction, List<String> availableIngredients, int timeAvailable) {
        Map<String, String> result = new HashMap<>();
        result.put("recipe", "");
        StringBuilder explanation = new StringBuilder();

        // Define the recipe database as per the prompt
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Spaghetti with Tomato Sauce",
                Arrays.asList("Tomatoes", "pasta", "basil", "olive oil"), 25, "basil"));
        recipes.add(new Recipe("Tomato Basil Soup",
                Arrays.asList("Tomatoes", "basil", "garlic"), 40, "basil"));
        recipes.add(new Recipe("basil Pesto Pasta",
                Arrays.asList("basil", "pasta", "olive oil", "garlic"), 20, "basil"));

        for (Recipe recipe : recipes) {
            if (!recipe.getDietaryRestriction().equalsIgnoreCase(dietaryRestriction)) {
                explanation.append("Skipped '").append(recipe.getName())
                        .append("' because it does not match dietary restriction ").append(dietaryRestriction).append(". ");
                continue;
            }
            if (recipe.getPreparationTime() > timeAvailable) {
                explanation.append("Skipped '").append(recipe.getName())
                        .append("' because it takes ").append(recipe.getPreparationTime())
                        .append(" minutes, exceeding available time of ").append(timeAvailable).append(" minutes. ");
                continue;
            }
            List<String> requiredIngredients = recipe.getIngredients();
            List<String> missingIngredients = new ArrayList<>();
            boolean allIngredientsAvailable = true;
            for (String required : requiredIngredients) {
                boolean found = false;
                for (String available : availableIngredients) {
                    if (available.equalsIgnoreCase(required)) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    missingIngredients.add(required);
                    allIngredientsAvailable = false;
                }
            }
            if (allIngredientsAvailable) {
                result.put("recipe", recipe.getName());
                explanation.append("Recommended '").append(recipe.getName())
                        .append("' because it is ").append(dietaryRestriction.toLowerCase())
                        .append(", can be prepared in ").append(recipe.getPreparationTime())
                        .append(" minutes (within ").append(timeAvailable)
                        .append(" minutes), and all required ingredients (")
                        .append(String.join(", ", requiredIngredients)).append(") are available.");
                break;
            } else {
                explanation.append("Considered '").append(recipe.getName())
                        .append("' but missing ingredients: ").append(String.join(", ", missingIngredients)).append(". ");
            }
        }
        if (result.get("recipe").isEmpty()) {
            explanation.append("No recipe matches the given dietary restriction, available ingredients, and time constraints.");
        }
        result.put("explanation", explanation.toString().trim());
        return result;
    }

    private void handleViewCustomerProfile() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        Customer customer = app.getCustomerByName(name);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Customer: " + customer.getName());
        System.out.println("Dietary Preference: " + customer.getDietaryPreference());
        System.out.println("Allergy: " + customer.getAllergy());
    }

    private void handleAssignTask() {
        TaskScheduler scheduler = app.getTaskSchedulerList().get(0);
        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine();
        System.out.print("Enter required expertise: ");
        String expertise = scanner.nextLine();

        Task task = new Task(taskName, expertise);
        app.addTask(task);

        System.out.print("Enter chef name: ");
        String chefName = scanner.nextLine();

        String result = app.notifyChefOfTask(scheduler, task, chefName);
        System.out.println(result);
    }

    private void handleViewTaskNotifications() {
        TaskScheduler scheduler = app.getTaskSchedulerList().get(0);
        Chef chef = app.getChefByName(scheduler, "Alice");
        if (chef == null) {
            System.out.println("Chef not found!");
            return;
        }

        List<Task> tasks = chef.getAssignedTasks();
        if (tasks.isEmpty()) {
            System.out.println("No assigned tasks.");
        } else {
            System.out.println("Assigned tasks:");
            for (Task task : tasks) {
                System.out.println("- " + task.getName() + " (Expertise: " + task.getRequiredExpertise() + ")");
            }
        }
    }

    private void handleGenerateFinancialReport() {
        FinancialReportGenerator report = app.generateFinancialReport();
        System.out.println("Financial Report:");
        System.out.println("Total Revenue: $" + report.getTotalRevenue());
        System.out.println("Invoice Count: " + report.getInvoiceCount());
        if (report.getInvoices().isEmpty()) {
            System.out.println("No invoices found.");
        } else {
            System.out.println("Invoices:");
            for (Invoice invoice : report.getInvoices()) {
                System.out.println("- Invoice ID: " + invoice.getInvoiceId() + ", Total: $" + invoice.getTotalPrice());
            }
        }
    }

    private void handleCheckInventory() {
        InventoryManager manager = app.getInventoryManagerList().get(0);
        System.out.println("Inventory Levels:");
        for (Map.Entry<String, Integer> entry : manager.getInventory().entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " units");
        }
    }

    private void handleViewOrderHistory() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();
        Customer customer = app.getCustomerByName(name);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        List<String> pastOrders = customer.getPastOrders();
        if (pastOrders.isEmpty()) {
            System.out.println("No past orders found.");
        } else {
            System.out.println("Order History for " + name + ":");
            for (String order : pastOrders) {
                System.out.println("- " + order);
            }
        }
    }

    private void handleLowStockAlert() {
        InventoryManager manager = app.getInventoryManagerList().get(0);
        String alert = app.generateLowStockAlert(manager);
        System.out.println(alert);
    }

    private void handleFetchSupplierPrices() {
        System.out.print("Enter ingredient name: ");
        String ingredient = scanner.nextLine();
        List<Map<String, String>> prices = app.fetchSupplierPrices(ingredient);
        if (prices.isEmpty()) {
            System.out.println("No prices found for " + ingredient);
        } else {
            System.out.println("Supplier Prices for " + ingredient + ":");
            for (Map<String, String> price : prices) {
                System.out.println("- " + price.get("Supplier") + ": $" + price.get("PricePerUnit"));
            }
        }
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}
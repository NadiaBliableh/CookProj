package production_code.core;

import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.actors.InventoryManager;
import production_code.actors.User;
import production_code.customer_features.CustomerOrderService;
import production_code.customer_features.PurchaseOrder;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;
import production_code.system_features.FinancialReportGenerator;
import production_code.core.Invoice;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private final Mainn app;
    private final Scanner scanner;
    private User currentUser;
    private Customer currentCustomer;

    public Menu() {
        app = new Mainn();
        scanner = new Scanner(System.in);
        initializeTestData();
    }

    private void initializeTestData() {
        try {
            // Register test users
            app.registerUser("john_doe", "Pass1234", "Customer");
            app.registerUser("chef_alice", "Chef1234", "Chef");
            app.registerUser("admin", "Admin1234", "Admin");

            // Initialize vegan-compatible ingredients
            Ingredients[] ingredients = {
                    new Ingredients("Lettuce", true),
                    new Ingredients("Tomato", true),
                    new Ingredients("Tofu", true),
                    new Ingredients("Basil", true),
                    new Ingredients("Pasta", true),
                    new Ingredients("Olive Oil", true),
                    new Ingredients("Garlic", true)
            };
            app.setAvailableIngredients(Arrays.asList(ingredients));

            // Initialize customer
            Customer customer = new Customer("John Doe", "Vegan", List.of("Peanut"));
            app.addCustomer(customer);
            app.addPastOrder(customer, "Vegan Salad");

            // Initialize chef and task scheduler
            Chef chef = new Chef("Alice", "Vegan");
            app.addChef(chef);
            TaskScheduler scheduler = new TaskScheduler();
            app.addTaskScheduler(scheduler);
            app.addChef(scheduler, chef);

            // Initialize inventory
            InventoryManager inventoryManager = new InventoryManager();
            app.addInventoryManager(inventoryManager);
            app.addIngredient(inventoryManager, "Tomato", 50);
            app.addIngredient(inventoryManager, "Garlic", 5);
            app.addIngredient(inventoryManager, "Onions", 10);
            app.setCriticalStockLevel(15);

            // Initialize suppliers
            SupplierService freshFarms = new SupplierService("FreshFarms");
            SupplierService greenGrocers = new SupplierService("GreenGrocers");
            app.addSupplierService(freshFarms);
            app.addSupplierService(greenGrocers);
            app.addPriceToSupplier(freshFarms, "Onions", 0.50);
            app.addPriceToSupplier(freshFarms, "Garlic", 0.30);
            app.addPriceToSupplier(greenGrocers, "Onions", 0.45);
            app.addPriceToSupplier(greenGrocers, "Garlic", 0.35);
        } catch (Exception e) {
            System.out.println("Error initializing test data: " + e.getMessage());
        }
    }

    public void start() {
        while (true) {
            try {
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
                            System.out.println("Invalid role! Logging out.");
                            app.logout();
                            currentUser = null;
                            currentCustomer = null;
                    }
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void displayLoginMenu() {
        System.out.println("\n=== Login Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");

        int choice = getValidIntInput(1, 3);
        if (choice == -1) return;

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
        }
    }

    private void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty!");
            return;
        }

        try {
            String result = app.login(username, password);
            System.out.println(result);
            if (result.startsWith("Login successful")) {
                currentUser = new User(username, password, app.getCurrentUserRole());
                if (currentUser.getRole().equals("Customer")) {
                    currentCustomer = app.getCustomerByName("John Doe");
                    if (currentCustomer == null) {
                        System.out.println("Warning: No customer profile found for this user.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private void handleRegister() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();
        if (password.isEmpty()) {
            System.out.println("Password cannot be empty!");
            return;
        }

        System.out.print("Enter role (Customer/Chef/Admin): ");
        String role = scanner.nextLine().trim();
        if (role.isEmpty() || !Arrays.asList("Customer", "Chef", "Admin").contains(role)) {
            System.out.println("Invalid role! Must be Customer, Chef, or Admin.");
            return;
        }

        try {
            String result = app.registerUser(username, password, role);
            System.out.println(result);
            if (role.equals("Customer")) {
                System.out.print("Enter customer name: ");
                String customerName = scanner.nextLine().trim();
                if (customerName.isEmpty()) {
                    System.out.println("Customer name cannot be empty!");
                    return;
                }
                System.out.print("Enter dietary preference (e.g., Vegan): ");
                String dietary = scanner.nextLine().trim();
                System.out.print("Enter allergies (comma-separated, or none): ");
                String allergiesInput = scanner.nextLine().trim();
                List<String> allergies = allergiesInput.equalsIgnoreCase("none") ?
                        new ArrayList<>() : Arrays.asList(allergiesInput.split("\\s*,\\s*"));
                Customer customer = new Customer(customerName, dietary, allergies);
                customer.setAllergies(allergies);
                app.addCustomer(customer);
            }
        } catch (Exception e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    private void displayCustomerMenu() {
        System.out.println("\n=== Customer Menu ===");
        System.out.println("1. Create Custom Meal");
        System.out.println("2. View Past Orders");
        System.out.println("3. Reorder Meal");
        System.out.println("4. Get Recipe Recommendation");
        System.out.println("5. Update Dietary Preferences");
        System.out.println("6. Check Delivery Reminders");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");

        int choice = getValidIntInput(1, 7);
        if (choice == -1) return;

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
                handleUpdateDietaryPreferences();
                break;
            case 6:
                handleDeliveryReminders();
                break;
            case 7:
                app.logout();
                currentUser = null;
                currentCustomer = null;
                System.out.println("Logged out successfully.");
        }
    }

    private void displayChefMenu() {
        System.out.println("\n=== Chef Menu ===");
        System.out.println("1. View Customer Dietary Profile");
        System.out.println("2. Assign Cooking Task");
        System.out.println("3. View Task Notifications");
        System.out.println("4. Suggest Meal Substitutions");
        System.out.println("5. Logout");
        System.out.print("Choose an option: ");

        int choice = getValidIntInput(1, 5);
        if (choice == -1) return;

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
                handleSuggestSubstitutions();
                break;
            case 5:
                app.logout();
                currentUser = null;
                currentCustomer = null;
                System.out.println("Logged out successfully.");
        }
    }

    private void displayAdminMenu() {
        System.out.println("\n=== Admin Menu ===");
        System.out.println("1. Generate Financial Report");
        System.out.println("2. Check Inventory Levels");
        System.out.println("3. View Customer Order History");
        System.out.println("4. Generate Low Stock Alert");
        System.out.println("5. Fetch Supplier Prices");
        System.out.println("6. Set Critical Stock Level");
        System.out.println("7. Generate Purchase Orders");
        System.out.println("8. Logout");
        System.out.print("Choose an option: ");

        int choice = getValidIntInput(1, 8);
        if (choice == -1) return;

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
                handleSetCriticalStockLevel();
                break;
            case 7:
                handleGeneratePurchaseOrders();
                break;
            case 8:
                app.logout();
                currentUser = null;
                currentCustomer = null;
                System.out.println("Logged out successfully.");
        }
    }

    private void handleCreateCustomMeal() {
        if (currentCustomer == null) {
            System.out.println("Customer profile not found! Please log in as a customer.");
            return;
        }

        CustomerOrderService service = new CustomerOrderService(currentCustomer);

        List<Ingredients> availableIngredients = app.getAvailableIngredients();
        if (availableIngredients == null || availableIngredients.isEmpty()) {
            System.out.println("No ingredients available at the moment!");
            return;
        }

        System.out.println("Available ingredients:");
        for (Ingredients ingredient : availableIngredients) {
            System.out.println("- " + ingredient.getName() + (ingredient.isAvailable() ? " (Available)" : " (Unavailable)"));
        }

        System.out.println("Enter ingredients (one per line, type 'done' to finish):");
        List<Ingredients> selectedIngredients = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty! Try again.");
                continue;
            }

            Ingredients selected = availableIngredients.stream()
                    .filter(i -> i.getName().equalsIgnoreCase(input))
                    .findFirst()
                    .orElse(null);

            if (selected == null) {
                System.out.println("Ingredient '" + input + "' not found. Try again.");
                continue;
            }

            if (!selected.isAvailable()) {
                System.out.println(selected.getName() + " is currently unavailable. Please choose a different ingredient.");
                suggestSubstitution(selected.getName(), currentCustomer);
                continue;
            }

            if (isIncompatible(currentCustomer, selected.getName())) {
                System.out.println(selected.getName() + " is not compatible with your dietary preferences or allergies.");
                System.out.println("Please choose a different ingredient.");
                suggestSubstitution(selected.getName(), currentCustomer);
                continue;
            }

            selectedIngredients.add(selected);
        }

        if (selectedIngredients.isEmpty()) {
            System.out.println("No valid ingredients selected. Meal creation cancelled.");
            return;
        }

        try {
            String result = app.createCustomMeal(service, selectedIngredients);
            System.out.println(result);
            if (result.equals("Custom meal created successfully.")) {
                String addResult = app.addMealToOrder(service, new Meal(selectedIngredients));
                System.out.println(addResult);
            }
        } catch (Exception e) {
            System.out.println("Error creating custom meal: " + e.getMessage());
        }
    }

    private boolean isIncompatible(Customer customer, String ingredient) {
        try {
            String dietary = customer.getDietaryPreference() != null ? customer.getDietaryPreference().toLowerCase() : "";
            if (dietary.equals("vegan") && (ingredient.equalsIgnoreCase("Cheese") || ingredient.equalsIgnoreCase("Chicken"))) {
                return true;
            }
            List<String> allergies = customer.getAllergies() != null ? customer.getAllergies() : new ArrayList<>();
            return allergies.stream().anyMatch(allergy -> ingredient != null && ingredient.toLowerCase().contains(allergy.toLowerCase()));
        } catch (Exception e) {
            System.out.println("Error checking compatibility: " + e.getMessage());
            return false;
        }
    }

    private void suggestSubstitution(String originalIngredient, Customer customer) {
        List<String> alternatives = new ArrayList<>();
        String dietary = customer.getDietaryPreference() != null ? customer.getDietaryPreference().toLowerCase() : "";
        List<Ingredients> availableIngredients = app.getAvailableIngredients();
        if (availableIngredients == null || availableIngredients.isEmpty()) {
            System.out.println("No ingredients available to suggest!");
            return;
        }

        for (Ingredients ingredient : availableIngredients) {
            if (ingredient.isAvailable() && !isIncompatible(customer, ingredient.getName()) &&
                    !ingredient.getName().equalsIgnoreCase(originalIngredient)) {
                alternatives.add(ingredient.getName());
            }
        }

        if (alternatives.isEmpty()) {
            System.out.println("No suitable alternatives available for " + originalIngredient + ".");
        } else {
            System.out.println("Suggested alternatives for " + originalIngredient + ":");
            alternatives.forEach(alt -> System.out.println("- " + alt));
        }
    }

    private void handleViewPastOrders() {
        if (currentCustomer == null) {
            System.out.println("Customer profile not found! Please log in as a customer.");
            return;
        }

        List<String> pastOrders = currentCustomer.getPastOrders();
        if (pastOrders == null || pastOrders.isEmpty()) {
            System.out.println("No past orders found.");
        } else {
            System.out.println("Past orders:");
            for (String order : pastOrders) {
                System.out.println("- " + order);
            }
        }
    }

    private void handleReorderMeal() {
        if (currentCustomer == null) {
            System.out.println("Customer profile not found! Please log in as a customer.");
            return;
        }

        handleViewPastOrders();
        System.out.print("Enter meal to reorder: ");
        String meal = scanner.nextLine().trim();
        if (meal.isEmpty()) {
            System.out.println("Meal name cannot be empty!");
            return;
        }

        try {
            String result = app.reorderMeal(currentCustomer, meal);
            System.out.println(result != null ? "Meal reordered: " + result : "Meal not found in past orders.");
        } catch (Exception e) {
            System.out.println("Error during reorder: " + e.getMessage());
        }
    }

    private void handleRecipeRecommendation() {
        if (currentCustomer == null) {
            System.out.println("Customer profile not found! Please log in as a customer.");
            return;
        }

        List<String> ingredientNames = app.getAvailableIngredients() != null ?
                app.getAvailableIngredients().stream()
                        .filter(Ingredients::isAvailable)
                        .map(Ingredients::getName)
                        .collect(Collectors.toList()) : new ArrayList<>();

        System.out.print("Enter available time (minutes): ");
        int timeAvailable = getValidIntInput(1, Integer.MAX_VALUE);
        if (timeAvailable == -1) {
            System.out.println("Invalid time entered.");
            return;
        }

        try {
            Map<String, String> recommendation = simulateLLMRecommendation(
                    currentCustomer.getDietaryPreference(),
                    currentCustomer.getAllergies(),
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
        } catch (Exception e) {
            System.out.println("Error recommending recipe: " + e.getMessage());
        }
    }

    private Map<String, String> simulateLLMRecommendation(String dietaryRestriction, List<String> allergies,
                                                          List<String> availableIngredients, int timeAvailable) {
        Map<String, String> result = new HashMap<>();
        result.put("recipe", "");
        StringBuilder explanation = new StringBuilder();

        if (availableIngredients == null || availableIngredients.isEmpty()) {
            explanation.append("No ingredients available to recommend a recipe.");
            result.put("explanation", explanation.toString().trim());
            return result;
        }

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe("Spaghetti with Tomato Sauce",
                Arrays.asList("Tomato", "Pasta", "Basil", "Olive Oil"), 25, "Vegan"));
        recipes.add(new Recipe("Tomato Basil Soup",
                Arrays.asList("Tomato", "Basil", "Garlic"), 40, "Vegan"));
        recipes.add(new Recipe("Basil Pesto Pasta",
                Arrays.asList("Basil", "Pasta", "Olive Oil", "Garlic"), 20, "Vegan"));
        recipes.add(new Recipe("Tofu Stir Fry",
                Arrays.asList("Tofu", "Lettuce", "Garlic"), 30, "Vegan"));

        for (Recipe recipe : recipes) {
            if (!recipe.getDietaryRestriction().equalsIgnoreCase(dietaryRestriction != null ? dietaryRestriction : "")) {
                explanation.append("Skipped '" + recipe.getName() + "' due to dietary mismatch. ");
                continue;
            }
            if (recipe.getPreparationTime() > timeAvailable) {
                explanation.append("Skipped '" + recipe.getName() + "' due to time constraint. ");
                continue;
            }
            List<String> requiredIngredients = recipe.getIngredients();
            List<String> missingIngredients = new ArrayList<>();
            boolean hasAllergy = false;
            boolean allIngredientsAvailable = true;
            for (String required : requiredIngredients) {
                if (allergies != null && allergies.stream().anyMatch(a -> required.toLowerCase().contains(a.toLowerCase()))) {
                    hasAllergy = true;
                    break;
                }
                if (!availableIngredients.stream().anyMatch(avail -> avail.equalsIgnoreCase(required))) {
                    missingIngredients.add(required);
                    allIngredientsAvailable = false;
                }
            }
            if (hasAllergy) {
                explanation.append("Skipped '" + recipe.getName() + "' due to allergies. ");
                continue;
            }
            if (allIngredientsAvailable) {
                result.put("recipe", recipe.getName());
                explanation.append("Recommended '" + recipe.getName() + "' as it matches " + dietaryRestriction +
                        ", fits within " + timeAvailable + " minutes, and all ingredients are available.");
                break;
            } else {
                explanation.append("Skipped '" + recipe.getName() + "' due to missing ingredients: " +
                        String.join(", ", missingIngredients) + ". ");
            }
        }
        if (result.get("recipe").isEmpty()) {
            explanation.append("No recipe matches the criteria.");
        }
        result.put("explanation", explanation.toString().trim());
        return result;
    }

    private void handleUpdateDietaryPreferences() {
        if (currentCustomer == null) {
            System.out.println("Customer profile not found! Please log in as a customer.");
            return;
        }

        System.out.print("Enter new dietary preference (e.g., Vegan, Vegetarian): ");
        String dietary = scanner.nextLine().trim();
        if (dietary.isEmpty()) {
            System.out.println("Dietary preference cannot be empty!");
            return;
        }

        System.out.print("Enter allergies (comma-separated, or none): ");
        String allergiesInput = scanner.nextLine().trim();
        if (allergiesInput.isEmpty()) {
            System.out.println("Allergies cannot be empty! Enter 'none' if there are no allergies.");
            return;
        }

        List<String> allergies = allergiesInput.equalsIgnoreCase("none") ?
                new ArrayList<>() : Arrays.asList(allergiesInput.split("\\s*,\\s*"));

        try {
            currentCustomer.setDietaryPreference(dietary);
            currentCustomer.setAllergies(allergies);
            System.out.println("Dietary preferences updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating dietary preferences: " + e.getMessage());
        }
    }

    private void handleDeliveryReminders() {
        if (currentCustomer == null) {
            System.out.println("Customer profile not found! Please log in as a customer.");
            return;
        }

        List<Order> orders = app.getCustomerOrders(currentCustomer);
        if (orders == null || orders.isEmpty()) {
            System.out.println("No upcoming orders found.");
            return;
        }

        for (Order order : orders) {
            try {
                String notification = app.sendDeliveryReminder(currentCustomer, order);
                System.out.println(notification != null ? notification : "No reminders available for this order.");
            } catch (Exception e) {
                System.out.println("Error sending delivery reminder: " + e.getMessage());
            }
        }
    }

    private void handleViewCustomerProfile() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Customer name cannot be empty!");
            return;
        }

        Customer customer = app.getCustomerByName(name);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.println("Customer: " + customer.getName());
        System.out.println("Dietary Preference: " + (customer.getDietaryPreference() != null ? customer.getDietaryPreference() : "Not specified"));
        System.out.println("Allergies: " + (customer.getAllergies() != null && !customer.getAllergies().isEmpty() ? String.join(", ", customer.getAllergies()) : "None"));
    }

    private void handleAssignTask() {
        TaskScheduler scheduler = app.getTaskSchedulerList() != null ?
                app.getTaskSchedulerList().stream().findFirst().orElse(null) : null;
        if (scheduler == null) {
            System.out.println("No task scheduler available!");
            return;
        }

        System.out.print("Enter task name: ");
        String taskName = scanner.nextLine().trim();
        if (taskName.isEmpty()) {
            System.out.println("Task name cannot be empty!");
            return;
        }

        System.out.print("Enter required expertise: ");
        String expertise = scanner.nextLine().trim();
        if (expertise.isEmpty()) {
            System.out.println("Expertise cannot be empty!");
            return;
        }

        Task task = new Task(taskName, expertise);
        try {
            app.addTask(task);
            String result = app.assignTask(scheduler, task);
            if (result != null && result.startsWith("Task assigned to")) {
                System.out.println(result);
            } else {
                System.out.println("No suitable chef found. Assign manually? (y/n)");
                String response = scanner.nextLine().trim();
                if (response.equalsIgnoreCase("y")) {
                    System.out.print("Enter chef name: ");
                    String chefName = scanner.nextLine().trim();
                    if (chefName.isEmpty()) {
                        System.out.println("Chef name cannot be empty!");
                        return;
                    }
                    result = app.notifyChefOfTask(scheduler, task, chefName);
                    System.out.println(result != null ? result : "Failed to notify chef.");
                } else {
                    System.out.println("Task not assigned.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error assigning task: " + e.getMessage());
        }
    }

    private void handleViewTaskNotifications() {
        TaskScheduler scheduler = app.getTaskSchedulerList() != null ?
                app.getTaskSchedulerList().stream().findFirst().orElse(null) : null;
        if (scheduler == null) {
            System.out.println("No task scheduler available!");
            return;
        }

        Chef chef = app.getChefByName(scheduler, currentUser.getUsername());
        if (chef == null) {
            System.out.println("Chef profile not found!");
            return;
        }

        List<Task> tasks = chef.getAssignedTasks();
        if (tasks == null || tasks.isEmpty()) {
            System.out.println("No assigned tasks.");
        } else {
            System.out.println("Assigned tasks:");
            for (Task task : tasks) {
                System.out.println("- " + task.getName() + " (Expertise: " + task.getRequiredExpertise() + ")");
            }
        }
    }

    private void handleSuggestSubstitutions() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine().trim();
        if (customerName.isEmpty()) {
            System.out.println("Customer name cannot be empty!");
            return;
        }

        Customer customer = app.getCustomerByName(customerName);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        System.out.print("Enter ingredient to substitute: ");
        String ingredient = scanner.nextLine().trim();
        if (ingredient.isEmpty()) {
            System.out.println("Ingredient name cannot be empty!");
            return;
        }

        suggestSubstitution(ingredient, customer);
    }

    private void handleGenerateFinancialReport() {
        try {
            FinancialReportGenerator report = app.generateFinancialReport();
            if (report == null) {
                System.out.println("Failed to generate financial report!");
                return;
            }

            System.out.println("Financial Report:");
            System.out.println("Total Revenue: $" + report.getTotalRevenue());
            System.out.println("Invoice Count: " + report.getInvoiceCount());
            if (report.getInvoices() == null || report.getInvoices().isEmpty()) {
                System.out.println("No invoices found.");
            } else {
                System.out.println("Invoices:");
                for (Invoice invoice : report.getInvoices()) {
                    System.out.println("- Invoice ID: " + invoice.getInvoiceId());
                    System.out.println("  Customer: " + invoice.getCustomer().getName());
                    System.out.println("  Total: $" + invoice.getTotalPrice());
                    System.out.println("  Items:");
                    for (Meal meal : invoice.getItems()) {
                        System.out.println("    - Meal with ingredients: " +
                                meal.getIngredients().stream().map(Ingredients::getName).collect(Collectors.joining(", ")));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error generating financial report: " + e.getMessage());
        }
    }

    private void handleCheckInventory() {
        InventoryManager manager = app.getInventoryManagerList() != null ?
                app.getInventoryManagerList().stream().findFirst().orElse(null) : null;
        if (manager == null) {
            System.out.println("No inventory manager available!");
            return;
        }

        System.out.println("Inventory Levels:");
        Map<String, Integer> inventory = manager.getInventory();
        if (inventory == null || inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " units");
        }
    }

    private void handleViewOrderHistory() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Customer name cannot be empty!");
            return;
        }

        Customer customer = app.getCustomerByName(name);
        if (customer == null) {
            System.out.println("Customer not found!");
            return;
        }

        List<String> pastOrders = customer.getPastOrders();
        if (pastOrders == null || pastOrders.isEmpty()) {
            System.out.println("No past orders found.");
        } else {
            System.out.println("Order History for " + name + ":");
            for (String order : pastOrders) {
                System.out.println("- " + order);
            }
        }
    }

    private void handleLowStockAlert() {
        InventoryManager manager = app.getInventoryManagerList() != null ?
                app.getInventoryManagerList().stream().findFirst().orElse(null) : null;
        if (manager == null) {
            System.out.println("No inventory manager available!");
            return;
        }

        try {
            String alert = app.generateLowStockAlert(manager);
            System.out.println(alert != null ? alert : "No low stock alerts.");
        } catch (Exception e) {
            System.out.println("Error generating low stock alert: " + e.getMessage());
        }
    }

    private void handleFetchSupplierPrices() {
        System.out.print("Enter ingredient name: ");
        String ingredient = scanner.nextLine().trim();
        if (ingredient.isEmpty()) {
            System.out.println("Ingredient name cannot be empty!");
            return;
        }

        try {
            List<Map<String, String>> prices = app.fetchSupplierPrices(ingredient);
            if (prices == null || prices.isEmpty()) {
                System.out.println("No prices found for " + ingredient);
            } else {
                System.out.println("Supplier Prices for " + ingredient + ":");
                for (Map<String, String> price : prices) {
                    System.out.println("- " + price.get("Supplier") + ": $" + price.get("PricePerUnit"));
                }
            }
        } catch (Exception e) {
            System.out.println("Error fetching supplier prices: " + e.getMessage());
        }
    }

    private void handleSetCriticalStockLevel() {
        System.out.print("Enter critical stock level (units): ");
        int level = getValidIntInput(1, Integer.MAX_VALUE);
        if (level == -1) {
            System.out.println("Invalid stock level.");
            return;
        }

        try {
            app.setCriticalStockLevel(level);
            System.out.println("Critical stock level set to " + level + " units.");
        } catch (Exception e) {
            System.out.println("Error setting critical stock level: " + e.getMessage());
        }
    }

    private void handleGeneratePurchaseOrders() {
        InventoryManager manager = app.getInventoryManagerList() != null ?
                app.getInventoryManagerList().stream().findFirst().orElse(null) : null;
        if (manager == null) {
            System.out.println("No inventory manager available!");
            return;
        }

        try {
            PurchaseOrder order = app.checkInventoryAndOrder(manager);
            if (order == null) {
                System.out.println("No purchase orders generated. All stock levels are sufficient.");
            } else {
                String notification = app.notifyManager(order);
                System.out.println(notification != null ? notification : "Failed to notify manager.");
            }
        } catch (Exception e) {
            System.out.println("Error generating purchase orders: " + e.getMessage());
        }
    }

    private int getValidIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    System.out.print("Input cannot be empty! Please enter a number between " + min + " and " + max + ": ");
                    continue;
                }
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.print("Please enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print("Error reading input. Please enter a number between " + min + " and " + max + ": ");
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}
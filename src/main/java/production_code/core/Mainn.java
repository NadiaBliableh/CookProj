package production_code.core;




import production_code.actors.Chef;
import production_code.actors.Customer;
import production_code.actors.InventoryManager;
import production_code.actors.User;
import production_code.customer_features.CustomerOrderService;
import production_code.customer_features.PurchaseOrder;
import production_code.InventoryManagerfeature.RestockSuggestion;
import production_code.kitchen_manager_features.Task;
import production_code.kitchen_manager_features.TaskScheduler;
import production_code.system_features.FinancialReportGenerator;

import java.util.*;

public class Mainn {
    private List<Chef> chefList;
    private List<Task> taskList;
    private List<Meal> mealList;
    private List<Order> orderList;
    private List<Customer> customerProfileList;
    private List<Ingredients> ingredientList;
    private List<CustomerOrderService> customerOrderServiceList;
    private List<IngredientService> ingredientServiceList;
    private List<InventoryManager> inventoryManagerList;
    private List<RestockSuggestion> restockSuggestionList;
    private List<TaskScheduler> taskSchedulerList;
    private List<SupplierService> supplierServiceList;
    private List<PurchaseOrder> purchaseOrderList;
    private List<Invoice> invoiceList;
    private List<Invoice> salesRecords;
    private List<User> userList; // Added for login feature
    private User currentUser; // Tracks logged-in user
    private int chefIndex;
    private int taskIndex;
    private int mealIndex;
    private int orderIndex;
    private int customerProfileIndex;
    private int ingredientIndex;
    private int customerOrderServiceIndex;
    private int ingredientServiceIndex;
    private int inventoryManagerIndex;
    private int restockSuggestionIndex;
    private int taskSchedulerIndex;
    private int supplierServiceIndex;
    private int purchaseOrderIndex;
    private int invoiceIndex;
    private int salesRecordIndex;
    private int criticalStockLevel;
    private List<Ingredients> availableIngredients;
    private List<Recipe> recipeList;

    public Mainn() {
        this.chefList = new ArrayList<>();
        this.taskList = new ArrayList<>();
        this.mealList = new ArrayList<>();
        this.orderList = new ArrayList<>();
        this.customerProfileList = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
        this.customerOrderServiceList = new ArrayList<>();
        this.ingredientServiceList = new ArrayList<>();
        this.inventoryManagerList = new ArrayList<>();
        this.restockSuggestionList = new ArrayList<>();
        this.taskSchedulerList = new ArrayList<>();
        this.supplierServiceList = new ArrayList<>();
        this.purchaseOrderList = new ArrayList<>();
        this.invoiceList = new ArrayList<>();
        this.salesRecords = new ArrayList<>();
        this.userList = new ArrayList<>(); // Initialize user list
        this.currentUser = null; // No user logged in initially
        this.chefIndex = 0;
        this.taskIndex = 0;
        this.mealIndex = 0;
        this.orderIndex = 0;
        this.customerProfileIndex = 0;
        this.ingredientIndex = 0;
        this.customerOrderServiceIndex = 0;
        this.ingredientServiceIndex = 0;
        this.inventoryManagerIndex = 0;
        this.restockSuggestionIndex = 0;
        this.taskSchedulerIndex = 0;
        this.supplierServiceIndex = 0;
        this.purchaseOrderIndex = 0;
        this.invoiceIndex = 0;
        this.salesRecordIndex = 0;
        this.criticalStockLevel = 5;
        this.availableIngredients = new ArrayList<>();
        this.recipeList = new ArrayList<>();
        initializeRecipes();
    }

    private void initializeRecipes() {
        recipeList.add(new Recipe("Spaghetti with Tomato Sauce",
                Arrays.asList("Tomatoes", "pasta", "basil", "olive oil"), 25, "Vegan"));
        recipeList.add(new Recipe("Tomato Basil Soup",
                Arrays.asList("Tomatoes", "basil", "garlic"), 40, "Vegan"));
        recipeList.add(new Recipe("Vegan Pesto Pasta",
                Arrays.asList("basil", "pasta", "olive oil", "garlic"), 20, "Vegan"));
    }

    // Login-related methods
    public String registerUser(String username, String password, String role) {
        if (username == null || password == null || role == null) {
            throw new IllegalArgumentException("Username, password, and role cannot be null");
        }
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return "Username already exists";
            }
        }
        User user = new User(username, password, role);
        userList.add(user);
        return "User registered successfully";
    }

    public String login(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username and password cannot be null");
        }
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return "Login successful for " + username + " as " + user.getRole();
            }
        }
        return "Invalid username or password";
    }

    public String getCurrentUserRole() {
        return currentUser != null ? currentUser.getRole() : null;
    }

    public void logout() {
        currentUser = null;
    }

    public Map<String, String> recommendRecipe(String dietaryRestriction, List<String> availableIngredients, int timeAvailable) {
        if (dietaryRestriction == null || availableIngredients == null || timeAvailable < 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        Map<String, String> result = new HashMap<>();
        result.put("recipe", "");
        StringBuilder explanation = new StringBuilder();

        for (Recipe recipe : recipeList) {
            // Check if recipe matches dietary restriction
            if (!recipe.getDietaryRestriction().equalsIgnoreCase(dietaryRestriction)) {
                continue;
            }

            // Check if preparation time is within the available time
            if (recipe.getPreparationTime() > timeAvailable) {
                continue;
            }

            // Check if all required ingredients are available
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
                        .append(" minutes (within ").append(timeAvailable).append(" minutes).");
                break; // Return the first matching recipe
            } else {
                explanation.append("Considered '").append(recipe.getName())
                        .append("' but missing ingredients: ").append(String.join(", ", missingIngredients)).append(". ");
            }
        }

        if (result.get("recipe").isEmpty()) {
            explanation.append("No recipe matches the given dietary restriction and time constraints.");
        }

        result.put("explanation", explanation.toString().trim());
        return result;
    }

    public void setAvailableIngredientsForService(IngredientService service, List<Ingredients> ingredients) {
        if (service == null) throw new IllegalArgumentException("IngredientService cannot be null");
        service.getAvailableIngredients().clear();
        if (ingredients != null) {
            service.getAvailableIngredients().addAll(ingredients);
        }
    }

    public void setMealsForOrder(Order order, List<Meal> meals) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        order.getMeals().clear();
        if (meals != null) {
            order.getMeals().addAll(meals);
        }
    }

    public void setSupplierNameForPurchaseOrder(PurchaseOrder order, String supplierName) {
        if (order == null) throw new IllegalArgumentException("PurchaseOrder cannot be null");
        order.getClass().getDeclaredFields();
        try {
            java.lang.reflect.Field field = PurchaseOrder.class.getDeclaredField("supplierName");
            field.setAccessible(true);
            field.set(order, supplierName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot set supplierName", e);
        }
    }

    public void setIngredientForPurchaseOrder(PurchaseOrder order, String ingredient) {
        if (order == null) throw new IllegalArgumentException("PurchaseOrder cannot be null");
        try {
            java.lang.reflect.Field field = PurchaseOrder.class.getDeclaredField("ingredient");
            field.setAccessible(true);
            field.set(order, ingredient);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot set ingredient", e);
        }
    }

    public void setQuantityForPurchaseOrder(PurchaseOrder order, int quantity) {
        if (order == null) throw new IllegalArgumentException("PurchaseOrder cannot be null");
        try {
            java.lang.reflect.Field field = PurchaseOrder.class.getDeclaredField("quantity");
            field.setAccessible(true);
            field.setInt(order, quantity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot set quantity", e);
        }
    }

    public void setPricePerUnitForPurchaseOrder(PurchaseOrder order, double pricePerUnit) {
        if (order == null) throw new IllegalArgumentException("PurchaseOrder cannot be null");
        try {
            java.lang.reflect.Field field = PurchaseOrder.class.getDeclaredField("pricePerUnit");
            field.setAccessible(true);
            field.setDouble(order, pricePerUnit);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot set pricePerUnit", e);
        }
    }

    public List<Chef> getChefList() { return chefList; }
    public void setChefList(List<Chef> chefList) { this.chefList = chefList; this.chefIndex = 0; }
    public List<Task> getTaskList() { return taskList; }
    public void setTaskList(List<Task> taskList) { this.taskList = taskList; this.taskIndex = 0; }
    public List<Meal> getMealList() { return mealList; }
    public void setMealList(List<Meal> mealList) { this.mealList = mealList; this.mealIndex = 0; }
    public List<Order> getOrderList() { return orderList; }
    public void setOrderList(List<Order> orderList) { this.orderList = orderList; this.orderIndex = 0; }
    public List<Customer> getCustomerProfileList() { return customerProfileList; }
    public void setCustomerProfileList(List<Customer> customerProfileList) { this.customerProfileList = customerProfileList; this.customerProfileIndex = 0; }
    public List<Ingredients> getIngredientList() { return ingredientList; }
    public void setIngredientList(List<Ingredients> ingredientList) { this.ingredientList = ingredientList; this.ingredientIndex = 0; }
    public List<CustomerOrderService> getCustomerOrderServiceList() { return customerOrderServiceList; }
    public void setCustomerOrderServiceList(List<CustomerOrderService> customerOrderServiceList) { this.customerOrderServiceList = customerOrderServiceList; this.customerOrderServiceIndex = 0; }
    public List<IngredientService> getIngredientServiceList() { return ingredientServiceList; }
    public void setIngredientServiceList(List<IngredientService> ingredientServiceList) { this.ingredientServiceList = ingredientServiceList; this.ingredientServiceIndex = 0; }
    public List<InventoryManager> getInventoryManagerList() { return inventoryManagerList; }
    public void setInventoryManagerList(List<InventoryManager> inventoryManagerList) { this.inventoryManagerList = inventoryManagerList; this.inventoryManagerIndex = 0; }
    public List<RestockSuggestion> getRestockSuggestionList() { return restockSuggestionList; }
    public void setRestockSuggestionList(List<RestockSuggestion> restockSuggestionList) { this.restockSuggestionList = restockSuggestionList; this.restockSuggestionIndex = 0; }
    public List<TaskScheduler> getTaskSchedulerList() { return taskSchedulerList; }
    public void setTaskSchedulerList(List<TaskScheduler> taskSchedulerList) { this.taskSchedulerList = taskSchedulerList; this.taskSchedulerIndex = 0; }
    public List<SupplierService> getSupplierServiceList() { return supplierServiceList; }
    public void setSupplierServiceList(List<SupplierService> supplierServiceList) { this.supplierServiceList = supplierServiceList; this.supplierServiceIndex = 0; }
    public List<PurchaseOrder> getPurchaseOrderList() { return purchaseOrderList; }
    public void setPurchaseOrderList(List<PurchaseOrder> purchaseOrderList) { this.purchaseOrderList = purchaseOrderList; this.purchaseOrderIndex = 0; }
    public List<Invoice> getInvoiceList() { return invoiceList; }
    public void setInvoiceList(List<Invoice> invoiceList) { this.invoiceList = invoiceList; this.invoiceIndex = 0; }
    public List<Invoice> getSalesRecords() { return salesRecords; }
    public void setSalesRecords(List<Invoice> salesRecords) { this.salesRecords = salesRecords; this.salesRecordIndex = 0; }
    public int getCriticalStockLevel() { return criticalStockLevel; }
    public void setCriticalStockLevel(int criticalStockLevel) { this.criticalStockLevel = criticalStockLevel; }

    public Chef getNextChef() {
        if (chefIndex >= chefList.size()) throw new IllegalStateException("No more Chef objects available");
        return chefList.get(chefIndex++);
    }
    public Task getNextTask() {
        if (taskIndex >= taskList.size()) throw new IllegalStateException("No more Task objects available");
        return taskList.get(taskIndex++);
    }
    public Meal getNextMeal() {
        if (mealIndex >= mealList.size()) throw new IllegalStateException("No more Meal objects available");
        return mealList.get(mealIndex++);
    }
    public Order getNextOrder() {
        if (orderIndex >= orderList.size()) throw new IllegalStateException("No more Order objects available");
        return orderList.get(orderIndex++);
    }
    public Customer getNextCustomer() {
        if (customerProfileIndex >= customerProfileList.size()) throw new IllegalStateException("No more CustomerProfile objects available");
        return customerProfileList.get(customerProfileIndex++);
    }
    public Ingredients getNextIngredient() {
        if (ingredientIndex >= ingredientList.size()) throw new IllegalStateException("No more Ingredient objects available");
        return ingredientList.get(ingredientIndex++);
    }
    public CustomerOrderService getNextCustomerOrderService() {
        if (customerOrderServiceIndex >= customerOrderServiceList.size()) throw new IllegalStateException("No more CustomerOrderService objects available");
        return customerOrderServiceList.get(customerOrderServiceIndex++);
    }
    public IngredientService getNextIngredientService() {
        if (ingredientServiceIndex >= ingredientServiceList.size()) throw new IllegalStateException("No more IngredientService objects available");
        return ingredientServiceList.get(ingredientServiceIndex++);
    }
    public InventoryManager getNextInventoryManager() {
        if (inventoryManagerIndex >= inventoryManagerList.size()) throw new IllegalStateException("No more InventoryManager objects available");
        return inventoryManagerList.get(inventoryManagerIndex++);
    }
    public RestockSuggestion getNextRestockSuggestion() {
        if (restockSuggestionIndex >= restockSuggestionList.size()) throw new IllegalStateException("No more RestockSuggestion objects available");
        return restockSuggestionList.get(restockSuggestionIndex++);
    }
    public TaskScheduler getNextTaskScheduler() {
        if (taskSchedulerIndex >= taskSchedulerList.size()) throw new IllegalStateException("No more TaskScheduler objects available");
        return taskSchedulerList.get(taskSchedulerIndex++);
    }
    public SupplierService getNextSupplierService() {
        if (supplierServiceIndex >= supplierServiceList.size()) throw new IllegalStateException("No more SupplierService objects available");
        return supplierServiceList.get(supplierServiceIndex++);
    }
    public PurchaseOrder getNextPurchaseOrder() {
        if (purchaseOrderIndex >= purchaseOrderList.size()) throw new IllegalStateException("No more PurchaseOrder objects available");
        return purchaseOrderList.get(purchaseOrderIndex++);
    }
    public Invoice getNextInvoice() {
        if (invoiceIndex >= invoiceList.size()) throw new IllegalStateException("No more Invoice objects available");
        return invoiceList.get(invoiceIndex++);
    }
    public Invoice getNextSalesRecord() {
        if (salesRecordIndex >= salesRecords.size()) throw new IllegalStateException("No more SalesRecord objects available");
        return salesRecords.get(salesRecordIndex++);
    }

    public Customer getCustomerByName(String name) {
        if (name == null) throw new IllegalArgumentException("Name cannot be null");
        for (Customer profile : customerProfileList) {
            if (name.equals(profile.getName())) return profile;
        }
        return null;
    }

    public Invoice getInvoiceById(UUID invoiceId) {
        if (invoiceId == null) throw new IllegalArgumentException("Invoice ID cannot be null");
        for (Invoice invoice : invoiceList) {
            if (invoiceId.equals(invoice.getInvoiceId())) return invoice;
        }
        return null;
    }

    public void addChef(Chef chef) {
        if (chef == null) throw new IllegalArgumentException("Chef cannot be null");
        chefList.add(chef);
    }
    public void addTask(Task task) {
        if (task == null) throw new IllegalArgumentException("Task cannot be null");
        taskList.add(task);
    }
    public void addMeal(Meal meal) {
        if (meal == null) throw new IllegalArgumentException("Meal cannot be null");
        mealList.add(meal);
    }
    public void addOrder(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        orderList.add(order);
    }
    public void addCustomer(Customer profile) {
        if (profile == null) throw new IllegalArgumentException("CustomerProfile cannot be null");
        customerProfileList.add(profile);
    }
    public void addIngredient(Ingredients ingredient) {
        if (ingredient == null) throw new IllegalArgumentException("Ingredient cannot be null");
        ingredientList.add(ingredient);
    }
    public void addCustomerOrderService(CustomerOrderService service) {
        if (service == null) throw new IllegalArgumentException("CustomerOrderService cannot be null");
        customerOrderServiceList.add(service);
    }
    public void addIngredientService(IngredientService service) {
        if (service == null) throw new IllegalArgumentException("IngredientService cannot be null");
        ingredientServiceList.add(service);
    }
    public void addInventoryManager(InventoryManager manager) {
        if (manager == null) throw new IllegalArgumentException("InventoryManager cannot be null");
        inventoryManagerList.add(manager);
    }
    public void addRestockSuggestion(RestockSuggestion suggestion) {
        if (suggestion == null) throw new IllegalArgumentException("RestockSuggestion cannot be null");
        restockSuggestionList.add(suggestion);
    }
    public void addTaskScheduler(TaskScheduler scheduler) {
        if (scheduler == null) throw new IllegalArgumentException("TaskScheduler cannot be null");
        taskSchedulerList.add(scheduler);
    }
    public void addSupplierService(SupplierService service) {
        if (service == null) throw new IllegalArgumentException("SupplierService cannot be null");
        supplierServiceList.add(service);
    }
    public void addPurchaseOrder(PurchaseOrder order) {
        if (order == null) throw new IllegalArgumentException("PurchaseOrder cannot be null");
        purchaseOrderList.add(order);
    }
    public void addInvoice(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("Invoice cannot be null");
        invoiceList.add(invoice);
        addSalesRecord(invoice);
    }
    public void addSalesRecord(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("Invoice cannot be null");
        salesRecords.add(invoice);
    }

    public void resetLists() {
        chefIndex = 0;
        taskIndex = 0;
        mealIndex = 0;
        orderIndex = 0;
        customerProfileIndex = 0;
        ingredientIndex = 0;
        customerOrderServiceIndex = 0;
        ingredientServiceIndex = 0;
        inventoryManagerIndex = 0;
        restockSuggestionIndex = 0;
        taskSchedulerIndex = 0;
        supplierServiceIndex = 0;
        purchaseOrderIndex = 0;
        invoiceIndex = 0;
        salesRecordIndex = 0;
    }

    public void assignTask(Chef chef, Task task) {
        if (chef == null || task == null) throw new IllegalArgumentException("Chef or Task cannot be null");
        chef.getAssignedTasks().add(task);
        System.out.println("Task assigned to " + chef.getName() + ": " + task.getName());
    }

    public String createCustomMeal(CustomerOrderService service, List<Ingredients> selectedIngredients) {
        if (service == null || selectedIngredients == null) throw new IllegalArgumentException("Service or ingredients cannot be null");
        for (Ingredients ingredient : selectedIngredients) {
            String availabilityAlert = getIngredientAvailabilityAlert(this, ingredient.getName());
            if (!availabilityAlert.isEmpty()) {
                return availabilityAlert;
            }
        }
        Meal customMeal = new Meal(selectedIngredients);
        String dietaryPreference = service.getDietaryPreference();
        if (!isMealCompatibleWithDietaryPreference(customMeal, dietaryPreference)) {
            for (Ingredients ingredient : selectedIngredients) {
                String compatibilityAlert = getIngredientCompatibilityAlert(this, ingredient.getName(), dietaryPreference);
                if (!compatibilityAlert.isEmpty()) {
                    return compatibilityAlert;
                }
            }
        }
        addMealToOrder(service, customMeal);
        return "Custom meal created successfully.";
    }

    public String addMealToOrder(CustomerOrderService service, Meal meal) {
        if (service == null || meal == null) throw new IllegalArgumentException("Service or meal cannot be null");
        service.getOrder().getMeals().add(meal);
        return "Meal added to order successfully.";
    }

    public List<String> suggestMeals(Customer profile) {
        if (profile == null) throw new IllegalArgumentException("CustomerProfile cannot be null");
        List<String> suggestedMeals = new ArrayList<>();
        if (profile.getDietaryPreference().equalsIgnoreCase("Vegan")) {
            suggestedMeals.add("Vegan Salad");
            suggestedMeals.add("Tofu Stir Fry");
            suggestedMeals.add("Grilled Vegetables");
        }
        String allergy = profile.getAllergy();
        if (allergy != null && !allergy.isEmpty()) {
            suggestedMeals.removeIf(meal -> meal.toLowerCase().contains(allergy.toLowerCase()));
        }
        if (profile.getDietaryPreference().equalsIgnoreCase("Vegan")) {
            suggestedMeals.removeIf(meal -> meal.toLowerCase().contains("cheese") ||
                    meal.toLowerCase().contains("milk") ||
                    meal.toLowerCase().contains("chicken") ||
                    meal.toLowerCase().contains("meat"));
        }
        profile.getSuggestedMeals().clear();
        profile.getSuggestedMeals().addAll(suggestedMeals);
        return suggestedMeals;
    }

    public String reorderMeal(Customer profile, String meal) {
        if (profile == null || meal == null) throw new IllegalArgumentException("Profile or meal cannot be null");
        return profile.getPastOrders().contains(meal) ? meal : null;
    }

    public String alertForIncompatibleIngredient(Customer profile, String ingredient) {
        if (profile == null || ingredient == null) throw new IllegalArgumentException("Profile or ingredient cannot be null");
        if (profile.getDietaryPreference().equalsIgnoreCase("Vegan") && ingredient.equalsIgnoreCase("cheese")) {
            System.out.println("Alert: Cheese is not compatible with Vegan dietary preference.");
            return "Cheese is not compatible with your dietary preference.";
        }
        return "Ingredient is compatible.";
    }

    public String analyzeMealPreferences(Customer profile) {
        if (profile == null) throw new IllegalArgumentException("CustomerProfile cannot be null");
        if (profile.getDietaryPreference().equalsIgnoreCase("Vegan")) {
            return "Vegan preference trend detected";
        }
        return "Other preferences trend detected";
    }

    public boolean suggestServiceImprovements(Customer profile) {
        if (profile == null) throw new IllegalArgumentException("CustomerProfile cannot be null");
        return profile.getDietaryPreference().equalsIgnoreCase("Vegan");
    }

    public void addPastOrder(Customer profile, String meal) {
        if (profile == null || meal == null) throw new IllegalArgumentException("Profile or meal cannot be null");
        profile.getPastOrders().add(meal);
    }

    public boolean isIngredientAvailable(Mainn app, String ingredientName) {
        if (app == null || ingredientName == null) throw new IllegalArgumentException("Service or ingredientName cannot be null");
        for (Ingredients ingredient : app.availableIngredients) {
            if (ingredient.getName().equalsIgnoreCase(ingredientName) && ingredient.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public String getIngredientAvailabilityAlert(Mainn app, String ingredientName) {
        if (app == null || ingredientName == null) throw new IllegalArgumentException("Service or ingredientName cannot be null");
        if (!isIngredientAvailable(app, ingredientName)) {
            return ingredientName + " is currently unavailable. Please choose a different ingredient.";
        }
        return "";
    }

    public boolean isAvailable(Mainn app, String ingredient) {
        if (app == null || ingredient == null) throw new IllegalArgumentException("Service or ingredient cannot be null");
        return app.availableIngredients.stream()
                .anyMatch(i -> i.getName().equalsIgnoreCase(ingredient) && i.isAvailable());
    }

    public String getIngredientCompatibilityAlert(Mainn app, String ingredientName, String dietaryPreference) {
        if (app == null || ingredientName == null || dietaryPreference == null) {
            throw new IllegalArgumentException("Service, ingredientName, or dietaryPreference cannot be null");
        }
        if ("Vegan".equalsIgnoreCase(dietaryPreference)) {
            if ("Cheese".equalsIgnoreCase(ingredientName) || "Milk".equalsIgnoreCase(ingredientName) ||
                    "Egg".equalsIgnoreCase(ingredientName) || "Peanut Butter".equalsIgnoreCase(ingredientName)) {
                System.out.println("Alert for " + ingredientName + ": " + ingredientName + " is not compatible with your dietary preference.");
                return ingredientName + " is not compatible with your dietary preference. Please choose a different ingredient.";
            }
        }
        return "";
    }

    public List<Ingredients> getAvailableIngredients() {
        return availableIngredients;
    }

    public void setAvailableIngredients(List<Ingredients> ingredients) {
        if (ingredients == null) throw new IllegalArgumentException("Ingredients list cannot be null");
        this.availableIngredients.clear();
        this.availableIngredients.addAll(ingredients);
    }

    public void addIngredient(InventoryManager inventoryManager, String ingredient, int quantity) {
        if (inventoryManager == null || ingredient == null) throw new IllegalArgumentException("Manager or ingredient cannot be null");
        if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
        inventoryManager.getInventory().put(ingredient, quantity);
    }

    public boolean isLowStock(InventoryManager manager, String ingredient) {
        if (manager == null || ingredient == null) throw new IllegalArgumentException("Manager or ingredient cannot be null");
        return manager.getInventory().getOrDefault(ingredient, 0) <= criticalStockLevel;
    }

    public List<String> getRestockSuggestions(RestockSuggestion suggestion) {
        if (suggestion == null) throw new IllegalArgumentException("RestockSuggestion cannot be null");
        List<String> suggestions = new ArrayList<>();
        InventoryManager manager = suggestion.getInventoryManager();
        if (manager == null) throw new IllegalStateException("InventoryManager not set in RestockSuggestion");
        for (String ingredient : manager.getInventory().keySet()) {
            if (isLowStock(manager, ingredient)) {
                suggestions.add(ingredient);
            }
        }
        return suggestions;
    }

    public List<String> checkLowStockIngredients(InventoryManager manager) {
        if (manager == null) throw new IllegalArgumentException("InventoryManager cannot be null");
        List<String> lowStockIngredients = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : manager.getInventory().entrySet()) {
            if (entry.getValue() <= criticalStockLevel) {
                lowStockIngredients.add(entry.getKey());
            }
        }
        return lowStockIngredients;
    }

    public String generateLowStockAlert(InventoryManager manager) {
        if (manager == null) throw new IllegalArgumentException("InventoryManager cannot be null");
        List<String> lowStockIngredients = checkLowStockIngredients(manager);
        if (lowStockIngredients.isEmpty()) {
            return "No low stock ingredients detected.";
        }
        StringBuilder alert = new StringBuilder("Warning: Low stock for the following ingredients (below " + criticalStockLevel + " units):\n");
        for (String ingredient : lowStockIngredients) {
            alert.append("- ").append(ingredient).append(" (").append(manager.getInventory().get(ingredient)).append(" units)\n");
        }
        alert.insert(0, "The following ingredients are low in stock:\n");
        return alert.toString();
    }

    public void addChef(TaskScheduler scheduler, Chef chef) {
        if (scheduler == null || chef == null) throw new IllegalArgumentException("Scheduler or chef cannot be null");
        scheduler.addChef(chef);
    }

    public void assignTask(TaskScheduler scheduler, Task task) {
        if (scheduler == null || task == null) throw new IllegalArgumentException("Scheduler or task cannot be null");
        System.out.println("Assigning task: " + task.getName() + ", Required Expertise: " + task.getRequiredExpertise());
        for (Chef chef : scheduler.getChefs()) {
            System.out.println("Before - Chef: " + chef.getName() + ", Expertise: " + chef.getExpertise() + ", Tasks: " + chef.getAssignedTasks().size());
        }
        Chef leastBusyChef = null;
        for (Chef chef : scheduler.getChefs()) {
            if (chef.getExpertise().equals(task.getRequiredExpertise())) {
                if (leastBusyChef == null || chef.getAssignedTasks().size() < leastBusyChef.getAssignedTasks().size()) {
                    leastBusyChef = chef;
                }
            }
        }
        if (leastBusyChef == null) {
            int minTasks = Integer.MAX_VALUE;
            for (Chef chef : scheduler.getChefs()) {
                int taskCount = chef.getAssignedTasks().size();
                if (taskCount < minTasks) {
                    minTasks = taskCount;
                    leastBusyChef = chef;
                }
            }
        }
        if (leastBusyChef != null) {
            assignTask(leastBusyChef, task);
        }
        System.out.println("Task assigned to: " + (leastBusyChef != null ? leastBusyChef.getName() : "None"));
    }

    public String getAssignedChef(TaskScheduler scheduler, Task task) {
        if (scheduler == null || task == null) throw new IllegalArgumentException("Scheduler or task cannot be null");
        for (Chef chef : scheduler.getChefs()) {
            if (chef.getAssignedTasks().contains(task)) {
                return chef.getName();
            }
        }
        return null;
    }

    public Chef getChefByName(TaskScheduler scheduler, String name) {
        if (scheduler == null || name == null) throw new IllegalArgumentException("Scheduler or name cannot be null");
        for (Chef chef : scheduler.getChefs()) {
            if (chef.getName().equals(name)) {
                return chef;
            }
        }
        return null;
    }

    public String assignTaskToChef(TaskScheduler scheduler, Task task, String chefName) {
        if (scheduler == null || task == null || chefName == null) {
            throw new IllegalArgumentException("Scheduler, task, or chefName cannot be null");
        }
        Chef chef = getChefByName(scheduler, chefName);
        if (chef != null) {
            assignTask(chef, task);
            return "You have been assigned a new task: " + task.getName();
        }
        return "Chef not found!";
    }

    public String getSubstitutionAlert(String original, String substitute) {
        if (original == null || substitute == null) throw new IllegalArgumentException("Original or substitute cannot be null");
        return "Alert: Substitution made - " + original + " was replaced with " + substitute;
    }

    public boolean isMealCompatibleWithDietaryPreference(Meal meal, String dietaryPreference) {
        if (meal == null || dietaryPreference == null) return true;
        for (Ingredients ingredient : meal.getIngredients()) {
            String name = ingredient.getName().toLowerCase();
            if ("vegan".equalsIgnoreCase(dietaryPreference) &&
                    (name.equals("cheese") || name.equals("milk") || name.equals("egg") || name.equals("peanut butter"))) {
                System.out.println("Alert for " + ingredient.getName() + ": " + ingredient.getName() + " is not compatible with your dietary preference.");
                return false;
            }
        }
        return true;
    }

    public List<Map<String, String>> fetchSupplierPrices(String ingredient) {
        List<Map<String, String>> prices = new ArrayList<>();
        for (SupplierService supplier : supplierServiceList) {
            Double price = supplier.getPrices().get(ingredient);
            if (price != null) {
                Map<String, String> priceEntry = new HashMap<>();
                priceEntry.put("Supplier", supplier.getName());
                priceEntry.put("Ingredient", ingredient);
                priceEntry.put("PricePerUnit", String.valueOf(price));
                prices.add(priceEntry);
            }
        }
        return prices;
    }

    public Map<String, Object> selectCheapestSupplier(String ingredient) {
        String cheapestSupplier = null;
        Double lowestPrice = null;
        for (SupplierService supplier : supplierServiceList) {
            Double price = supplier.getPrices().get(ingredient);
            if (price != null && (lowestPrice == null || price < lowestPrice)) {
                cheapestSupplier = supplier.getName();
                lowestPrice = price;
            }
        }
        if (cheapestSupplier == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("supplier", cheapestSupplier);
        result.put("price", lowestPrice);
        return result;
    }

    public PurchaseOrder checkInventoryAndOrder(InventoryManager manager) {
        for (String ingredient : manager.getInventory().keySet()) {
            if (isLowStock(manager, ingredient)) {
                Map<String, Object> supplierInfo = selectCheapestSupplier(ingredient);
                if (supplierInfo != null) {
                    String supplier = (String) supplierInfo.get("supplier");
                    double price = (Double) supplierInfo.get("price");
                    PurchaseOrder order = new PurchaseOrder(supplier, ingredient, 50, price);
                    addPurchaseOrder(order);
                    return order;
                }
            }
        }
        return null;
    }

    public String notifyManager(PurchaseOrder order) {
        if (order == null) return "";
        return String.format("Purchase order created for %s: %d units from %s at $%.2f per unit",
                order.getIngredient(), order.getQuantity(), order.getSupplierName(), order.getPricePerUnit());
    }

    public double calculateInvoiceTotalPrice(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("Invoice cannot be null");
        double total = 0.0;
        for (Meal meal : invoice.getItems()) {
            for (Ingredients ingredient : meal.getIngredients()) {
                total += 5.0;
            }
        }
        return total;
    }

    public void markInvoiceAsSent(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("Invoice cannot be null");
        invoice.setSent(true);
    }

    public Invoice createInvoice(Customer customer, Order order) {
        if (customer == null || order == null) throw new IllegalArgumentException("Customer or order cannot be null");
        double totalPrice = calculateInvoiceTotalPrice(new Invoice(customer, order, 0.0));
        Invoice invoice = new Invoice(customer, order, totalPrice);
        addInvoice(invoice);
        return invoice;
    }

    public FinancialReportGenerator generateFinancialReport() {
        double totalRevenue = 0.0;
        for (Invoice invoice : salesRecords) {
            totalRevenue += invoice.getTotalPrice();
        }
        return new FinancialReportGenerator(totalRevenue, salesRecords);
    }

    public String sendDeliveryReminder(Customer customer, Order order) {
        if (customer == null || order == null) throw new IllegalArgumentException("Customer or order cannot be null");
        if (order.getDeliveryTime() == null) {
            return String.format("Dear %s, your meal delivery time is not scheduled.", customer.getName());
        }
        return String.format("Reminder: Dear %s, your meal delivery is scheduled for %s.",
                customer.getName(), order.getDeliveryTime());
    }

    public String notifyChefOfTask(TaskScheduler scheduler, Task task, String chefName) {
        if (scheduler == null || task == null || chefName == null) {
            throw new IllegalArgumentException("Scheduler, task, or chefName cannot be null");
        }
        String assignmentResult = assignTaskToChef(scheduler, task, chefName);
        if (assignmentResult.startsWith("Chef not found")) {
            return assignmentResult;
        }
        return String.format("Notification: %s, you have a new cooking task: %s.", chefName, task.getName());
    }

    public void addPriceToSupplier(SupplierService supplier, String ingredient, double price) {
        if (supplier == null || ingredient == null) throw new IllegalArgumentException("Supplier or ingredient cannot be null");
        supplier.getPrices().put(ingredient, price);
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList != null ? new ArrayList<>(recipeList) : new ArrayList<>();
    }
}


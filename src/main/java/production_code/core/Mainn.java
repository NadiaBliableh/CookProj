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

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Mainn {
    private List<User> users;
    private List<Customer> customers;
    private List<Chef> chefs;
    private List<TaskScheduler> taskSchedulers;
    private List<InventoryManager> inventoryManagers;
    private List<SupplierService> supplierServices;
    private List<Ingredients> availableIngredients;
    private List<Task> tasks;
    private List<Order> orders;
    private List<Invoice> invoices;
    private int criticalStockLevel;
    private String currentUserRole;
    private List<CustomerOrderService> customerOrderServices;

    public Mainn() {
        users = new ArrayList<>();
        customers = new ArrayList<>();
        chefs = new ArrayList<>();
        taskSchedulers = new ArrayList<>();
        inventoryManagers = new ArrayList<>();
        supplierServices = new ArrayList<>();
        availableIngredients = new ArrayList<>();
        tasks = new ArrayList<>();
        orders = new ArrayList<>();
        invoices = new ArrayList<>();
        customerOrderServices = new ArrayList<>();
        criticalStockLevel = 5;
        currentUserRole = null;
    }

    public String registerUser(String username, String password, String role) {
        if (username == null || password == null || role == null || username.trim().isEmpty()) {
            return "Invalid input!";
        }
        if (users.stream().anyMatch(u -> u.getUsername().equals(username))) {
            return "Username already exists!";
        }
        users.add(new User(username, password, role));
        return "User registered successfully.";
    }

    public String login(String username, String password) {
        if (username == null || password == null) {
            return "Invalid input!";
        }
        User user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        if (user == null) {
            return "Invalid username or password!";
        }
        currentUserRole = user.getRole();
        return "Login successful! Role: " + currentUserRole;
    }

    public String getCurrentUserRole() {
        return currentUserRole != null ? currentUserRole : "No user logged in";
    }

    public void logout() {
        currentUserRole = null;
    }

    public void addCustomer(Customer customer) {
        if (customer != null && !customers.contains(customer)) {
            customers.add(customer);
        }
    }

    public Customer getCustomerByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        return customers.stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void addPastOrder(Customer customer, String order) {
        if (customer != null && order != null) {
            customer.addPastOrder(order);
        }
    }

    public void addChef(Chef chef) {
        if (chef != null && !chefs.contains(chef)) {
            chefs.add(chef);
        }
    }

    public void addTaskScheduler(TaskScheduler scheduler) {
        if (scheduler != null && !taskSchedulers.contains(scheduler)) {
            taskSchedulers.add(scheduler);
        }
    }

    public void addChef(TaskScheduler scheduler, Chef chef) {
        if (scheduler != null && chef != null) {
            scheduler.addChef(chef);
        }
    }

    public List<TaskScheduler> getTaskSchedulerList() {
        return new ArrayList<>(taskSchedulers);
    }

    public Chef getChefByName(TaskScheduler scheduler, String name) {
        if (scheduler == null || name == null || name.trim().isEmpty()) {
            return null;
        }
        return scheduler.getChefs().stream()
                .filter(c -> c.getName() != null && c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public void addInventoryManager(InventoryManager manager) {
        if (manager != null && !inventoryManagers.contains(manager)) {
            inventoryManagers.add(manager);
        }
    }

    public List<InventoryManager> getInventoryManagerList() {
        return new ArrayList<>(inventoryManagers);
    }

    public void addIngredient(InventoryManager manager, String ingredient, int quantity) {
        if (manager != null && ingredient != null && quantity >= 0) {
            manager.addIngredient(ingredient, quantity);
        }
    }

    public void setCriticalStockLevel(int level) {
        if (level >= 0) {
            this.criticalStockLevel = level;
        }
    }

    public void addSupplierService(SupplierService supplier) {
        if (supplier != null && !supplierServices.contains(supplier)) {
            supplierServices.add(supplier);
        }
    }

    public void addPriceToSupplier(SupplierService supplier, String ingredient, double price) {
        if (supplier != null && ingredient != null && price >= 0) {
            supplier.addPrice(ingredient, price);
        }
    }

    public List<SupplierService> getSupplierServiceList() {
        return new ArrayList<>(supplierServices);
    }

    public void setAvailableIngredients(List<Ingredients> ingredients) {
        if (ingredients != null) {
            this.availableIngredients = new ArrayList<>(ingredients);
        }
    }

    public List<Ingredients> getAvailableIngredients() {
        return new ArrayList<>(availableIngredients);
    }

    public void setAvailableIngredientsForService(SupplierService service, List<Ingredients> ingredients) {
        if (service != null && ingredients != null) {
            service.setAvailableIngredients(ingredients);
        }
    }

    public void setAvailableIngredientsForService(IngredientService service, List<Ingredients> ingredients) {
        if (service != null && ingredients != null) {
            this.availableIngredients = new ArrayList<>(ingredients);
        }
    }

    public String createCustomMeal(CustomerOrderService service, List<Ingredients> ingredients) {
        if (service != null && ingredients != null && !ingredients.isEmpty()) {
            Customer customer = service.getCustomer();
            if (customer != null) {
                String dietaryPref = customer.getDietaryPreference().toLowerCase();
                for (Ingredients ing : ingredients) {
                    if (dietaryPref.equals("vegan") && ing.getName().toLowerCase().contains("meat")) {
                        return ing.getName() + " is not compatible with your dietary preference. Please choose a different ingredient.";
                    }
                    if (!ing.isAvailable()) {
                        return ing.getName() + " is currently unavailable. Please choose a different ingredient.";
                    }
                }
            }
            return "Custom meal created successfully.";
        }
        return "Failed to create custom meal!";
    }

    public String addMealToOrder(CustomerOrderService service, Meal meal) {
        if (service != null && meal != null) {
            Customer customer = service.getCustomer();
            if (customer != null) {
                Order newOrder = new Order(customer, List.of(meal));
                orders.add(newOrder);
                service.setOrder(newOrder);
                return "Meal added to order successfully.";
            }
        }
        return "Failed to add meal to order!";
    }

    public String reorderMeal(Customer customer, String mealName) {
        if (customer != null && mealName != null && !mealName.trim().isEmpty()) {
            List<String> pastOrders = customer.getPastOrders();
            if (pastOrders != null && pastOrders.contains(mealName)) {
                addPastOrder(customer, mealName);
                Order order = new Order(customer, List.of(new Meal(availableIngredients)));
                orders.add(order);
                return mealName;
            }
        }
        return null;
    }

    public List<Order> getCustomerOrders(Customer customer) {
        if (customer == null) {
            return new ArrayList<>();
        }
        return orders.stream()
                .filter(o -> o.getCustomer() != null && o.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    public String sendDeliveryReminder(Customer customer, Order order) {
        if (customer != null && order != null) {
            return "Dear " + customer.getName() + ", your meal delivery is scheduled.";
        }
        return "Dear " + (customer != null ? customer.getName() : "Customer") + ", your meal delivery time is not scheduled.";
    }

    public void addTask(Task task) {
        if (task != null && !tasks.contains(task)) {
            tasks.add(task);
        }
    }

    public Task getLastTask() {
        return tasks.isEmpty() ? null : tasks.get(tasks.size() - 1);
    }

    public String assignTask(TaskScheduler scheduler, Task task) {
        if (scheduler != null && task != null) {
            Chef suitableChef = scheduler.getChefs().stream()
                    .filter(c -> c.getExpertise() != null && c.getExpertise().equalsIgnoreCase(task.getRequiredExpertise()))
                    .findFirst()
                    .orElse(null);
            if (suitableChef != null) {
                suitableChef.assignTask(task);
                return "Task assigned to " + suitableChef.getName();
            }
        }
        return null;
    }

    public String notifyChefOfTask(TaskScheduler scheduler, Task task, String chefName) {
        if (scheduler == null || task == null || chefName == null || chefName.trim().isEmpty()) {
            return "Invalid input!";
        }
        Chef chef = getChefByName(scheduler, chefName);
        if (chef != null) {
            chef.assignTask(task);
            return "Notification: " + chefName + ", you have a new cooking task: " + task.getName() + ".";
        }
        return "Chef not found!";
    }

    public FinancialReportGenerator generateFinancialReport() {
        FinancialReportGenerator report = new FinancialReportGenerator();
        report.setInvoices(new ArrayList<>(invoices));
        double totalRevenue = invoices.stream()
                .filter(Objects::nonNull)
                .mapToDouble(Invoice::getTotalPrice)
                .sum();
        report.setTotalRevenue(totalRevenue);
        report.setInvoiceCount(invoices.size());
        return report;
    }

    public String generateLowStockAlert(InventoryManager manager) {
        if (manager == null) {
            return null;
        }
        Map<String, Integer> inventory = manager.getInventory();
        if (inventory == null || inventory.isEmpty()) {
            return "No low stock ingredients detected.";
        }
        StringBuilder alert = new StringBuilder("The following ingredients are low in stock:\n");
        boolean hasLowStock = false;
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            if (entry.getValue() < criticalStockLevel) {
                alert.append("- ").append(entry.getKey()).append(" (").append(entry.getValue()).append(" units)\n");
                hasLowStock = true;
            }
        }
        return hasLowStock ? alert.toString() : "No low stock ingredients detected.";
    }

    public List<Map<String, String>> fetchSupplierPrices(String ingredient) {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            return new ArrayList<>();
        }
        List<Map<String, String>> prices = new ArrayList<>();
        for (SupplierService supplier : supplierServices) {
            Double price = supplier.getPrice(ingredient);
            if (price != null && price >= 0) {
                Map<String, String> priceInfo = new HashMap<>();
                priceInfo.put("Supplier", supplier.getName());
                priceInfo.put("PricePerUnit", String.valueOf(price));
                prices.add(priceInfo);
            }
        }
        return prices;
    }

    public Map<String, Object> selectCheapestSupplier(String ingredient) {
        if (ingredient == null || ingredient.trim().isEmpty()) {
            return null;
        }
        SupplierService cheapestSupplier = supplierServices.stream()
                .filter(s -> s.getPrice(ingredient) != null)
                .min(Comparator.comparingDouble(s -> s.getPrice(ingredient)))
                .orElse(null);
        if (cheapestSupplier == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("supplier", cheapestSupplier.getName());
        result.put("price", cheapestSupplier.getPrice(ingredient));
        return result;
    }

    public PurchaseOrder checkInventoryAndOrder(InventoryManager manager) {
        if (manager == null) {
            return null;
        }
        Map<String, Integer> inventory = manager.getInventory();
        if (inventory == null || inventory.isEmpty()) {
            return null;
        }
        Map<String, Integer> itemsToOrder = new HashMap<>();
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            if (entry.getValue() < criticalStockLevel) {
                int quantityToOrder = criticalStockLevel - entry.getValue() + 10;
                itemsToOrder.put(entry.getKey(), quantityToOrder);
            }
        }
        if (itemsToOrder.isEmpty()) {
            return null;
        }
        SupplierService bestSupplier = supplierServices.stream()
                .filter(s -> {
                    for (String ingredient : itemsToOrder.keySet()) {
                        if (s.getPrice(ingredient) == null) {
                            return false;
                        }
                    }
                    return true;
                })
                .min(Comparator.comparingDouble(s -> {
                    double total = 0.0;
                    for (Map.Entry<String, Integer> entry : itemsToOrder.entrySet()) {
                        Double price = s.getPrice(entry.getKey());
                        total += price != null ? price * entry.getValue() : Double.MAX_VALUE;
                    }
                    return total;
                }))
                .orElse(null);
        if (bestSupplier == null) {
            return null;
        }
        return new PurchaseOrder(bestSupplier.getName(), itemsToOrder, bestSupplier);
    }

    public String notifyManager(PurchaseOrder order) {
        if (order == null) {
            return null;
        }
        StringBuilder message = new StringBuilder("Purchase Order Generated:\n");
        message.append("Supplier: ").append(order.getSupplierName()).append("\n");
        message.append("Items to Order:\n");
        for (Map.Entry<String, Integer> entry : order.getItemsToOrder().entrySet()) {
            message.append("- ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" units\n");
        }
        message.append("Total Price: ").append(order.getTotalPrice());
        return message.toString();
    }

    public Invoice createInvoice(Customer customer, Order order) {
        if (customer != null && order != null) {
            Invoice invoice = new Invoice(order);
            invoices.add(invoice);
            orders.add(order);
            return invoice;
        }
        return null;
    }

    public Invoice getInvoiceById(UUID invoiceId) {
        if (invoiceId == null) {
            return null;
        }
        return invoices.stream()
                .filter(i -> i.getInvoiceId().equals(invoiceId))
                .findFirst()
                .orElse(null);
    }

    public void markInvoiceAsSent(Invoice invoice) {
        if (invoice != null) {
            invoice.markAsSent();
        }
    }

    public Order getLastOrder() {
        return orders.isEmpty() ? null : orders.get(orders.size() - 1);
    }

    public void resetLists() {
        users.clear();
        customers.clear();
        chefs.clear();
        taskSchedulers.clear();
        inventoryManagers.clear();
        supplierServices.clear();
        availableIngredients.clear();
        tasks.clear();
        orders.clear();
        invoices.clear();
        customerOrderServices.clear();
        currentUserRole = null;
    }

    public List<String> suggestMeals(Customer customer) {
        if (customer == null) return new ArrayList<>();
        List<String> suggestions = new ArrayList<>();
        String pref = customer.getDietaryPreference().toLowerCase();
        if ("vegan".equals(pref)) {
            suggestions.add("Vegan Salad");
            suggestions.add("Tofu Stir Fry");
        } else if ("vegetarian".equals(pref)) {
            suggestions.add("Veggie Pasta");
            suggestions.add("Grilled Vegetables");
        }
        customer.setSuggestedMeals(suggestions);
        return suggestions;
    }

    public String getSubstitutionAlert(String originalIngredient, String substitutedIngredient) {
        if (originalIngredient == null || substitutedIngredient == null) {
            return "Invalid substitution alert: ingredients cannot be null.";
        }
        return "Substitution Alert: " + originalIngredient + " has been replaced with " + substitutedIngredient + " due to dietary restrictions.";
    }

    public void addCustomerOrderService(CustomerOrderService service) {
        if (service != null && !customerOrderServices.contains(service)) {
            customerOrderServices.add(service);
        }
    }

    public void addOrder(Order order) {
        if (order != null && !orders.contains(order)) {
            orders.add(order);
        }
    }

    public String analyzeMealPreferences(Customer customer) {
        if (customer == null || customer.getPastOrders().isEmpty()) {
            return "No order history available to analyze trends.";
        }
        StringBuilder report = new StringBuilder("Order Trends Report for " + customer.getName() + ":\n");
        report.append("Dietary Preference: ").append(customer.getDietaryPreference()).append("\n");
        report.append("Past Orders: ").append(customer.getPastOrders()).append("\n");

        String trend = "Customer prefers " + customer.getDietaryPreference().toLowerCase() + " meals.";
        if (customer.getPastOrders().stream().anyMatch(order -> order.toLowerCase().contains("salad"))) {
            trend += " Notable trend: Preference for salads.";
        }
        report.append("Trend Analysis: ").append(trend).append("\n");
        return report.toString();
    }
}
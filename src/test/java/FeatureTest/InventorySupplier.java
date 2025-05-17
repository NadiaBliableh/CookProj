package FeatureTest;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import production_code.actors.InventoryManager;
import production_code.core.Ingredients;
import production_code.core.Mainn;
import production_code.core.SupplierService;
import production_code.customer_features.PurchaseOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InventorySupplier {
    private final Mainn app;
    private InventoryManager inventoryManager;
    private SupplierService supplierService;
    private PurchaseOrder lastPurchaseOrder;
    private String notification;

    public InventorySupplier() {
        this.app = new Mainn();
    }

    @Given("an inventory manager with the following inventory:")
    public void an_inventory_manager_with_the_following_inventory(DataTable dataTable) {
        inventoryManager = new InventoryManager();
        app.addInventoryManager(inventoryManager);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String ingredient = row.get("ingredient");
            int quantity = Integer.parseInt(row.get("quantity"));
            app.addIngredient(inventoryManager, ingredient, quantity);
        }
    }

    @Given("a supplier service with the following ingredients and prices:")
    public void a_supplier_service_with_the_following_ingredients_and_prices(DataTable dataTable) {
        supplierService = new SupplierService("Test Supplier");
        app.addSupplierService(supplierService);
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<Ingredients> supplierIngredients = new ArrayList<>();
        for (Map<String, String> row : rows) {
            String ingredient = row.get("ingredient");
            double price = Double.parseDouble(row.get("price"));
            app.addPriceToSupplier(supplierService, ingredient, price);
            supplierIngredients.add(new Ingredients(ingredient, true));
        }
        app.setAvailableIngredientsForService(supplierService, supplierIngredients);
    }

    @Given("the critical stock level is set to {int}")
    public void the_critical_stock_level_is_set_to(Integer criticalLevel) {
        app.setCriticalStockLevel(criticalLevel);
    }

    @When("the system checks inventory and places orders")
    public void the_system_checks_inventory_and_places_orders() {
        lastPurchaseOrder = app.checkInventoryAndOrder(inventoryManager);
        if (lastPurchaseOrder != null) {
            notification = app.notifyManager(lastPurchaseOrder);
        } else {
            notification = null;
        }
    }

    @Then("a purchase order should be generated for the following ingredients:")
    public void a_purchase_order_should_be_generated_for_the_following_ingredients(DataTable dataTable) {
        assertNotNull(lastPurchaseOrder, "Purchase order should be generated");
        List<Map<String, String>> expectedItems = dataTable.asMaps(String.class, String.class);
        Map<String, Integer> actualItems = lastPurchaseOrder.getItemsToOrder();
        for (Map<String, String> expectedItem : expectedItems) {
            String ingredient = expectedItem.get("ingredient");
            int quantity = Integer.parseInt(expectedItem.get("quantity"));
            assertTrue(actualItems.containsKey(ingredient), "Ingredient " + ingredient + " should be in the purchase order");
            assertEquals(quantity, (int) actualItems.get(ingredient), "Quantity for " + ingredient + " should match");
        }
    }

    @Then("the purchase order should be from supplier {string}")
    public void the_purchase_order_should_be_from_supplier(String supplierName) {
        assertNotNull(lastPurchaseOrder, "Purchase order should be generated");
        assertEquals(supplierName, lastPurchaseOrder.getSupplierName(), "Supplier name should match");
    }

    @Then("the manager should be notified with the purchase order details")
    public void the_manager_should_be_notified_with_the_purchase_order_details() {
        assertNotNull(notification, "Notification should be generated");
        assertTrue(notification.contains("Purchase Order Generated"), "Notification should contain purchase order details");
    }

    @Then("no purchase order should be generated")
    public void no_purchase_order_should_be_generated() {
        assertNull(lastPurchaseOrder, "No purchase order should be generated");
        assertNull(notification, "No notification should be generated");
    }
}
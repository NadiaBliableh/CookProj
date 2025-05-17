package FeatureTest;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.datatable.DataTable;
import production_code.actors.InventoryManager;
import production_code.core.Mainn;
import production_code.customer_features.PurchaseOrder;
import production_code.core.SupplierService;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SupplierIntegration {
    private Mainn app;
    private InventoryManager inventoryManager;
    private List<Map<String, String>> lastFetchedPrices;
    private String lastSelectedSupplier;
    private double lastSelectedPrice;
    private PurchaseOrder lastPurchaseOrder;
    private String lastNotification;

    public SupplierIntegration(Mainn app) {
        this.app = app;
        this.inventoryManager = new InventoryManager();
        this.app.addInventoryManager(inventoryManager);
    }

    @Given("the following inventory levels:")
    public void the_following_inventory_levels(DataTable dataTable) {
        List<Map<String, String>> inventoryData = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : inventoryData) {
            String ingredient = row.get("Ingredient");
            int quantity = Integer.parseInt(row.get("Quantity"));
            app.addIngredient(inventoryManager, ingredient, quantity);
        }
    }

    @Given("the following suppliers are available:")
    public void the_following_suppliers_are_available(DataTable dataTable) {
        List<Map<String, String>> supplierData = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> row : supplierData) {
            String supplierName = row.get("Supplier");
            String ingredient = row.get("Ingredient");
            double price = Double.parseDouble(row.get("PricePerUnit"));
            SupplierService supplier = null;
            for (SupplierService s : app.getSupplierServiceList()) {
                if (s.getName().equals(supplierName)) {
                    supplier = s;
                    break;
                }
            }
            if (supplier == null) {
                supplier = new SupplierService(supplierName);
                app.addSupplierService(supplier);
            }
            app.addPriceToSupplier(supplier, ingredient, price);
        }
    }

    @When("I request prices for {string} from all suppliers")
    public void i_request_prices_for_from_all_suppliers(String ingredient) {
        lastFetchedPrices = app.fetchSupplierPrices(ingredient);
    }

    @Then("the system should return the following prices:")
    public void the_system_should_return_the_following_prices(DataTable dataTable) {
        List<Map<String, String>> expectedPrices = dataTable.asMaps(String.class, String.class);
        assertEquals("Number of prices should match", expectedPrices.size(), lastFetchedPrices.size());
        for (Map<String, String> expected : expectedPrices) {
            boolean found = false;
            for (Map<String, String> actual : lastFetchedPrices) {
                if (actual.get("Supplier").equals(expected.get("Supplier")) &&
                        actual.get("Ingredient").equals(expected.get("Ingredient")) &&
                        Double.parseDouble(actual.get("PricePerUnit")) == Double.parseDouble(expected.get("PricePerUnit"))) {
                    found = true;
                    break;
                }
            }
            assertTrue("Expected price not found: " + expected, found);
        }
    }

    @When("I request the cheapest supplier for {string}")
    public void i_request_the_cheapest_supplier_for(String ingredient) {
        Map<String, Object> result = app.selectCheapestSupplier(ingredient);
        if (result != null) {
            lastSelectedSupplier = (String) result.get("supplier");
            lastSelectedPrice = (Double) result.get("price");
        }
    }

    @Then("the system should select {string} with price {double} for {string}")
    public void the_system_should_select_with_price_for(String expectedSupplier, Double expectedPrice, String ingredient) {
        assertEquals("Selected supplier should match", expectedSupplier, lastSelectedSupplier);
        assertEquals("Selected price should match", expectedPrice, lastSelectedPrice, 0.01);
    }

    @Given("the critical stock level is {int} units")
    public void the_critical_stock_level_is_units(Integer threshold) {
        app.setCriticalStockLevel(threshold);
    }

    @When("the system checks inventory levels")
    public void the_system_checks_inventory_levels() {
        lastPurchaseOrder = app.checkInventoryAndOrder(inventoryManager);
    }

    @Then("a purchase order should be generated for {string} with:")
    public void a_purchase_order_should_be_generated_for_with(String ingredient, DataTable dataTable) {
        List<Map<String, String>> expectedOrder = dataTable.asMaps(String.class, String.class);
        Map<String, String> expected = expectedOrder.get(0);
        assertNotNull("Purchase order should be generated", lastPurchaseOrder);
        assertEquals("Supplier should match", expected.get("Supplier"), lastPurchaseOrder.getSupplierName());
        assertEquals("Ingredient should match", ingredient, lastPurchaseOrder.getIngredient());
        assertEquals("Quantity should match", Integer.parseInt(expected.get("Quantity")), lastPurchaseOrder.getQuantity());
        assertEquals("Price should match", Double.parseDouble(expected.get("PricePerUnit")), lastPurchaseOrder.getPricePerUnit(), 0.01);
        lastNotification = app.notifyManager(lastPurchaseOrder);
    }

    @Then("the kitchen manager should receive a notification: {string}")
    public void the_kitchen_manager_should_receive_a_notification(String expectedNotification) {
        assertEquals("Notification should match", expectedNotification, lastNotification);
    }
}
package production_code.customer_features;


import production_code.actors.InventoryManager;

public class RestockSuggestion {
    private InventoryManager inventoryManager;

    public RestockSuggestion(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public InventoryManager getInventoryManager() { return inventoryManager; }
    public void setInventoryManager(InventoryManager inventoryManager) { this.inventoryManager = inventoryManager; }
}

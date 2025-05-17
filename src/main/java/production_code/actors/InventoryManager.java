package production_code.actors;

import java.util.HashMap;
import java.util.Map;


public class InventoryManager {
    private final Map<String, Integer> inventory;


    public InventoryManager() {
        this.inventory = new HashMap<>();
    }


    public Map<String, Integer> getInventory() {
        return new HashMap<>(inventory);
    }

    /**
     * Sets the inventory to a new map.
     *
     * @param inventory The new inventory map, must not be null.
     * @throws IllegalArgumentException if inventory is null.
     */
    public void setInventory(Map<String, Integer> inventory) {
        if (inventory == null) {
            throw new IllegalArgumentException("Inventory cannot be null");
        }
        this.inventory.clear();
        this.inventory.putAll(inventory);
    }

    public void addIngredient(String ingredient, int quantity) {
        if (ingredient != null && quantity >= 0) {
            inventory.put(ingredient, quantity);
        }
    }

    public void addOrUpdateIngredient(String name, int quantity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or empty");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        inventory.put(name.trim(), quantity);
    }

    @Override
    public String toString() {
        return "InventoryManager{inventory=" + inventory + "}";
    }
}
package production_code.customer_features;

import production_code.core.SupplierService;

import java.util.HashMap;
import java.util.Map;

public class PurchaseOrder {
    private final String supplierName;
    private final Map<String, Integer> itemsToOrder;
    private final double totalPrice;
    private final SupplierService supplierService;

    public PurchaseOrder(String supplierName, Map<String, Integer> itemsToOrder, SupplierService supplierService) {
        if (supplierName == null || itemsToOrder == null || supplierService == null) {
            throw new IllegalArgumentException("Supplier name, items to order, and supplier service cannot be null");
        }
        this.supplierName = supplierName;
        this.itemsToOrder = new HashMap<>(itemsToOrder);
        this.supplierService = supplierService;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        double price = 0.0;
        for (Map.Entry<String, Integer> entry : itemsToOrder.entrySet()) {
            String ingredient = entry.getKey();
            int quantity = entry.getValue();
            Double unitPrice = supplierService.getPrice(ingredient);
            if (unitPrice != null) {
                price += unitPrice * quantity;
            }
        }
        return price;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public Map<String, Integer> getItemsToOrder() {
        return new HashMap<>(itemsToOrder);
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getIngredient() {
        if (itemsToOrder.isEmpty()) {
            throw new IllegalStateException("No items in the purchase order");
        }
        return itemsToOrder.keySet().iterator().next();
    }

    public int getQuantity() {
        if (itemsToOrder.isEmpty()) {
            throw new IllegalStateException("No items in the purchase order");
        }
        return itemsToOrder.values().iterator().next();
    }

    public double getPricePerUnit() {
        if (itemsToOrder.isEmpty()) {
            throw new IllegalStateException("No items in the purchase order");
        }
        Map.Entry<String, Integer> entry = itemsToOrder.entrySet().iterator().next();
        String ingredient = entry.getKey();
        int quantity = entry.getValue();
        if (quantity == 0) {
            throw new IllegalStateException("Quantity cannot be zero");
        }
        Double unitPrice = supplierService.getPrice(ingredient);
        if (unitPrice == null) {
            throw new IllegalStateException("Price not found for ingredient: " + ingredient);
        }
        return unitPrice;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{supplierName='" + supplierName + "', itemsToOrder=" + itemsToOrder + ", totalPrice=" + totalPrice + "}";
    }
}
package production_code.core;

import production_code.customer_features.PurchaseOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SupplierService {
    private final String name;
    private final List<PurchaseOrder> purchaseOrders;
    private final Map<String, Double> prices;
    private List<Ingredients> availableIngredients;

    public SupplierService(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Supplier name cannot be null");
        }
        this.name = name;
        this.purchaseOrders = new ArrayList<>();
        this.prices = new HashMap<>();
        this.availableIngredients = new ArrayList<>();
    }

    public void sendPurchaseOrder(PurchaseOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Purchase order cannot be null");
        }
        purchaseOrders.add(order);
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return new ArrayList<>(purchaseOrders);
    }

    public String getName() {
        return name;
    }

    public void addPrice(String ingredient, double price) {
        if (ingredient != null && price >= 0) {
            prices.put(ingredient, price);
        }
    }

    public Double getPrice(String ingredient) {
        return prices.get(ingredient);
    }

    public void setAvailableIngredients(List<Ingredients> ingredients) {
        if (ingredients != null) {
            this.availableIngredients = new ArrayList<>(ingredients);
        }
    }

    public List<Ingredients> getAvailableIngredients() {
        return new ArrayList<>(availableIngredients);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupplierService that = (SupplierService) o;
        return name.equals(that.name) && purchaseOrders.equals(that.purchaseOrders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, purchaseOrders);
    }

    @Override
    public String toString() {
        return "SupplierService{name='" + name + "', purchaseOrders=" + purchaseOrders.size() + "}";
    }
}
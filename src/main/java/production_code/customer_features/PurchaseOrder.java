package production_code.customer_features;



public class PurchaseOrder {
    private String supplierName;
    private String ingredient;
    private int quantity;
    private double pricePerUnit;

    public PurchaseOrder(String supplierName, String ingredient, int quantity, double pricePerUnit) {
        this.supplierName = supplierName;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }
}

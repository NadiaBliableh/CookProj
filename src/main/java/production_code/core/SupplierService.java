package production_code.core;



import java.util.HashMap;
import java.util.Map;

public class SupplierService {
    private String name;
    private Map<String, Double> prices;

    public SupplierService() {
        this.name = "";
        this.prices = new HashMap<>();
    }

    public SupplierService(String name) {
        this.name = name;
        this.prices = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getPrices() {
        return prices;
    }

    public void setPrices(Map<String, Double> prices) {
        this.prices = prices;
    }
}

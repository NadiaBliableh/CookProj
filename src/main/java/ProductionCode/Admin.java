package ProductionCode;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class Admin {
    private String username;

    public Admin(String username) {
        this.username = username;
    }

    public boolean hasDatabaseAccess() {
        return true; // Assume admin has access
    }
    public List<Meal> retrieveOrderHistory() {
        return new ArrayList<>(Arrays.asList(new Meal("Burger"), new Meal("Salad")));
    }

    public TrendsReport analyzeTrends(List<Meal> orders) {
        return new TrendsReport("Trending Meals Report");
    }
}

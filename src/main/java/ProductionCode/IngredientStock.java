package ProductionCode;
import java.util.HashSet;
import java.util.Set;
public class IngredientStock {
    private static final Set<String> outOfStockIngredients = new HashSet<>();

    public static void markOutOfStock(String ingredient) {
        outOfStockIngredients.add(ingredient);
    }

    public static boolean isOutOfStock(String ingredient) {
        return outOfStockIngredients.contains(ingredient);
    }
}

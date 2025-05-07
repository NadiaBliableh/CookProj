package production_code.system_features;
import static production_code.core.Ingredients.getIngredients;
import java.util.List;
import java.util.Map;

public class SuggestAlternativeIngredients {
	public static List<String> suggestAlternativeIngredients(String ingredient) {
		Map<String, List<String>> ingredientsMap = getIngredients();
		if (ingredientsMap.containsKey(ingredient)) {
			return ingredientsMap.get(ingredient);
		}
		
		for (Map.Entry<String, List<String>> entry : ingredientsMap.entrySet()) {
			if (entry.getValue().contains(ingredient)) {
				return ingredientsMap.get(entry.getKey());
			}
		}
		return List.of("No alternatives found");
	}
}
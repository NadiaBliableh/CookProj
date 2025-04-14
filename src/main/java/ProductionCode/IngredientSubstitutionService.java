package ProductionCode;

import java.util.HashMap;
import java.util.Map;

public class IngredientSubstitutionService {

    private static final Map<String, String[]> substitutions = new HashMap<>();

    static {
        substitutions.put("milk", new String[]{"almond milk", "oat milk"});
        substitutions.put("eggs", new String[]{"flaxseed meal", "applesauce"});
        substitutions.put("butter", new String[]{"margarine", "coconut oil"});
    }

    public String suggestSubstitute(String ingredient) {
        if (substitutions.containsKey(ingredient)) {
            return substitutions.get(ingredient)[0]; // Suggest the first alternative
        }
        throw new RuntimeException("No substitute available for " + ingredient);
    }

    public boolean notifyChef(String original, String substitute) {
        System.out.println("Alert: Chef notified about substitution of " + original + " with " + substitute);
        return true; // Simulating chef notification success
    }

    public String approveOrModifySubstitution(String suggestedSubstitute) {
        boolean chefApproves = true; // Assume the chef approves in this case
        return chefApproves ? suggestedSubstitute : "Chef's choice of substitute";
    }
}

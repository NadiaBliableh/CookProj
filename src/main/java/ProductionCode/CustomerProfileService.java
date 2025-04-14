package ProductionCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerProfileService {
    private Map<String, CustomerProfile> customerProfiles = new HashMap<>();

    public void addCustomerProfile(String name, List<String> preferences, List<String> allergies) {
        customerProfiles.put(name, new CustomerProfile(name, preferences, allergies));
    }

    public CustomerProfile getCustomerProfile(String name) {
        return customerProfiles.get(name);
    }

    public void updateCustomerPreferences(String name, List<String> newPreferences) {
        CustomerProfile profile = customerProfiles.get(name);
        if (profile != null) {
            profile.updatePreferences(newPreferences);
        }
    }

    public void updateCustomerAllergies(String name, List<String> newAllergies) {
        CustomerProfile profile = customerProfiles.get(name);
        if (profile != null) {
            profile.updateAllergies(newAllergies);
        }
    }
}

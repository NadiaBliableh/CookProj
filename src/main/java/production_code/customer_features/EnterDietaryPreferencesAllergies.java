package production_code.customer_features;
import static production_code.files_manager.DataManager.*;
import production_code.actors.Customer;

public class EnterDietaryPreferencesAllergies {
	private String customerName;
	private String dietaryPreferences;
	private String allergies;
	
	public EnterDietaryPreferencesAllergies(String customerName, String dietaryPreferences, String allergies) {
		this.customerName = customerName;
		this.dietaryPreferences = dietaryPreferences;
		this.allergies = allergies;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	public String getDietaryPreferences() {
		return dietaryPreferences;
	}
	public String getAllergies() {
		return allergies;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public void setDietaryPreferences(String dietaryPreferences) {
		this.dietaryPreferences = dietaryPreferences;
	}
	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}
	
	public boolean addNewCell(){
		if(customerName == null || allergies == null || dietaryPreferences == null){
			return false;
		}
		
		boolean thereIs = thereIsCustomer(customerName);
		if(!thereIs){
			return false;
		}
		
		addNewDietaryPreference(dietaryPreferences, customerName);
		return addNewAllergy(allergies, customerName);
	}
	
	public void addNewDietaryPreference(String dietaryPreference, String customerName) {
		Customer customer = getCustomer(customerName);
		customer.addDietaryPreference(dietaryPreference);
	}
	
	public boolean addNewAllergy(String allergy, String customerName) {
		Customer customer = getCustomer(customerName);
		customer.addAllergy(allergy);
		return true;
	}
}

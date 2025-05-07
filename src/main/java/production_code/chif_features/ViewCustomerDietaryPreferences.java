package production_code.chif_features;
import production_code.actors.Customer;

public class ViewCustomerDietaryPreferences {
	public static boolean viewCustomerDietaryPreferences(Customer customer) {
		System.out.println("+--------------------------------+");
		System.out.println("| #  |   Dietary Preferences     |");
		System.out.println("+--------------------------------+");
		
		int count = 1;
		for (String diet : customer.getDietaryPreferences()) {
			System.out.printf("| %-2d | %-24s |\n", count, diet);
			count++;
		}
		System.out.println("+--------------------------------+");
		return true;
	}
}
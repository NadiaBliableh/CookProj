package production_code.kitchen_manager_features;
import static production_code.files_manager.DataManager.*;
import static production_code.files_manager.DataSaver.saveChefSpecialityToFile;
import static production_code.files_manager.DataSaver.saveSpecialtyMealsToFile;
import static production_code.files_manager.FilePaths.CHEF_FILE_NAME;
import static production_code.files_manager.FilePaths.SPECIALTY_MEALS_FILE_NAME;

import production_code.actors.Customer;
import production_code.core.Meal;
import production_code.core.Specialty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssignTasksToChefs {
	public static boolean printChefSpeciality(Map<Meal, Map<Specialty, List<Customer>>> chefSpeciality) {
		System.out.println("Chef Speciality Assignment:\n");
		for (Meal meal : chefSpeciality.keySet()) {
			Map<Specialty, List<Customer>> specialty = chefSpeciality.get(meal);
			System.out.println("Meal: " + meal.getMealName()+ " (" + meal.getIngredients() + ") - Specialty: " + specialty.keySet());
		}
		return true;
	}
	
	public static boolean assignTasksToChefs(
			Map<Meal, Map<Specialty, List<Customer>>> chefSpeciality,
			Map<Specialty, Map<Meal, List<Customer>>> specialtyMeals
	){
		System.out.println("Assigning tasks to chefs...\n");
		if(chefSpeciality == null || chefSpeciality.isEmpty()){
			System.out.println("No meals found in DataManager.");
			return true;
		}
		
		Set<Meal> chefSpecialityKeySet = chefSpeciality.keySet();
		for(Meal meal : chefSpecialityKeySet){
			Map<Specialty, List<Customer>> specialtyMap = chefSpeciality.get(meal);
			Set<Specialty> specialtyKey = specialtyMap.keySet();
			Specialty specialty = specialtyKey.iterator().next();
			
			Map<Meal, List<Customer>> meals = new HashMap<>();
			meals.put(meal, specialtyMap.get(specialty));
			if(thereIsSpecialtyMeals(specialty)){
				specialtyMeals.get(specialty).put(meal, meals.get(meal));
			} else {
				addSpecialtyMeals(specialty, meals);
			}
		}
		
		saveChefSpecialityToFile(getChefSpeciality(), CHEF_FILE_NAME);
		return saveSpecialtyMealsToFile(specialtyMeals, SPECIALTY_MEALS_FILE_NAME);
	}
	
	public static boolean printSpecialtyMeals(Map<Specialty, Map<Meal, List<Customer>>> specialtyMeals) {
		System.out.println("Specialty Meal Assignment:\n");
		for (Specialty specialty : specialtyMeals.keySet()) {
			System.out.println("Specialty: " + specialty);
			Map<Meal, List<Customer>> meals = specialtyMeals.get(specialty);
			for (Meal meal : meals.keySet()) {
				System.out.println("- " + meal.getMealName()+ " (" + meal.getIngredients() + ")");
			}
			System.out.println();
		}
		return true;
	}
	
}
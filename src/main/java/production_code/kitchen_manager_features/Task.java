package production_code.kitchen_manager_features;




public class Task {
	private String name;
	private String requiredExpertise;

	public Task(String name, String requiredExpertise) {
		this.name = name;
		this.requiredExpertise = requiredExpertise;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getRequiredExpertise() { return requiredExpertise; }
	public void setRequiredExpertise(String requiredExpertise) { this.requiredExpertise = requiredExpertise; }
}
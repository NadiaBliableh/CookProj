package production_code.kitchen_manager_features;

import java.util.Objects;

public class Task {
	private final String name;
	private final String requiredExpertise;

	public Task(String name) {
		this(name, "General");
	}

	public Task(String name, String requiredExpertise) {
		if (name == null || requiredExpertise == null) {
			throw new IllegalArgumentException("Name and expertise cannot be null");
		}
		this.name = name;
		this.requiredExpertise = requiredExpertise;
	}

	public String getName() {
		return name;
	}

	public String getRequiredExpertise() {
		return requiredExpertise;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Task task = (Task) o;
		return name.equals(task.name) && requiredExpertise.equals(task.requiredExpertise);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, requiredExpertise);
	}

	@Override
	public String toString() {
		return "Task{name='" + name + "', requiredExpertise='" + requiredExpertise + "'}";
	}
}
package production_code.core;

import java.util.Objects;

public class Ingredients {
	private final String name;
	private final boolean available;

	public Ingredients(String name, boolean available) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null");
		}
		this.name = name;
		this.available = available;
	}

	public String getName() {
		return name;
	}

	public boolean isAvailable() {
		return available;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Ingredients that = (Ingredients) o;
		return available == that.available && name.equals(that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, available);
	}

	@Override
	public String toString() {
		return "Ingredients{name='" + name + "', available=" + available + "}";
	}
}
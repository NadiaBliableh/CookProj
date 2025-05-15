package production_code.core;


public class Ingredients {
	private String name;
	private boolean isAvailable;

	public Ingredients(String name, boolean isAvailable) {
		this.name = name;
		this.isAvailable = isAvailable;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public boolean isAvailable() { return isAvailable; }
	public void setAvailable(boolean available) { this.isAvailable = available; }
}
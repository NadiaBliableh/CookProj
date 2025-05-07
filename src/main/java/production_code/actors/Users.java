package production_code.actors;

public interface Users {
	String getUsername();
	void setUsername(String username);
	String getPassword();
	void setPassword(String password);
	void login();
	boolean isLoggedIn();
	void logout();
}
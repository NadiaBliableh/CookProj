package ProductionCode;


public class User {
    private String username;
    private String password; // Should be hashed in a real-world app

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String inputPassword) {
        return this.password.equals(inputPassword);
    }
}

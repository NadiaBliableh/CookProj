package ProductionCode;



import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> userDatabase = new HashMap<>();
    private String loginMessage;

    public void registerUser(String username, String password) {
        userDatabase.put(username, new User(username, password));
    }

    public boolean login(String username, String password) {
        User user = userDatabase.get(username);
        if (user == null) {
            loginMessage = "User not found";
            return false;
        }
        if (!user.validatePassword(password)) {
            loginMessage = "Invalid credentials";
            return false;
        }
        loginMessage = "Login successful";
        return true;
    }

    public String getLoginMessage() {
        return loginMessage;
    }
}
package production_code.actors;

import java.util.Objects;

/**
 * Represents a User with a username, password, and role.
 */
public class User {
    private String username;
    private String password;
    private String role;


    public User(String username, String password, String role) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        this.username = username.trim();
        this.password = password.trim();
        this.role = role.trim();
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username.trim();
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password.trim();
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        this.role = role.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', role='" + role + "'}";
    }
}
package production_code.actors;

import production_code.core.Specialty;

public class Chef implements Users {
    private boolean loggedIn;
    private String username;
    private String password;
    private Specialty specialty;
    
    public Chef(String username, String password) {
        this.username = username;
        this.loggedIn = true;
        this.password = password;
    }
    
    public Chef() {}
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
    
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    
    public Specialty getSpecialty() {
        return specialty;
    }
    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    public void logout() {
        this.loggedIn = false;
    }
    public void login() {
        this.loggedIn = true;
    }
}
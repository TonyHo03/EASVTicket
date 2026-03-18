package dk.easv.easvticket.BE;

public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String role;

    public User(int id, String username, String email, String role) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;

    }

    public User(String username, String email, String role) {

        this.username = username;
        this.email = email;
        this.role = role;

    }

    public User(String username, String password, String email, String role) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;

    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }
    public String getEmail() {
        return this.email;
    }
    public String getRole() {
        return this.role;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.username;
    }

}

package dk.easv.easvticket.BE;

public class User {

    private int id;
    private String name;
    private String username;
    private String email;
    private String role;

    public User(int id, String name, String username, String email, String role) {

        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;

    }

    public User(String name, String username, String email, String role) {

        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;

    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
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

    public void setName(String name) {
        this.name = name;
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
        return this.name;
    }

}

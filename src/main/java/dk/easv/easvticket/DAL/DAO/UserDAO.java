package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.Interfaces.IUserDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDataAccess {

    private DBConnector dbConnector = new DBConnector();

    public UserDAO() throws Exception {

    }

    @Override
    public User createUser(User newUser) throws Exception {
        return null;
    }

    @Override
    public List<User> getUsers() throws Exception {

        List<User> users = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT UserId, Username, Email, Role FROM [User]");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int userId = rs.getInt("UserId");
                String username = rs.getString("Username");
                String email = rs.getString("Email");
                String role = rs.getString("Role");

                users.add(new User(userId, username, email, role));

            }

        }
        catch (SQLException e) {

            throw new Exception("Could not get users", e);

        }

        return users;

    }

    @Override
    public List<User> getUsersWithRole(Roles roles) throws Exception {

        List<User> users = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT UserId, Username, Email, Role FROM [User] WHERE Role = ?");
            ps.setString(1, roles.toString());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int userId = rs.getInt("UserId");
                String username = rs.getString("Username");
                String email = rs.getString("Email");
                String role = rs.getString("Role");

                users.add(new User(userId, username, email, role));

            }

        }
        catch (SQLException e) {

            throw new Exception("Could not get users", e);

        }

        return users;

    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public void deleteUser(int userID) throws Exception {

    }
}

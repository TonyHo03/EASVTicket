package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.Interfaces.IUserDataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDataAccess {

    private DBConnector dbConnector = new DBConnector();

    public UserDAO() throws Exception {

    }

    @Override
    public User createUser(User newUser) throws Exception {
        String sql = "INSERT INTO [User] (Username, Password, Email, Role) VALUES (?, ?, ?, ?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, newUser.getUsername());
            ps.setString(2, newUser.getPassword());
            ps.setString(3, newUser.getEmail());
            ps.setString(4, newUser.getRole());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newUser.setId(rs.getInt(1));
            }

            return newUser;

        } catch (SQLException e) {
            throw new Exception("Could not create user", e);
        }
    }

    @Override
    public List<User> getUsers() throws Exception {

        List<User> users = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT UserId, Username, Email, Role FROM [User] WHERE deleted_at IS NULL");

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

            PreparedStatement ps = connection.prepareStatement("SELECT UserId, Username, Email, Role FROM [User] WHERE Role = ? AND deleted_at IS NULL");
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
    public User getUserFromUsername(String username) throws Exception {

        String sql = "SELECT * FROM [User] WHERE Username = ?";

        try (Connection connection = dbConnector.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql))
        {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            User user = null;

            if (rs.next()) {

                user = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Email"), rs.getString("Role"));

            }

            return user;

        }
        catch (Exception e) {

            throw new Exception("Could not fetch user from username " + username, e);

        }

    }

    @Override
    public void updateUser(User user) throws Exception {
        String sql = "UPDATE [User] SET Username = ?, Password = ?, Email = ?, Role = ? WHERE UserId = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getRole());
            ps.setInt(5, user.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Could not update user", e);
        }
    }

    @Override
    public void deleteUser(int userID) throws Exception {
        String sql = "UPDATE [User] SET deleted_at = ? WHERE UserId = ?";
        String unassignCoordSQL = "DELETE FROM EventCoordinators WHERE user_id = ?";


        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             PreparedStatement ps2 = connection.prepareStatement(unassignCoordSQL)){

            ps.setObject(1, LocalDateTime.now());
            ps.setInt(2, userID);
            ps.executeUpdate();

            ps2.setInt(1, userID);
            ps2.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Could not delete user", e);
        }
    }
}

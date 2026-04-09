package dk.easv.easvticket.BLL;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.DAO.UserDAO;
import dk.easv.easvticket.DAL.Interfaces.IUserDataAccess;

import java.util.List;

public class UserManager {
    private IUserDataAccess userDataAccess;

    public UserManager() throws Exception {
        userDataAccess = new UserDAO();
    }

    public User createUser(User newUser) throws Exception {
        return userDataAccess.createUser(newUser);
    }

    public List<User> getUsers() throws Exception {
        return userDataAccess.getUsers();
    }

    public List<User> getUsersWithRole(Roles role) throws Exception {
        return userDataAccess.getUsersWithRole(role);
    }

    public User getUserFromUsername(String username) throws Exception {
        return userDataAccess.getUserFromUsername(username);
    }

    public void updateUser(User updatedUser) throws Exception {
        userDataAccess.updateUser(updatedUser);
    }

    public void deleteUser(User selectedUser) throws Exception {
        userDataAccess.deleteUser(selectedUser.getId());
    }
}

package dk.easv.easvticket.BLL;

import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.DAO.UserDAO;
import dk.easv.easvticket.DAL.Interfaces.IUserDataAccess;

import java.util.List;

public class UserManager {

    private IUserDataAccess userDAO = new UserDAO();;

    public UserManager() throws Exception {}

    public User createUser(User newUser) throws Exception {
        return userDAO.createUser(newUser);
    }

    public List<User> getUsers() throws Exception {
        return userDAO.getUsers();
    }

    public List<User> getUsersWithRole(Roles role) throws Exception {
        return userDAO.getUsersWithRole(role);
    }

    public void updateUser(User updatedUser) throws Exception {
        userDAO.updateUser(updatedUser);
    }

    public void deleteUser(User selectedUser) throws Exception {
        userDAO.deleteUser(selectedUser.getId());
    }
}

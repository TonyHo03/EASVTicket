package dk.easv.easvticket.BLL;

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

    public void updateUser(User updatedUser) throws Exception {
        userDataAccess.updateUser(updatedUser);
    }

    public void deleteUser(User selectedUser) throws Exception {
        userDataAccess.deleteUser(selectedUser.getId());
    }
}

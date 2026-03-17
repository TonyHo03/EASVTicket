package dk.easv.easvticket.BLL;

import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.DAO.UserDAO;
import dk.easv.easvticket.DAL.Interfaces.IUserDataAccess;

import java.util.List;

public class UserManager {

    private IUserDataAccess userDAO = new UserDAO();

    public UserManager() throws Exception {}

    public List<User> getUsers() throws Exception {

        return userDAO.getUsers();

    }

}

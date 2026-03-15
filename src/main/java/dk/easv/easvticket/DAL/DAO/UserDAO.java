package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.Interfaces.IUserDataAccess;

import java.util.List;

public class UserDAO implements IUserDataAccess {
    @Override
    public User createUser(User newUser) throws Exception {
        return null;
    }

    @Override
    public List<User> getUsers() throws Exception {
        return List.of();
    }

    @Override
    public void updateUser(User user) throws Exception {

    }

    @Override
    public void deleteUser(int userID) throws Exception {

    }
}

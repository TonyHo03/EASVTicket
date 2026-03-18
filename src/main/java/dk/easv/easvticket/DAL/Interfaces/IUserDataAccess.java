package dk.easv.easvticket.DAL.Interfaces;

import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.BE.Roles;

import java.util.List;

public interface IUserDataAccess {
    User createUser(User newUser) throws Exception;
    List<User> getUsers() throws Exception;
    List<User> getUsersWithRole(Roles role) throws Exception;
    void updateUser(User user) throws Exception;
    void deleteUser(int userID) throws Exception;
}

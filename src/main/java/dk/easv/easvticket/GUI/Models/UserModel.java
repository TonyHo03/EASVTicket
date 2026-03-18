package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Roles;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.Facade.TicketSystemFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class UserModel {

    private TicketSystemFacade facade = new TicketSystemFacade();

    private ObservableList<User> userObservableList;



    public UserModel() throws Exception {

        userObservableList = FXCollections.observableArrayList();
        userObservableList.setAll(facade.userManager.getUsers());
    }

    // User Management
    public ObservableList<User> getUsers() {

        return userObservableList;

    }
    public void updateUser(User updatedUser) throws Exception {
        facade.userManager.updateUser(updatedUser);
        // Update the local observable list so the table refreshes automatically
        int index = userObservableList.indexOf(updatedUser);
        if (index >= 0) {
            userObservableList.set(index, updatedUser);
        }
    }

    public List<User> getUsersWithRole(Roles role) throws Exception {

        return facade.userManager.getUsersWithRole(role);

    }

    public void addUser(User newUser) throws Exception {
        User createdUser = facade.userManager.createUser(newUser);
        userObservableList.add(createdUser);
    }
}

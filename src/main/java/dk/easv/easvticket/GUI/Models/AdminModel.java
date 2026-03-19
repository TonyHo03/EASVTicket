package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.Facade.TicketSystemFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminModel {

    private TicketSystemFacade facade = new TicketSystemFacade();

    private ObservableList<User> userObservableList;
    private ObservableList<Event> eventObservableList;

    public AdminModel() throws Exception {
        userObservableList = FXCollections.observableArrayList();
        userObservableList.setAll(facade.userManager.getUsers());
        eventObservableList = FXCollections.observableArrayList();
        eventObservableList.setAll(facade.eventManager.getEvents());
    }

    // User Management
    public void createUser(User newUser) throws Exception {
        User createdUser = facade.userManager.createUser(newUser);
        userObservableList.add(createdUser);
    }

    public ObservableList<User> getUsers() {
        return userObservableList;
    }

    public void updateUser(User updatedUser) throws Exception {
        facade.userManager.updateUser(updatedUser);
        int index = userObservableList.indexOf(updatedUser);
        if (index >= 0) {
            userObservableList.set(index, updatedUser);
        }
    }

    public void deleteUser(User selectedUser) throws Exception {
        facade.userManager.deleteUser(selectedUser);
        userObservableList.remove(selectedUser);
    }

    // Event Management
    public ObservableList<Event> getEvents() {
        return eventObservableList;
    }
}
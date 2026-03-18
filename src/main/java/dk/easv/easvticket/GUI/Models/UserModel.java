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

    private ObservableList<Event>  eventObservableList;

    public UserModel() throws Exception {

        userObservableList = FXCollections.observableArrayList();
        userObservableList.setAll(facade.userManager.getUsers());
        eventObservableList = FXCollections.observableArrayList();
        eventObservableList.setAll(facade.eventManager.getEvents());
    }

    // User Management
    public ObservableList<User> getUsers() {

        return userObservableList;

    }

    public List<User> getUsersWithRole(Roles role) throws Exception {

        return facade.userManager.getUsersWithRole(role);

    }

    // Event Management
    public ObservableList<Event> getEvents() {
        return eventObservableList;
    }

    public void deleteEvent (Event event) throws Exception {

        facade.eventManager.deleteEvent(event);
        eventObservableList.remove(event);
    }
}

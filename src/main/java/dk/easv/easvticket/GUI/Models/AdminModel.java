package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.Facade.TicketSystemFacade;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class AdminModel {

    private TicketSystemFacade facade = new TicketSystemFacade();

    private ObservableList<User> userObservableList;

    private ObservableList<Event>  eventObservableList;

    public AdminModel() throws Exception {

        userObservableList = FXCollections.observableArrayList();
        userObservableList.setAll(facade.userManager.getUsers());
        eventObservableList = FXCollections.observableArrayList();
        eventObservableList.setAll(facade.eventManager.getEvents());
    }

    // User Management
    public ObservableList<User> getUsers() {

        return userObservableList;

    }

    // Event Management
    public ObservableList<Event> getEvents() {
        return eventObservableList;
    }

}

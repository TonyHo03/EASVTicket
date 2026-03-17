package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.Facade.TicketSystemFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class AdminModel {

    private TicketSystemFacade facade = new TicketSystemFacade();

    private ObservableList<User> userObservableList;

    public AdminModel() throws Exception {

        userObservableList = FXCollections.observableArrayList();
        userObservableList.setAll(facade.userManager.getUsers());

    }

    // User Management
    public ObservableList<User> getUsers() {

        return userObservableList;

    }

    // Event Management

}

package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.Facade.TicketSystemFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EventModel {
    private TicketSystemFacade facade = new TicketSystemFacade();
    private ObservableList<Event> eventObservableList;

    public EventModel() throws Exception {
        eventObservableList = FXCollections.observableArrayList();
        eventObservableList.setAll(facade.eventManager.getEvents());
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

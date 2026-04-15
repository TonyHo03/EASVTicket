package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.Facade.ModelManagerFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventModel {
    private ModelManagerFacade facade = new ModelManagerFacade();
    private ObservableList<Event> eventObservableList;

    public EventModel() throws Exception {
        eventObservableList = FXCollections.observableArrayList();
        eventObservableList.setAll(facade.eventManager.getEvents());
    }

    // Event Management
    public void createEvent(Event newEvent) throws Exception{
        Event createdEvent  = facade.eventManager.createEvent(newEvent);
        eventObservableList.add(createdEvent);
    }

    public ObservableList<Event> getEvents() {
        return eventObservableList;
    }

    public void deleteEvent(Event event) throws Exception {
        facade.eventManager.deleteEvent(event); // This updates the Database
        eventObservableList.remove(event);      // This hides it from the UI instantly
    }

    public void updateEvent(Event event) throws Exception {
        facade.eventManager.updateEvent(event);
    }

    public void refreshEvents() throws Exception {
        eventObservableList.setAll(facade.eventManager.getEvents());
    }

    public void assignCoordinatorToEvent(User coordinator, Event selectedEvent) throws Exception {
        facade.eventManager.assignCoordinatorToEvent(coordinator, selectedEvent);
        selectedEvent.getCoordinators().add(coordinator);
        refreshEventInList(selectedEvent);
    }

    public void removeCoordinatorFromEvent(User coordinator, Event selectedEvent) throws Exception {
        facade.eventManager.removeCoordinatorFromEvent(coordinator, selectedEvent);
        selectedEvent.getCoordinators().removeIf(u -> u.getId() == coordinator.getId());
        refreshEventInList(selectedEvent);
    }

    private void refreshEventInList(Event selectedEvent) {
        for (int i = 0; i < eventObservableList.size(); i++) {
            if (eventObservableList.get(i).getId() == selectedEvent.getId()) {
                eventObservableList.set(i, selectedEvent);
                break;
            }
        }
    }
}

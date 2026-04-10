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

        List<User> newCoordList = new ArrayList<>();
        List<User> coordinatorList = selectedEvent.getCoordinators();

        newCoordList.addAll(coordinatorList);
        newCoordList.add(coordinator);
        selectedEvent.setCoordinators(newCoordList);

        int selectedId = selectedEvent.getId();

        List<Event> tempEventList = new ArrayList<>(eventObservableList);

        for (Event event: tempEventList) {
            if (event.getId() == selectedId) {
                int indexOf = tempEventList.indexOf(event);

                eventObservableList.set(indexOf, selectedEvent);

                break;

            }
        }
    }
}

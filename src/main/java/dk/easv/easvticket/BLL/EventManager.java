package dk.easv.easvticket.BLL;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.DAO.EventDAO;
import dk.easv.easvticket.DAL.Interfaces.IEventDataAccess;

import java.util.List;

public class EventManager {

    private static IEventDataAccess eventDataAccess;

    public EventManager() throws Exception {
        eventDataAccess = new EventDAO();
    }

    public Event createEvent(Event newEvent) throws Exception {
        return eventDataAccess.createEvent(newEvent);
    }

    public List<Event> getEvents() throws Exception {
        return eventDataAccess.getEvents();
    }

    public void updateEvent(Event updatedEvent) throws Exception {
        eventDataAccess.updateEvent(updatedEvent);
    }

    public void deleteEvent(Event event) throws Exception {
        eventDataAccess.deleteEvent(event.getId());
    }

    public void assignCoordinatorToEvent(User coordinator, Event selectedEvent) throws Exception {
        eventDataAccess.assignCoordinatorToEvent(coordinator, selectedEvent);
    }
}

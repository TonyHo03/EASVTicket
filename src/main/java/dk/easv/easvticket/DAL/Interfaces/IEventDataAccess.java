package dk.easv.easvticket.DAL.Interfaces;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.User;

import java.util.List;

public interface IEventDataAccess {
    Event createEvent(Event newEvent) throws Exception;
    List<Event> getEvents() throws Exception;
    void updateEvent(Event event) throws Exception;
    void deleteEvent(int eventID) throws Exception;
    void assignCoordinatorToEvent(User coordinator, Event selectedEvent) throws Exception;
}

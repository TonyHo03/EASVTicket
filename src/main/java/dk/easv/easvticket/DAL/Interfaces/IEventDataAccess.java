package dk.easv.easvticket.DAL.Interfaces;

import dk.easv.easvticket.BE.Event;

import java.util.List;

public interface IEventDataAccess {
    Event createEvent(Event newEvent) throws Exception;
    List<Event> getEvents() throws Exception;
    void updateEvent(Event event) throws Exception;
    void deleteEvent(int eventID) throws Exception;
}

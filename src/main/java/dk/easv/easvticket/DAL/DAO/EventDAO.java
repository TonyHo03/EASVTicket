package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.DAL.Interfaces.IEventDataAccess;

import java.util.List;

public class EventDAO implements IEventDataAccess {
    @Override
    public Event createEvent(Event newEvent) throws Exception {
        return null;
    }

    @Override
    public List<Event> getEvents() throws Exception {
        return List.of();
    }

    @Override
    public void updateEvent(Event event) throws Exception {

    }

    @Override
    public void deleteEvent(int eventID) throws Exception {

    }
}

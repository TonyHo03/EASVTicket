package dk.easv.easvticket.BE;

import javafx.fxml.FXML;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private int id;
    private String name;
    private Date date;
    private Location location;
    private List<User> coordinators;
    private int availableTickets;

    public Event(int id, String name, Date date, Location location, List<User> coordinators, int availableTickets) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.coordinators = (coordinators == null) ? new ArrayList<>() : coordinators;
        this.availableTickets = availableTickets;
    }

    public Event(String name, Date date, Location location, List<User> coordinators, int availableTickets) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.coordinators = (coordinators == null) ? new ArrayList<>() : coordinators;
        this.availableTickets = availableTickets;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Location getLocation() {
        return this.location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public List<User> getCoordinators() {
        return this.coordinators;
    }
    public void setCoordinators(List<User> coordinators) {
        this.coordinators = coordinators;
    }
    public int getAvailableTickets() {
        return this.availableTickets;
    }
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    @Override
    public String toString() {
        return this.name;
    }
}

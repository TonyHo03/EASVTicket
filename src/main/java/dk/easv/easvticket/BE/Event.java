package dk.easv.easvticket.BE;

import javafx.fxml.FXML;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private int id;
    private String name;
    private LocalDateTime date;
    private Location location;
    private List<User> coordinators;
    private int availableTickets;
    private int totalTickets;
    private String description;

    public Event(int id, String name, LocalDateTime date, Location location, List<User> coordinators, int totalTickets, int availableTickets, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.coordinators = (coordinators == null) ? new ArrayList<>() : coordinators;
        this.availableTickets = availableTickets;
        this.totalTickets = totalTickets;
        this.description = description;
    }

    public Event(String name, LocalDateTime date, Location location, List<User> coordinators, int totalTickets, int availableTickets, String description) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.coordinators = (coordinators == null) ? new ArrayList<>() : coordinators;
        this.availableTickets = availableTickets;
        this.totalTickets = totalTickets;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public LocalDateTime getDate() {
        return this.date;
    }
    public Location getLocation() {
        return this.location;
    }
    public List<User> getCoordinators() {
        return this.coordinators;
    }
    public int getAvailableTickets() {
        return this.availableTickets;
    }
    public int getTotalTickets() {
        return totalTickets;
    }
    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
    public void setCoordinators(List<User> coordinators) {
        this.coordinators = coordinators;
    }
    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setId(int anInt) {
    }

    @Override
    public String toString() {
        return this.name;
    }
}

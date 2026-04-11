package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Location;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.BLL.EventManager;
import dk.easv.easvticket.DAL.Interfaces.IEventDataAccess;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDataAccess {

    private DBConnector dbConnector = new DBConnector();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public EventDAO() throws IOException {
    }

    @Override
    public Event createEvent(Event newEvent) throws Exception {
        String sql = "INSERT INTO [Events] (name, event_date, location_id, total_tickets, available_tickets, description) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnector.getConnection()) {

            connection.setAutoCommit(false); // start transaction

            try {

                int locationId = getOrCreateLocationId(connection, newEvent.getLocation());

                try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, newEvent.getName());
                    ps.setTimestamp(2, Timestamp.valueOf(newEvent.getDate()));
                    ps.setInt(3, locationId);
                    ps.setInt(4, newEvent.getTotalTickets());
                    ps.setInt(5, newEvent.getAvailableTickets());
                    ps.setString(6, newEvent.getDescription());
                    ps.executeUpdate();

                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        newEvent.setId(rs.getInt(1));
                    }
                }

                connection.commit(); // end transaction (everything worked)
                return newEvent;

            } catch (SQLException e) {
                connection.rollback(); // something failed
                throw e;
            }

        } catch (SQLException e) {
            throw new Exception("Could not create event", e);
        }
    }

    private int getOrCreateLocationId(Connection connection, Location location) throws SQLException {

        // Try to find existing location
        String selectSql = "SELECT location_id FROM Location WHERE location_name = ? AND address = ? AND city = ?";

        try (PreparedStatement selectPs = connection.prepareStatement(selectSql)) {
            selectPs.setString(1, location.getVenueName());
            selectPs.setString(2, location.getAddress());
            selectPs.setString(3, location.getCity());

            ResultSet rs = selectPs.executeQuery();

            if (rs.next()) {
                return rs.getInt("location_id");
            }
        }

        // If not found make new location
        String insertSql = "INSERT INTO Location (location_name, address, city) VALUES (?, ?, ?)";

        try (PreparedStatement insertPs = connection.prepareStatement(insertSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            insertPs.setString(1, location.getVenueName());
            insertPs.setString(2, location.getAddress());
            insertPs.setString(3, location.getCity());

            insertPs.executeUpdate();

            ResultSet rs = insertPs.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        throw new SQLException("Failed to create or find location");
    }

    @Override
    public List<Event> getEvents() throws Exception {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, e.event_date, e.total_tickets, e.available_tickets, e.description, e.deleted_at, l.location_id, l.location_name, l.address, l.city FROM Events e JOIN Location l ON e.location_id = l.location_id WHERE e.deleted_at IS NULL";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

            while (rs.next()) {
                // Get coordinators for this specific event
                PreparedStatement ps2 = con.prepareStatement("SELECT u.UserId, u.Username, u.Role, u.Email FROM [User] u JOIN EventCoordinators ec ON u.UserId = ec.user_id WHERE ec.event_id = ?");
                ps2.setInt(1, rs.getInt("event_id"));
                List<User> coordinators = new ArrayList<>();

                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    coordinators.add(new User(
                            rs2.getInt("UserId"),
                            rs2.getString("Username"),
                            rs2.getString("Role"),
                            rs2.getString("Email")
                    ));
                }

                // Map standard fields
                int eventId = rs.getInt("event_id");
                String eventName = rs.getString("name");
                LocalDateTime eventDate = rs.getTimestamp("event_date").toLocalDateTime();
                Location eventLocation = new Location(rs.getInt("location_id"), rs.getString("location_name"), rs.getString("address"), rs.getString("city"));
                int totalTickets = rs.getInt("total_tickets");
                int availableTickets = rs.getInt("available_tickets");
                String description = rs.getString("description");

                Event e = new Event(eventId, eventName, eventDate, eventLocation, coordinators, totalTickets, availableTickets, description);

                events.add(e);
            }
        }
        return events;
    }

    @Override
    public void updateEvent(Event event) throws Exception {

    }

    @Override
    public void deleteEvent(int eventID) throws Exception {
        String sql = "UPDATE Events SET deleted_at = ? WHERE event_id = ?";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setObject(1, LocalDateTime.now());
            ps.setInt(2, eventID);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Could not delete event", e);
        }
    }

    @Override
    public void assignCoordinatorToEvent(User coordinator, Event selectedEvent) throws Exception {

        try (Connection connection = dbConnector.getConnection()) {

            System.out.println(coordinator.getId());
            System.out.println(selectedEvent.getId());

            PreparedStatement ps = connection.prepareStatement("INSERT INTO EventCoordinators (event_id, user_id) VALUES (?, ?)");
            ps.setInt(1, selectedEvent.getId());
            ps.setInt(2, coordinator.getId());

            int affectedRows = ps.executeUpdate();

        }
    }
}

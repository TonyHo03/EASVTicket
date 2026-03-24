package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Location;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.BLL.EventManager;
import dk.easv.easvticket.DAL.Interfaces.IEventDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EventDAO implements IEventDataAccess {

    private DBConnector dbConnector = new DBConnector();

    public EventDAO() throws IOException {
    }

    @Override
    public Event createEvent(Event newEvent) throws Exception {
        return null;
    }

    @Override
    public List<Event> getEvents() throws Exception {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT e.event_id, e.name, e.event_date, e.total_tickets, l.location_id, l.location_name, l.address, l.city FROM Events e JOIN Location l ON e.location_id = l.location_id";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery())
             {

                 while (rs.next()) {

                     PreparedStatement ps2 = con.prepareStatement("SELECT u.UserId, u.Username, u.Role, u.Email FROM [User] u JOIN EventCoordinators ec ON u.UserId = ec.user_id WHERE ec.event_id = ?");
                     ps2.setInt(1, rs.getInt("event_id"));
                     List<User> coordinators = new ArrayList<>();

                     ResultSet rs2 = ps2.executeQuery();

                     while (rs2.next()) {

                         int id = rs2.getInt("UserId");
                         String user = rs2.getString("Username");
                         String role = rs2.getString("Role");
                         String email = rs2.getString("Email");

                         coordinators.add(new User(id, user, role, email));

                     }

                     Event e = new Event(
                             rs.getInt("event_id"),
                             rs.getString("name"),
                             rs.getDate("event_date"),
                             new Location(rs.getInt("location_id"), rs.getString("location_name"), rs.getString("address"), rs.getString("city")),
                             coordinators,
                             rs.getInt("total_tickets")
                     );
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
        try (Connection con = dbConnector.getConnection()) {

            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM EventCoordinators WHERE event_id = ?");
            preparedStatement.setInt(1, eventID);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement1 = con.prepareStatement("DELETE FROM Events WHERE event_id = ?");
            preparedStatement1.setInt(1, eventID);
            Integer resultSet = preparedStatement1.executeUpdate();
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

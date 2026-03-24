package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Location;
import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.User;
import dk.easv.easvticket.DAL.Interfaces.ITicketDataAccess;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements ITicketDataAccess {
    private DBConnector dbConnector = new DBConnector();

    public TicketDAO() throws IOException {
    }

    @Override
    public Ticket createTicket(Ticket newTicket) throws Exception {
        return null;
    }

    @Override
    public List<Ticket> getTickets() throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.id, t.event, t.customer_name, t.email, t.price, t.status, e.event_id, e.name, e.event_date, e.location_id, e.total_tickets, e.available_tickets, e.description, l.location_id, l.location_name, l.address, l.city FROM Ticket t JOIN Events e ON t.event = e.event_id JOIN Location l ON e.location_id = l.location_id";

        try (Connection con = dbConnector.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {

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

                Ticket ticket = new Ticket(
                        rs.getInt("id"),
                        new Event(rs.getInt("event_id"), rs.getString("name"), rs.getDate("event_date"), new Location(rs.getInt("location_id"), rs.getString("location_name"), rs.getString("address"), rs.getString("city")), coordinators, rs.getInt("total_tickets"), rs.getInt("available_tickets"), rs.getString("description")),
                        rs.getString("customer_name"),
                        rs.getString("email"),
                        rs.getDouble("price"),
                        String.valueOf(rs.getInt("status"))
                );
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    @Override
    public void updateTicket(Ticket ticket) throws Exception {

    }

    @Override
    public void deleteTicket(int TicketID) throws Exception {

    }
}

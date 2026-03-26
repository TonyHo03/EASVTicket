package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.*;
import dk.easv.easvticket.DAL.Interfaces.ITicketDataAccess;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketDAO implements ITicketDataAccess {
    private DBConnector dbConnector = new DBConnector();

    public TicketDAO() throws IOException {
    }

    @Override
    public Ticket createTicket(Ticket newTicket) throws Exception {

        try (Connection conn = dbConnector.getConnection()) {

            PreparedStatement ps = conn.prepareStatement("INSERT INTO Ticket (event, customer_name, email, price, ticket_type, ticket_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, newTicket.getEvent().getId());
            ps.setString(2, newTicket.getCustomerName());
            ps.setString(3, newTicket.getEmail());
            ps.setDouble(4, newTicket.getPrice());
            ps.setInt(5, newTicket.getTicketType().getId());
            ps.setString(6, newTicket.getTicketId());
            ps.executeUpdate();

            ResultSet generatedIds = ps.getGeneratedKeys();
            int generatedId;
            Ticket createdTicket = new Ticket(0, "0", null, "", "", 0, new TicketTypes(0, ""));

            if (generatedIds.next()) {

                generatedId = generatedIds.getInt(1);
                createdTicket = new Ticket(generatedId, newTicket.getTicketId(), newTicket.getEvent(), newTicket.getCustomerName(), newTicket.getEmail(), newTicket.getPrice(), newTicket.getTicketType());

            }

            return createdTicket;

        }
        catch (SQLException e) {

            throw new SQLException("Could not create ticket", e);

        }

    }

    @Override
    public List<Ticket> getTickets() throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT t.id, t.event, t.customer_name, t.email, t.price, t.ticket_type, t.ticket_id, e.event_id, e.name, e.event_date, e.location_id, e.total_tickets, e.available_tickets, e.description, l.location_id, l.location_name, l.address, l.city, tt.tickettype_id, tt.tickettype FROM Ticket t JOIN Events e ON t.event = e.event_id JOIN Location l ON e.location_id = l.location_id JOIN TicketTypes tt ON t.ticket_type = tt.tickettype_id";

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
                        rs.getInt("id"), rs.getString("ticket_id"),
                        new Event(rs.getInt("event_id"), rs.getString("name"), rs.getTimestamp("event_date").toLocalDateTime(), new Location(rs.getInt("location_id"), rs.getString("location_name"), rs.getString("address"), rs.getString("city")), coordinators, rs.getInt("total_tickets"), rs.getInt("available_tickets"), rs.getString("description")),
                        rs.getString("customer_name"),
                        rs.getString("email"),
                        rs.getDouble("price"),
                        new TicketTypes(rs.getInt("tickettype_id"), rs.getString("tickettype"))
                );
                tickets.add(ticket);
            }
        }
        catch (SQLException e) {

            throw new SQLException("Could not get tickets", e);

        }
        return tickets;
    }

    @Override
    public List<TicketTypes> getTicketTypes() throws Exception {

        List<TicketTypes> ticketTypes = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT * FROM TicketTypes");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int id = rs.getInt("tickettype_id");
                String ticketType = rs.getString("tickettype");

                ticketTypes.add(new TicketTypes(id, ticketType));

            }

            return ticketTypes;

        }
        catch (SQLException e) {

            throw new SQLException("Could not get ticket types", e);

        }

    }

    @Override
    public void updateTicket(Ticket ticket) throws Exception {

    }

    @Override
    public void deleteTicket(int TicketID) throws Exception {

    }
}

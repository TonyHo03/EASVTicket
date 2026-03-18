package dk.easv.easvticket.BLL;

import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.DAL.DAO.TicketDAO;
import dk.easv.easvticket.DAL.Interfaces.ITicketDataAccess;

import java.util.List;

public class TicketManager {
    private ITicketDataAccess ticketDataAccess;

    public TicketManager() throws Exception {
        ticketDataAccess = new TicketDAO();
    }

    public Ticket createTicket(Ticket newTicket) throws Exception {
        return ticketDataAccess.createTicket(newTicket);
    }

    public List<Ticket> getTickets() throws Exception {
        return ticketDataAccess.getTickets();
    }

    public void updateTicket(Ticket updatedTicket) throws Exception {
        ticketDataAccess.updateTicket(updatedTicket);
    }

    public void deleteTicket(Ticket selectedTicket) throws Exception {
        ticketDataAccess.deleteTicket(selectedTicket.getId()); // deletes by id
    }
}

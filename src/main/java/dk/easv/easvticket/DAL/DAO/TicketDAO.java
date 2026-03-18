package dk.easv.easvticket.DAL.DAO;

import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.DAL.Interfaces.ITicketDataAccess;

import java.util.List;

public class TicketDAO implements ITicketDataAccess {
    @Override
    public Ticket createTicket(Ticket newTicket) throws Exception {
        return null;
    }

    @Override
    public List<Ticket> getTickets() throws Exception {
        return List.of();
    }

    @Override
    public void updateTicket(Ticket ticket) throws Exception {

    }

    @Override
    public void deleteTicket(int TicketID) throws Exception {

    }
}

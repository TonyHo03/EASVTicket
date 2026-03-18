package dk.easv.easvticket.DAL.Interfaces;

import dk.easv.easvticket.BE.Ticket;

import java.util.List;

public interface ITicketDataAccess {
    Ticket createTicket(Ticket newTicket) throws Exception;
    List<Ticket> getTickets() throws Exception;
    void updateTicket(Ticket ticket) throws Exception;
    void deleteTicket(int TicketID) throws Exception;
}

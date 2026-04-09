package dk.easv.easvticket.DAL.Interfaces;

import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.TicketTypes;

import java.util.List;

public interface ITicketDataAccess {
    Ticket createTicket(Ticket newTicket) throws Exception;
    List<Ticket> getTickets() throws Exception;
    List<TicketTypes> getTicketTypes() throws Exception;
    void updateTicket(Ticket ticket) throws Exception;
    void deleteTicket(Ticket ticket) throws Exception;
}

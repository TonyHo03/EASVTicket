package dk.easv.easvticket.Facade;

import dk.easv.easvticket.BE.Event;
import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BLL.EventManager;
import dk.easv.easvticket.BLL.TicketManager;
import dk.easv.easvticket.BLL.UserManager;

import java.util.List;

public class TicketSystemFacade {

    public UserManager userManager = new UserManager();
    public EventManager eventManager = new EventManager();
    public TicketManager ticketManager = new TicketManager();

    public TicketSystemFacade() throws Exception {
    }

    public List<Ticket> getTickets() throws Exception {
        return ticketManager.getTickets();
    }

}

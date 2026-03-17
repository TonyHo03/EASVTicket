package dk.easv.easvticket.Facade;

import dk.easv.easvticket.BLL.EventManager;
import dk.easv.easvticket.BLL.TicketManager;
import dk.easv.easvticket.BLL.UserManager;

public class TicketSystemFacade {

    public UserManager userManager = new UserManager();
    public EventManager eventManager = new EventManager();
    public TicketManager ticketManager = new TicketManager();

    public TicketSystemFacade() throws Exception {}

}

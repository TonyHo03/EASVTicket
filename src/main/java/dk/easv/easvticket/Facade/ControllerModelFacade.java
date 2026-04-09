package dk.easv.easvticket.Facade;

import dk.easv.easvticket.GUI.Models.EventModel;
import dk.easv.easvticket.GUI.Models.TicketModel;
import dk.easv.easvticket.GUI.Models.UserModel;

public class ControllerModelFacade {

    public EventModel eventModel = new EventModel();
    public TicketModel ticketModel = new TicketModel();
    public UserModel userModel = new UserModel();

    public ControllerModelFacade() throws Exception {}

}

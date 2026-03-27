package dk.easv.easvticket.GUI.Models;

import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.TicketTypes;
import dk.easv.easvticket.Facade.TicketSystemFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class TicketModel {
    private TicketSystemFacade facade = new TicketSystemFacade();
    private ObservableList<Ticket> ticketObservableList;

    public TicketModel() throws Exception {
        ticketObservableList = FXCollections.observableArrayList();
        ticketObservableList.setAll(facade.ticketManager.getTickets());
    }
    public ObservableList<Ticket> getTickets() {
        return ticketObservableList;
    }
    public void deleteTicket(Ticket ticket) throws Exception {
        facade.ticketManager.deleteTicket(ticket);
        ticketObservableList.remove(ticket);
    }
    public List<TicketTypes> getTicketTypes() throws Exception {
        return facade.ticketManager.getTicketTypes();
    }
    public void createTicket(Ticket newTicket) throws Exception {
        Ticket createdTicket = facade.ticketManager.createTicket(newTicket);
        ticketObservableList.add(createdTicket);
    }

    public void updateTicket(Ticket updatedTicket) throws Exception {
        facade.ticketManager.updateTicket(updatedTicket);
        int index = ticketObservableList.indexOf(updatedTicket);
        if (index >= 0) {ticketObservableList.set(index, updatedTicket);}
    }

    public void printTicket(Ticket selectedTicket) throws Exception {

        facade.ticketManager.printPDF(selectedTicket);

    }
}

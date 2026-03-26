package dk.easv.easvticket.BLL;

import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import dk.easv.easvticket.BE.Ticket;
import dk.easv.easvticket.BE.TicketTypes;
import dk.easv.easvticket.DAL.DAO.TicketDAO;
import dk.easv.easvticket.DAL.Interfaces.ITicketDataAccess;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.google.zxing.*;

public class TicketManager {
    private ITicketDataAccess ticketDataAccess;

    private final String SECRET = "7f3k9mXq2vNpLwR8hYtBdCeA5sJgUiOf";

    public TicketManager() throws Exception {
        ticketDataAccess = new TicketDAO();
    }

    public Ticket createTicket(Ticket newTicket) throws Exception {
        return ticketDataAccess.createTicket(newTicket);
    }

    public List<Ticket> getTickets() throws Exception {
        return ticketDataAccess.getTickets();
    }

    public List<TicketTypes> getTicketTypes() throws Exception {

        return ticketDataAccess.getTicketTypes();

    }

    public void updateTicket(Ticket updatedTicket) throws Exception {
        ticketDataAccess.updateTicket(updatedTicket);
    }

    public void deleteTicket(Ticket selectedTicket) throws Exception {
        ticketDataAccess.deleteTicket(selectedTicket.getId()); // deletes by id
    }

    public void generate2DCode(Ticket selectedTicket) {



    }

}

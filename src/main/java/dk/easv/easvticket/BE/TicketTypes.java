package dk.easv.easvticket.BE;

public class TicketTypes {

    private int id;
    private String ticketType;

    public TicketTypes(int id, String ticketType) {
        this.id = id;
        this.ticketType = ticketType;
    }

    public int getId() {
        return id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    @Override
    public String toString() {

        return this.ticketType;

    }
}

package dk.easv.easvticket.BE;

public class TicketTypes {

    private int id;
    private String ticketType;
    private int eventId;

    public TicketTypes(int id, String ticketType, int eventId) {
        this.id = id;
        this.ticketType = ticketType;
        this.eventId = eventId;
    }

    public TicketTypes(String ticketType, int eventId) {
        this.ticketType = ticketType;
        this.eventId = eventId;
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

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    @Override
    public String toString() {

        return this.ticketType;

    }
}

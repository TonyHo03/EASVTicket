package dk.easv.easvticket.BE;

public class Ticket {

    private int id;
    private String ticketId;
    private Event event;
    private String customerName;
    private String email;
    private double price;
    private String status;

    public Ticket(int id, String ticketId, Event event, String customerName, String email, double price, String status) {
        this.id = id;
        this.ticketId = ticketId;
        this.event = event;
        this.customerName = customerName;
        this.email = email;
        this.price = price;
        this.status = status;
    }

    public Ticket(String ticketId, Event event, String customerName, String email, double price, String status) {
        this.ticketId = ticketId;
        this.event = event;
        this.customerName = customerName;
        this.email = email;
        this.price = price;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }
    public String getTicketId() {
        return this.ticketId;
    }
    public Event getEvent() {
        return this.event;
    }
    public String getCustomerName() {
        return this.customerName;
    }
    public String getEmail() {
        return this.email;
    }
    public double getPrice() {
        return this.price;
    }
    public String getStatus() {
        return this.status;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.customerName + " " + this.email + " " + this.price + " " + this.status;
    }

}

package dk.easv.easvticket.BE;

public class Location {

    private int id;
    private String venueName;
    private String address;
    private String city;

    public Location(int id, String venueName, String address, String city) {
        this.id = id;
        this.venueName = venueName;
        this.address = address;
        this.city = city;
    }

    public Location(String venueName, String address, String city) {
        this.venueName = venueName;
        this.address = address;
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return this.venueName + ", " + this.address + ", " + this.city;
    }
}

package model;

public class Room {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    private int hotelId;
    private String type;
    private double price;
    private int capacity;

    public Room() {
        // Constructor ngầm định
    }

    public Room(int id, int hotelId, String type, double price, int capacity) {
        this.id = id;
        this.hotelId = hotelId;
        this.type = type;
        this.price = price;
        this.capacity = capacity;
    }
}

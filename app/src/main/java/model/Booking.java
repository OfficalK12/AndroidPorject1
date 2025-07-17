package model;

public class Booking {
    private int id;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    private int roomId;
    private String checkIn;
    private String checkOut;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getPriceAtBooking() {
        return priceAtBooking;
    }

    public void setPriceAtBooking(double priceAtBooking) {
        this.priceAtBooking = priceAtBooking;
    }

    private String paymentMethod;
    private double priceAtBooking;

    public Booking() {
        // Constructor ngầm định
    }

    public Booking(int id, int userId, int roomId, String checkIn, String checkOut, String paymentMethod, double priceAtBooking) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.paymentMethod = paymentMethod;
        this.priceAtBooking = priceAtBooking;
    }
}
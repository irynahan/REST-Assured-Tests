package com.herokuapp.restfulbooker;

public class BookingId {

    private int bookingId;
    private Booking booking;

    public BookingId(int bookingId, Booking booking) {
        this.bookingId = bookingId;
        this.booking = booking;
    }

    public BookingId() {
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    @Override
    public String toString() {
        return "BookingId{" +
                "bookingId=" + bookingId +
                ", booking=" + booking +
                '}';
    }

}

package com.herokuapp.restfulbooker;

public class Bookingdates {

    private String checkin;
    private String checkout;

    public Bookingdates(String checkin, String heckout) {
        this.checkin = checkin;
        this.checkout = heckout;
    }

    // empty constructor for response object
    public Bookingdates() {
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    @Override
    public String toString() {
        return "Bookingdates{" +
                "checkin='" + checkin + '\'' +
                ", heckout='" + checkout + '\'' +
                '}';
    }
}

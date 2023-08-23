package com.herokuapp.restfulbooker;

public class Bookingdates {

    private String checkin;
    private String heckout;

    public Bookingdates(String checkin, String heckout) {
        this.checkin = checkin;
        this.heckout = heckout;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getHeckout() {
        return heckout;
    }

    public void setHeckout(String heckout) {
        this.heckout = heckout;
    }

    @Override
    public String toString() {
        return "Bookingdates{" +
                "checkin='" + checkin + '\'' +
                ", heckout='" + heckout + '\'' +
                '}';
    }
}

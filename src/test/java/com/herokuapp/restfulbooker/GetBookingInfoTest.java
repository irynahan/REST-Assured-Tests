package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GetBookingInfoTest {

    private String getBookingUrl = "https://restful-booker.herokuapp.com/booking/";
    private int bookingId = 9;
    // private int bookingId = new Random().nextInt(10);

    @Test
    public void getBookingInfoTest ( ) {
        // get booking with booking ID
        Response response = RestAssured.get(getBookingUrl + bookingId);
        response.print();
        // check of status code
        Assert.assertEquals(response.getStatusCode(), 200);
        // check first and last name in the booking
        String firstName = response.jsonPath().getString("firstname");
        String lastName = response.jsonPath().getString("lastname");
        Assert.assertEquals(firstName + lastName, "Mary" + "Jones", "name of the client is wrong");
    }

}

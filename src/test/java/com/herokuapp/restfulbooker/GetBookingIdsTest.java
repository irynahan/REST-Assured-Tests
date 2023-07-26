package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GetBookingIdsTest {

    private String getBookingUrl = "https://restful-booker.herokuapp.com/booking";

    @Test
    public void getBookingIdsWithoutFiltersTest() {
        // Get response with booking ids
        Response response = RestAssured.get(getBookingUrl);
        response.print();

        // verify response with status code 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200, but it is not");

        // verify at least one booking id in response
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        System.out.println("There are " + bookingIds.size() + " actual bookings in the system");
        Collections.sort(bookingIds);
        System.out.println(bookingIds);
        Assert.assertFalse(bookingIds.isEmpty(), "The list is empty");

    }
}

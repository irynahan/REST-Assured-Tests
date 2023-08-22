package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GetBookingIdsTest extends BaseTest {

    private String getBookingUrl = "https://restful-booker.herokuapp.com/booking";

    @Test
    public void getBookingIdsWithoutFiltersTest() {
        // Get response with booking ids
        Response response = RestAssured.given().
                spec(spec).
                get("/booking");
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

    @Test
    public void getBookingIdsWithFilterByFirstnameTest() {
        // get request with query
        Response responseGetBookingByFirstname = RestAssured.given().
                spec(spec).
                get("/booking?firstname=Olga");
        responseGetBookingByFirstname.print();

    }

    @Test
    public void getBookingWithSpecFilter() {
        // add query parameters to spec
        spec.queryParam("firstname", "Olga");
        spec.queryParam("lastname", "Moroz");

        // get request booking with query filter
        Response responseGetBookingByFirstname = RestAssured.given(spec).get("/booking");
        responseGetBookingByFirstname.print();



    }

}

package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteBookingTest extends BaseTest {

    @Test
    public void deleteBookingTest() {

        // create new booking and get the booking ID
        Response responseBookingCreated = createNewBooking();
        int bookingId = responseBookingCreated.jsonPath().getInt("bookingid");
        responseBookingCreated.print();

        // delete request to delete booking

        Response responseDelete = RestAssured.given()
                .spec(spec)
                .auth().preemptive().basic("admin", "password123")
                .delete("/booking/" + bookingId);
        responseDelete.print();

        // Verify response code
        Assert.assertEquals(responseDelete.getStatusCode(), 201, "Delete request fails");

        // Verify that booking was deleted - response message

        Response responseGetBookingInfo = RestAssured.given().spec(spec).get("/booking/" + bookingId);
        responseGetBookingInfo.print();

        Assert.assertEquals(responseGetBookingInfo.getBody().asString(), "Not Found", "Body should be Not found, but is not");

    }
}

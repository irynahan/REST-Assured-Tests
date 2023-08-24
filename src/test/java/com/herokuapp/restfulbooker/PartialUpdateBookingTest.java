package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class PartialUpdateBookingTest extends BaseTest {

    @Test
    public void partialUpdateBookingTest() {

        // create new booking and get the booking ID
        Response responseBookingCreated = createNewBooking();
        int bookingId = responseBookingCreated.jsonPath().getInt("bookingid");
        responseBookingCreated.print();

        // create request doby for update
        JSONObject partUpdateBody = new JSONObject();
        partUpdateBody.put("firstname", "Maria");
        partUpdateBody.put("bookingdates.checkin", "01.01.2023");

        // send request to the server and become a response

        Response responseUpdate = RestAssured.given()
                .spec(spec)
                .auth().preemptive().basic("admin","password123")
                .contentType(ContentType.JSON)
                .body(partUpdateBody.toString())
                .patch("/" + bookingId);
        responseUpdate.print();

        // verification of status code
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Actual status code is not expected");

        // verification of the response body changes
        SoftAssert softAssert = new SoftAssert();

        String actualFirstname = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstname, "Maria", "The firstname has not been changed");

        String actualCheckInDate = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckInDate, "2023-01-01", "The check in date has not been changed");

        softAssert.assertAll("Some changes have not been made");

    }

}

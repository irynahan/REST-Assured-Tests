package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {


    @Test
    public void createNewBookingTest() {

        Response response = createNewBooking();
        response.print();

        // verifications

        // check the status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200, but it is not");

        // check value of all response fields with soft assert
        SoftAssert softAssert = new SoftAssert();

        String actualFirstname = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstname, "Olga", "Value of first name is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Moroz", "Value of last name is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 400, "The total price is wrong");

        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositPaid, "Depоsit schould be paid, but it has not been payed");

        String actualCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2018-01-01", "Value of check in date is not expected");

        String actualCheckOut = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2019-01-01", "Value of check out date is not expected");

        String addNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(addNeeds, "Breakfast", "Value of additional needs field is not expected");

        softAssert.assertAll("One or more values are not expected");

    }

    @Test
    public void  createNewBookingPOJOTest() {

        // create body using POJOs
        Bookingdates bookingdates = new Bookingdates("2023-01-01", "2023-01-09");
        Booking booking = new Booking("Leonid", "Voronin", 750, true, bookingdates, "Baby crib");

        // get response
        RequestSpecification given = RestAssured.given(spec).log().all();
        Response response  = given.
                contentType(ContentType.JSON).
                body(booking).
                post("/booking");
        response.print();

        // check the status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200, but it is not");


        // check value of all response fields with soft assert
        SoftAssert softAssert = new SoftAssert();

        String actualFirstname = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstname, "Leonid", "Value of first name is not expected");

        String actualLastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Voronin", "Value of last name is not expected");

        int actualTotalPrice = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 750, "The total price is wrong");

        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositPaid, "Depоsit schould be paid, but it has not been payed");

        String actualCheckIn = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2023-01-01", "Value of check in date is not expected");

        String actualCheckOut = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2023-01-09", "Value of check out date is not expected");

        String addNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(addNeeds, "Baby crib", "Value of additional needs field is not expected");

        softAssert.assertAll("One or more values are not expected");

    }

}


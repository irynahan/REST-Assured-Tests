package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GetBookingInfoTest extends BaseTest {

    @Test
    public void getBookingInfoNameTest( ) {

        // create new booking and get ID
        Response responseBookingCreated = createNewBooking();

        //https://restful-booker.herokuapp.com/booking/:id
        // set path parameter
        spec.pathParam("bookingId", responseBookingCreated.jsonPath().getInt("bookingid"));

        // get booking with booking ID
        Response response = RestAssured.given().
                spec(spec).
                get("/booking/{bookingId}");
        response.print();

        // check of status code
        Assert.assertEquals(response.getStatusCode(), 200);
        // check first and last name in the booking
        String firstName = response.jsonPath().getString("firstname");
        String lastName = response.jsonPath().getString("lastname");
        Assert.assertEquals(firstName + lastName, "Olga" + "Moroz", "name of the client is wrong");
    }

    @Test
    public void getBookingInfoAllFieldsTest(){

        // create new booking
        Response responseBookingCreated = createNewBooking();
        responseBookingCreated.print();

        // set path parameter and get ID
        spec.pathParam("bookingId", responseBookingCreated.jsonPath().getInt("bookingid"));

        // get booking with created booking ID
        Response response= RestAssured.given().
                spec(spec).
                get("/booking/{bookingId}");
        response.print();

        // check of status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200, but it is not");

        // check value of all response fields with soft assert
        SoftAssert softAssert = new SoftAssert();

        String actualFirstname = response.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstname,"Olga", "Value of first name is not expected");

        String actualLastName = response.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Moroz", "Value of last name is not expected");

        double actualTotalPrice = response.jsonPath().getDouble("totalprice");
        softAssert.assertEquals(actualTotalPrice, 400.0, "The total price is wrong");

        boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "Depоsit schould be paid, but it has not been payed");

        String actualCheckIn = response.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2018-01-01", "Value of check in date is not expected");

        String actualCheckOut = response.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2019-01-01", "Value of check out date is not expected");

        String addNeeds = response.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(addNeeds, "Breakfast", "Value of additional needs field is not expected");

        softAssert.assertAll("One or more values are not expected");

    }

    @Test
    public void getBookingInfoXMLTest() {

        // create new booking
        Response responseBookingCreated = createNewBooking();
        responseBookingCreated.print();

        // set path parameter and get ID
        spec.pathParam("bookingId", responseBookingCreated.jsonPath().getInt("bookingid"));


        // header for Get request to set a response format
        Header xml = new Header("Accept", "application/xml");
        spec.header(xml);

        // get booking with created booking ID
        Response response= RestAssured.given().
                spec(spec).
                get("/booking/{bookingId}");
        response.print();

        // check of status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code is 200, but it is not");

        // check value of all response fields with soft assert
        SoftAssert softAssert = new SoftAssert();

        String actualFirstname = response.xmlPath().getString("booking.firstname");
        softAssert.assertEquals(actualFirstname,"Olga", "Value of first name is not expected");

        String actualLastName = response.xmlPath().getString("booking.lastname");
        softAssert.assertEquals(actualLastName, "Moroz", "Value of last name is not expected");

        double actualTotalPrice = response.xmlPath().getDouble("booking.totalprice");
        softAssert.assertEquals(actualTotalPrice, 400.0, "The total price is wrong");

        boolean depositPaid = response.xmlPath().getBoolean("booking.depositpaid");
        softAssert.assertTrue(depositPaid, "Depоsit schould be paid, but it has not been payed");

        String actualCheckIn = response.xmlPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2018-01-01", "Value of check in date is not expected");

        String actualCheckOut = response.xmlPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2019-01-01", "Value of check out date is not expected");

        String addNeeds = response.xmlPath().getString("booking.additionalneeds");
        softAssert.assertEquals(addNeeds, "Breakfast", "Value of additional needs field is not expected");

        softAssert.assertAll("One or more values are not expected");

    }

}

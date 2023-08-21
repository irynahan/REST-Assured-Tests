package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTest extends BaseTest {

    @Test
    public void updateBookingTest() {

        // create new booking and get its ID
        Response responseCreateBooking = createNewBooking();
        responseCreateBooking.print();
        int bookingId = responseCreateBooking.jsonPath().getInt("bookingid");
        System.out.println(bookingId);

        // create JSON body for PUT request
        JSONObject updateBody = new JSONObject();
        updateBody.put("firstname", "Olga");
        updateBody.put("lastname", "Moroz");
        updateBody.put("totalprice", 500);
        updateBody.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2023-02-03");
        bookingDates.put("checkout", "2023-02-07");

        updateBody.put("bookingdates", bookingDates);
        updateBody.put("additionalneeds", "Breakfast");

        // update booking
        Response responseUpdate = RestAssured.given().auth().preemptive().basic("admin","password123").contentType(ContentType.JSON).body(updateBody.toString()).put(BaseUrl + bookingId);
        responseUpdate.print();
        
        // check status code
        Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Actual status code is not correct");
        
        // check response body
        
        SoftAssert softAssert = new SoftAssert();
        String actualFirstname = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(actualFirstname, "Olga", "Value of first name is not expected");

        String actualLastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(actualLastName, "Moroz", "Value of last name is not expected");

        int actualTotalPrice = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(actualTotalPrice, 500, "The total price is wrong");

        boolean depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertTrue(depositPaid, "Depоsit schould be paid, but it has not been payed");

        String actualCheckIn = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckIn, "2023-02-03", "Value of check in date is not expected");

        String actualCheckOut = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckOut, "2023-02-07", "Value of check out date is not expected");

        String addNeeds = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(addNeeds, "Breakfast", "Value of additional needs field is not expected");
        
        softAssert.assertAll("One or more parameters in the body are not correct");

        System.out.println(responseUpdate.getStatusCode());

    }
}

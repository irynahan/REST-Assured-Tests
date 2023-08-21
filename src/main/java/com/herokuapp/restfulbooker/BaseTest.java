package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

public class BaseTest {

    private String createBookingUrl = "https://restful-booker.herokuapp.com/booking";
    protected String BaseUrl = "https://restful-booker.herokuapp.com/booking/";

    protected Response createNewBooking() {
        // create json body
        JSONObject body = new JSONObject();
        body.put("firstname", "Olga");
        body.put("lastname", "Moroz");
        body.put("totalprice", 400);
        body.put("depositpaid", true);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "2018-01-01");
        bookingDates.put("checkout", "2019-01-01");

        body.put("bookingdates", bookingDates);
        body.put("additionalneeds", "Breakfast");

        // get response
        Response response = RestAssured.given().contentType(ContentType.JSON).body(body.toString()).post(createBookingUrl);
        return response;
    }


}

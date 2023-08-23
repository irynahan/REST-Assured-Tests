package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected RequestSpecification spec;

    @BeforeMethod
    public void setUp() {
        spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();
    }
    // "https://restful-booker.herokuapp.com/booking/"
    private String createBookingEndPoint = "/booking";


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

        String requestBody = body.toString();
        // get response
        Response response = RestAssured.given().
                spec(spec).
                contentType(ContentType.JSON).
                body(requestBody).
                log().all().
                post(createBookingEndPoint);
        return response;
    }


}

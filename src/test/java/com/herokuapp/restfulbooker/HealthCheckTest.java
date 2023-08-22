package com.herokuapp.restfulbooker;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class HealthCheckTest extends BaseTest {

    @Test
    public void healthCheckTest() {
        given().spec(spec).when().get("/ping").
                then().
                assertThat().
                statusCode(201);
    }

    @Test
    public void headersTest() {
        // request with headers
        Header firstHeader = new Header("header_name1", "header_value2");
        spec.header(firstHeader);

        Response response = RestAssured.given(spec).
                header("header_name2", "header_value2").
                log().all().
                get("/ping");

        // get all headers with headers class from response
        Headers headers = response.getHeaders();
        System.out.println(headers);

        //
        Header serverHeader = headers.get("Server");
        System.out.println(serverHeader.getName() + ":" + serverHeader.getValue());

        // get header value direct from response without header's name
        System.out.println(response.getHeader("Server"));

    }

    @Test
    public void cookiesTest() {

        // request with cookie
        Cookie firstCookie = new Cookie.Builder("cookie_name1", "cookie_value1").build();
        spec.cookie(firstCookie);

        Response response = RestAssured.given(spec).
                cookie("cookie_name2", "cookie_value2").
                log().all().
                get("/ping");

        // get all cokiess with cookies class (as with headers)

        Cookies cookiesDetailed = response.getDetailedCookies();
        System.out.println(cookiesDetailed);

    }

}

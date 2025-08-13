package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthUtil {

    private static final String AUTH_URL = "https://webapps.tekstac.com/OAuthRestApi/webapi/auth";

    /**
     * Step 1: login -> get auth_code
     */
    public static String getAuthCode(String username, String password) {
        String loginPath = "/login";
        Response r = given()
                .baseUri("https://webapps.tekstac.com")
                .header("Content-Type", "application/json")
                .body("{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}")
                .when().post("/OAuthRestApi/webapi/auth/login")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        return r.jsonPath().getString("auth_code");
    }

    /**
     * Step 2: exchange auth_code for access_token
     */
    public static String getAccessToken(String authCode) {
        Response r = given()
                .baseUri("https://webapps.tekstac.com")
                .header("Content-Type", "application/json")
                .body("{\"auth_code\":\"" + authCode + "\"}")
                .when().post("/OAuthRestApi/webapi/auth/token")
                .then().log().all()
                .statusCode(200)
                .extract().response();

        return r.jsonPath().getString("access_token");
    }
}

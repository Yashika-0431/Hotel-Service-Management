package tests;

import Base.ApiBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AuthTests extends ApiBase {

    private String auth_code;
    private String access_token;

    @BeforeClass
    public void setup() {
        // Set the base URI for authentication APIs
        // This will be used for both login and token
        setBaseURI("https://webapps.tekstac.com/OAuthRestApi/webapi/auth");
    }

    private RequestSpecification freshReq() {
        return getRequest();
    }

    @Test(priority = 1)
    public void twoStepAuth_statusCode200() {
        Response res = freshReq()
                .contentType(ContentType.URLENC)
                .formParam("username", "user1")
                .formParam("password", "pass123")
                .post("/login")
                .then()
                .statusCode(200) // validate HTTP 200
                .extract().response();

        auth_code = res.jsonPath().getString("auth_code");

        // Validation
        Assert.assertNotNull(auth_code, "auth_code should not be null");
        Assert.assertTrue(auth_code.length() > 0, "auth_code should not be empty");
    }

    @Test(priority = 2)
    public void twoStepAuth_authCodeNotNull() {
        Assert.assertNotNull(auth_code, "auth_code should not be null from previous step");
    }

    @Test(priority = 3)
    public void tokenRequest_statusCode200() {
        // Ensure auth_code is available
        Assert.assertNotNull(auth_code, "auth_code must be available before requesting token");

        Response res = freshReq()
                .contentType(ContentType.URLENC) // match Postman request
                .formParam("auth_code", auth_code) // pass auth_code from step 1
                .post("/token")
                .then()
                .statusCode(200) // validate HTTP 200
                .extract().response();

        access_token = res.jsonPath().getString("access_token");

        // Validation
        Assert.assertNotNull(access_token, "access_token should not be null");
        Assert.assertTrue(access_token.length() > 0, "access_token should not be empty");
    }

    @Test(priority = 4)
    public void tokenRequest_accessTokenNotNull() {
        Assert.assertNotNull(access_token, "access_token should not be null from previous step");
    }
}
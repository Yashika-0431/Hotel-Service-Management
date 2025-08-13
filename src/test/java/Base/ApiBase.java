package Base;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ApiBase {

    // Sets RestAssured base URI for the tests
    public void setBaseURI(String baseURI) {
        RestAssured.baseURI = baseURI;
    }

    // Returns a default RequestSpecification object for reuse
    public RequestSpecification getRequest() {
        return RestAssured.given()
                .log().all()          // optional: log requests for debugging
                .header("Accept", "*/*"); // default Accept header (can customize)
    }
}
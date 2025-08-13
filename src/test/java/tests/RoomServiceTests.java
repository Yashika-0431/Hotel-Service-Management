package tests;

import Base.ApiBase;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.TestDataLoader;

public class RoomServiceTests extends ApiBase {

    @BeforeClass
    public void setup() {
        setBaseURI("https://webapps.tekstac.com/HotelAPI/RoomService");
    }

    private RequestSpecification freshReq() {
        return getRequest();
    }

    @Test(priority = 1)
    public void viewRoomList_status200() {
        freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomList")
            .then()
            .statusCode(200);
    }

    @Test(priority = 2)
    public void viewRoomList_responseTimeBelow2s() {
        long time = freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomList")
            .time();
        Assert.assertTrue(time < 2000, "Response time should be below 2 seconds");
    }

    @Test(priority = 3)
    public void viewRoomList_bodyNotEmpty() {
        String body = freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomList")
            .asString();
        Assert.assertNotNull(body);
        Assert.assertTrue(body.length() > 0);
    }

    // Intentional Fail #1
    @Test(priority = 4)
    public void viewRoomList_bodyIsEmpty_Fail() {
        String body = freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomList")
            .asString();
        Assert.assertNull(body, "Intentional fail - body is expected null");
    }

    @Test(priority = 5)
    public void viewRoomById_status200() {
        freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomById/101")
            .then()
            .statusCode(200);
    }

    @Test(priority = 6)
    public void viewRoomById_responseTimeBelow2s() {
        long time = freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomById/101")
            .time();
        Assert.assertTrue(time < 2000);
    }

    @Test(priority = 7)
    public void viewRoomById_contains101() {
        String body = freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomById/101")
            .asString();
        Assert.assertTrue(body.contains("101"));
    }

    @DataProvider(name = "roomTypes")
    public Object[][] roomTypes() {
        return TestDataLoader.loadRoomTypesFromJson("src/test/resources/testdata/roomTypes.json");
    }
    
    @Test(priority = 8)
    public void viewRoomByType_status200() {
        freshReq()
            .accept(ContentType.JSON)
            .get("/viewRoomByType")
            .then()
            .statusCode(200);
    }

    @Test(priority = 9, dataProvider = "roomTypes")
    public void viewRoomByType_matchesType(String type) {
        String body = freshReq()
            .accept(ContentType.JSON)
            .queryParam("roomType", type)
            .get("/viewRoomByType")
            .asString();
        Assert.assertTrue(body.contains(type), "Body should contain " + type);
    }

    @Test(priority = 10)
    public void addRoom_statusCodeIs200() {
        freshReq()
            .accept(ContentType.XML)
            .contentType(ContentType.URLENC)
            .formParam("roomId", "11")
            .formParam("hotelId", "1001a")
            .formParam("roomType", "SINGLE")
            .formParam("roomStatus", "AVAILABLE")
            .formParam("roomPrice", "20000")
            .post("/addRoom")
            .then()
            .statusCode(200);
    }

    @Test(priority = 11)
    public void addRoom_verifyRoomDetails() {
        Response r = freshReq()
            .accept(ContentType.XML)
            .contentType(ContentType.URLENC)
            .formParam("roomId", "11")
            .formParam("hotelId", "1001a")
            .formParam("roomType", "SINGLE")
            .formParam("roomStatus", "AVAILABLE")
            .formParam("roomPrice", "20000")
            .post("/addRoom")
            .then()
            .statusCode(200)
            .extract().response();

        String xml = r.asString();
        Assert.assertTrue(xml.startsWith("<?xml") || xml.startsWith("<"), "Response is expected XML");

        XmlPath xp = new XmlPath(xml);
        Assert.assertEquals(xp.getString("rooms.room.roomId"), "11");
        Assert.assertEquals(xp.getString("rooms.room.hotelId"), "1001a");
    }

    // Intentional Fail #2
    @Test(priority = 12)
    public void addRoom_invalidRoomShouldNotExist_Fail() {
        String invalidId = "999";
        String xml = freshReq()
            .accept(ContentType.XML)
            .get("/viewRoomList")
            .asString();
        Assert.assertTrue(xml.contains("<roomId>" + invalidId + "</roomId>"), "Intentional fail - invalid roomId should not exist");
    }

    @Test(priority = 13)
    public void updateRoomPrice_statusCodeIs200() {
        freshReq()
            .accept(ContentType.XML)
            .contentType(ContentType.URLENC)
            .formParam("roomId", "101")
            .formParam("roomPrice", "1500")
            .put("/updateRoomPrice")
            .then()
            .statusCode(200);
    }

    @Test(priority = 14)
    public void updateRoomPrice_verifyUpdatedPriceInResponse() {
        Response r = freshReq()
            .accept(ContentType.XML)
            .contentType(ContentType.URLENC)
            .formParam("roomId", "101")
            .formParam("roomPrice", "1500")
            .put("/updateRoomPrice")
            .then()
            .statusCode(200)
            .extract().response();

        String xml = r.asString();
        Assert.assertTrue(xml.startsWith("<?xml") || xml.startsWith("<"), "Response is expected XML");
        Assert.assertTrue(xml.contains("1500"), "Updated price should be present");
    }
    @Test(priority = 15)
    public void deleteRoomById_statusCodeIs200() {
        freshReq()
            .accept(ContentType.XML)
            .delete("/deleteRoomById/101")
            .then()
            .statusCode(200);
    }

    @Test(priority = 16)
    public void deleteRoomById_verifyRoomIsDeleted() {
        String xml = freshReq()
            .accept(ContentType.XML)
            .delete("/deleteRoomById/101")
            .then()
            .statusCode(200)
            .extract().asString();

        Assert.assertTrue(xml.startsWith("<?xml") || xml.startsWith("<"), "Response is expected XML");
        Assert.assertFalse(xml.contains("<roomId>101</roomId>"), "Room should be deleted");
    }

    // Intentional Fail #3
    @Test(priority = 17)
    public void deleteRoomById_roomStillExists_Fail() {
        String xml = freshReq()
            .accept(ContentType.XML)
            .get("/viewRoomList")
            .asString();
        Assert.assertTrue(xml.contains("<roomId>101</roomId>"), "Intentional fail - room 101 should not exist after deletion");
    }
}
package model;

public class Room {
    private String hotelId;
    private String roomId;
    private String roomType;
    private String roomStatus;
    private Double roomPrice;

    // getters & setters
    public String getHotelId() { return hotelId; }
    public void setHotelId(String hotelId) { this.hotelId = hotelId; }
    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    public String getRoomStatus() { return roomStatus; }
    public void setRoomStatus(String roomStatus) { this.roomStatus = roomStatus; }
    public Double getRoomPrice() { return roomPrice; }
    public void setRoomPrice(Double roomPrice) { this.roomPrice = roomPrice; }
}

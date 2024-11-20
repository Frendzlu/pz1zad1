import org.example.Hotel;
import org.example.Room;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class HotelTest {
    @Test
    public void testDefaultConstructor() {
        Hotel hotel = new Hotel();
        assertNotNull(hotel.hotelName);  // The hotel name should not be null
        assertTrue(hotel.hotelName.startsWith("new_hotel_"));  // The name should start with "new_hotel_"
    }
    @Test
    public void testLoadFromFile() {
        Hotel hotel = new Hotel();
        hotel.loadFromFileWithName("testhotels/sampleHotel");

        // Check if the hotel name was updated from the file
        assertEquals("testhotels/sampleHotel", hotel.hotelName);

        // Check if rooms are loaded (assuming the sample file has rooms)
        assertTrue(hotel.getRooms().size() > 0);

        // Check if a specific room exists (room details depend on the sample file)
        Room room = hotel.getRoom("101");
        assertNotNull(room);
        assertEquals("101", room.getRoomName());
    }
    @Test
    public void testSaveToFile() {
        Hotel hotel = new Hotel();
        hotel.setRooms(2, 3);  // Create a hotel with 2 floors and 3 rooms per floor

        // Create a temporary file to save the data
        File tempFile = new File("./testhotels/test_hotel.csv");
        hotel.saveToFile(tempFile, "yyyy-MM-dd");

        assertTrue(tempFile.exists());  // Check if the file is created

        // Check if the file contains expected headers
        assertTrue(tempFile.length() > 0);  // File should have some content
    }
    @Test
    public void testSetRooms() {
        Hotel hotel = new Hotel();
        hotel.setRooms(2, 10);  // Set up 2 floors, 5 rooms per floor

        // Check the number of rooms
        assertEquals(20, hotel.getRooms().size());

        // Check if rooms are named correctly
        Room room1 = hotel.getRoom("101");
        assertNotNull(room1);
        assertEquals("101", room1.getRoomName());

        Room room5 = hotel.getRoom("105");
        assertNotNull(room5);
        assertEquals("105", room5.getRoomName());
    }
    @Test
    public void testAddRoom() {
        Hotel hotel = new Hotel();
        Room room = new Room("200", "Luxury Suite", 300.0, 2, 1, 2, new ArrayList<>(), null, null);

        hotel.addRoom(room);

        // Check if the room was added
        Room addedRoom = hotel.getRoom("200");
        assertNotNull(addedRoom);
        assertEquals("200", addedRoom.getRoomName());
    }
    @Test
    public void testGetRoom() {
        Hotel hotel = new Hotel();
        hotel.setRooms(1, 111);  // Set up 1 floor with 2 rooms

        Room room = hotel.getRoom("1011");
        assertNotNull(room);
        assertEquals("1011", room.getRoomName());

        Room nonExistingRoom = hotel.getRoom("2001");
        assertNull(nonExistingRoom);  // Should return null if room does not exist
    }
    @Test
    public void testLoadFromFileWithMalformedCSV() {
        Hotel hotel = new Hotel();

        // Assuming "malformed.csv" is an invalid or incorrectly formatted CSV file
        assertThrows(RuntimeException.class, () -> {
            hotel.loadFromFileWithName("malformed");
        });
    }
    @Test
    public void testRoomToCSV() {
        Room room = new Room("101", "Standard Room", 100.0, 1, 1, 2, new ArrayList<>(), null, null);

        String[] csvRecord = room.toCSVFileRecord("yyyy-MM-dd");

        assertNotNull(csvRecord);
        assertEquals("101", csvRecord[0]);
        assertEquals("1", csvRecord[1]);
        assertEquals("1", csvRecord[2]);
        assertEquals("100.0", csvRecord[3]);
        assertEquals("2", csvRecord[4]);
        assertEquals("Standard Room", csvRecord[5]);
    }
}

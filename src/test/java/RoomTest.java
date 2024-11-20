import org.example.Guest;
import org.example.Room;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    public void testRoomConstructor() {
        Room room = new Room("101", "A luxury suite", 150.0, 1, 101, 2);

        // Verify the room details
        assertEquals("101", room.getRoomName());
        assertEquals("A luxury suite", room.getRoomDescription());
        assertEquals(150.0, room.getRoomPrice());
        assertEquals(1, room.getFloor());
        assertEquals(101, room.getRoomNumber());
        assertEquals(2, room.getCapacity());
        assertNotNull(room.getGuests());
    }

    @Test
    public void testAddGuest() {
        Room room = new Room("101", "A luxury suite", 150.0, 1, 101, 2);

        // Create guests
        Guest guest1 = new Guest("John", "Doe");
        Guest guest2 = new Guest("Jane", "Smith");
        Guest guest3 = new Guest("Alice", "Johnson");

        // Add two guests (capacity 2)
        room.addGuest(guest1);
        room.addGuest(guest2);

        // Verify the guests added
        assertEquals(2, room.getGuests().size());
        assertTrue(room.getGuests().contains(guest1));
        assertTrue(room.getGuests().contains(guest2));

        // Try adding a third guest
        room.addGuest(guest3);

        // Ensure the third guest is not added due to room capacity
        assertEquals(2, room.getGuests().size());
        assertFalse(room.getGuests().contains(guest3));
    }

    @Test
    public void testRemoveGuests() {
        Room room = new Room("101", "A luxury suite", 150.0, 1, 101, 2);

        // Create and add guests
        Guest guest1 = new Guest("John", "Doe");
        room.addGuest(guest1);

        // Verify guest is added
        assertEquals(1, room.getGuests().size());

        // Remove guests
        room.removeGuests();

        // Verify all guests are removed
        assertEquals(0, room.getGuests().size());
    }

    @Test
    public void testIsValidRoomName() {
        assertTrue(Room.isValidRoomName(1, "101"));
        assertTrue(Room.isValidRoomName(2, "201"));
        assertFalse(Room.isValidRoomName(1, "201"));
        assertFalse(Room.isValidRoomName(3, "102"));
    }

    @Test
    public void testToCSVFileRecord() {
        // Create a guest list
        Guest guest1 = new Guest("John", "Doe");
        Guest guest2 = new Guest("Jane", "Smith");

        // Create room with check-in and check-out dates
        Room room = new Room("101", "A luxury suite", 150.0, 1, 101, 2);
        room.addGuest(guest1);
        room.addGuest(guest2);
        room.setCheckInDate(LocalDate.of(2024, 11, 1));
        room.setCheckOutDate(LocalDate.of(2024, 11, 5));

        // Get CSV record
        String[] csvRecord = room.toCSVFileRecord("uuuu-MM-dd");

        // Verify the CSV record fields
        assertEquals("101", csvRecord[0]);
        assertEquals("1", csvRecord[1]);
        assertEquals("101", csvRecord[2]);
        assertEquals("150.0", csvRecord[3]);
        assertEquals("2", csvRecord[4]);
        assertEquals("A luxury suite", csvRecord[5]);
        assertEquals("2024-11-01", csvRecord[6]);
        assertEquals("2024-11-05", csvRecord[7]);
        assertEquals("John,Doe;Jane,Smith", csvRecord[8]);
    }

    @Test
    public void testDefaultRoomConstructor() {
        Room room = new Room("102", 102, 1);

        // Verify default values
        assertEquals("102", room.getRoomName());
        assertEquals("No description was provided", room.getRoomDescription());
        assertEquals(0.0, room.getRoomPrice());
        assertEquals(1, room.getFloor());
        assertEquals(102, room.getRoomNumber());
        assertEquals(0, room.getCapacity());
        assertEquals(0, room.getGuests().size());
    }
}
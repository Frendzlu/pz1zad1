import org.example.Guest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestTest {

    @Test
    public void testGuestConstructor() {
        // Given
        String name = "John";
        String surname = "Doe";

        // When
        Guest guest = new Guest(name, surname);

        // Then
        assertNotNull(guest, "Guest object should be created.");
        assertEquals("John", guest.name, "Name should be 'John'");
        assertEquals("Doe", guest.surname, "Surname should be 'Doe'");
    }

    @Test
    public void testToString() {
        // Given
        Guest guest = new Guest("John", "Doe");

        // When
        String result = guest.toString();

        // Then
        assertEquals("John Doe", result, "toString should return 'John Doe'");
    }

    @Test
    public void testEmptyConstructor() {
        // Given
        Guest guest = new Guest("", "");

        // When
        String result = guest.toString();

        // Then
        assertEquals(" ", result, "toString should return a single space for empty strings");
    }

    @Test
    public void testNullName() {
        // Given
        String surname = "Doe";
        Guest guest = new Guest(null, surname);

        // When
        String result = guest.toString();

        // Then
        assertEquals("null Doe", result, "toString should handle null name and return 'null Doe'");
    }

    @Test
    public void testNullSurname() {
        // Given
        String name = "John";
        Guest guest = new Guest(name, null);

        // When
        String result = guest.toString();

        // Then
        assertEquals("John null", result, "toString should handle null surname and return 'John null'");
    }

    @Test
    public void testNullNameAndSurname() {
        // Given
        Guest guest = new Guest(null, null);

        // When
        String result = guest.toString();

        // Then
        assertEquals("null null", result, "toString should handle both name and surname as null and return 'null null'");
    }

    @Test
    public void testSameNameAndSurname() {
        // Given
        String name = "John";
        String surname = "John";
        Guest guest = new Guest(name, surname);

        // When
        String result = guest.toString();

        // Then
        assertEquals("John John", result, "toString should return 'John John' when both name and surname are the same");
    }
}
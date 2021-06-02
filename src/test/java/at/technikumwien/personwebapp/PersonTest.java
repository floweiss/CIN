package at.technikumwien.personwebapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Unit test

public class PersonTest {
    private Person person;

    @BeforeEach
    public void setUp() {
        person = new Person(Sex.MALE, "Max", "Mustermann", LocalDate.of(1990, 1, 10), true);
    }

    @Test
    public void testGetName() {
        assertEquals("Max Mustermann", person.getName());
    }

    @Test
    public void testGetNameWithFirstNameNull() {
        person.setFirstName(null);

        // lambda function because otherwise the function return value would be used for assertThrows, not the exception occurring in function
        assertThrows(IllegalArgumentException.class, () -> person.getName());
    }

    @Test
    public void testGetNameWithFirstNameBlank() {
        person.setFirstName("");

        assertThrows(IllegalArgumentException.class, () -> person.getName());
    }

    // TODO: add more tests...
}

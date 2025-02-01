package com.example.swiftcodes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the CountryModel class.
 */
 class CountryModelTest {

    @Test
     void testDefaultConstructor() {
        // Create an instance using the default constructor
        CountryModel countryModel = new CountryModel();

        // Check if all fields are initialized with null or default values
        assertNull(countryModel.getIso2Code());
        assertNull(countryModel.getName());
        assertNull(countryModel.getTimeZone());
    }

    @Test
     void testParameterizedConstructor() {
        // Create an instance using the parameterized constructor
        CountryModel countryModel = new CountryModel("US", "United States", "America/New_York");

        // Check if the fields are correctly set
        assertEquals("US", countryModel.getIso2Code());
        assertEquals("United States", countryModel.getName());
        assertEquals("America/New_York", countryModel.getTimeZone());
    }

    @Test
     void testSettersAndGetters() {
        // Create an instance using the default constructor
        CountryModel countryModel = new CountryModel();

        // Set values using setters
        countryModel.setIso2Code("CA");
        countryModel.setName("Canada");
        countryModel.setTimeZone("America/Toronto");

        // Check if values are correctly set
        assertEquals("CA", countryModel.getIso2Code());
        assertEquals("Canada", countryModel.getName());
        assertEquals("America/Toronto", countryModel.getTimeZone());
    }

    @Test
     void testEquality() {
        // Create two instances with the same values
        CountryModel country1 = new CountryModel("CA", "Canada", "America/Toronto");
        CountryModel country2 = new CountryModel("CA", "Canada", "America/Toronto");

        // Test if the two objects are considered equal
        assertEquals(country1, country2);
    }

    @Test
     void testToString() {
        // Create an instance
        CountryModel countryModel = new CountryModel("CA", "Canada", "America/Toronto");

        // Check if the toString() method gives the expected output
        String expectedToString = "CountryModel(iso2Code=CA, name=Canada, timeZone=America/Toronto)";
        assertEquals(expectedToString, countryModel.toString());
    }
}
package com.example.swiftcodes.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the SwiftModel class.
 */
class SwiftModelTest {

    @Test
     void testDefaultConstructor() {
        // Create an instance using the default constructor
        SwiftModel swiftModel = new SwiftModel();

        // Check if all fields are initialized with null or default values
        assertNull(swiftModel.getSwiftCode());
        assertNull(swiftModel.getBankName());
        assertNull(swiftModel.getAddress());
        assertNull(swiftModel.getTownName());
        assertNull(swiftModel.getIso2Code());
        assertFalse(swiftModel.isHeadquarter());  // Default boolean value should be false
    }

    @Test
     void testParameterizedConstructor() {
        // Create an instance using the parameterized constructor
        SwiftModel swiftModel = new SwiftModel("SWIFT123", "Bank ABC", "123 Bank St.", "New York", "US", true);

        // Check if the fields are correctly set
        assertEquals("SWIFT123", swiftModel.getSwiftCode());
        assertEquals("Bank ABC", swiftModel.getBankName());
        assertEquals("123 Bank St.", swiftModel.getAddress());
        assertEquals("New York", swiftModel.getTownName());
        assertEquals("US", swiftModel.getIso2Code());
        assertTrue(swiftModel.isHeadquarter());  // The boolean flag should be true
    }

    @Test
     void testSettersAndGetters() {
        // Create an instance using the default constructor
        SwiftModel swiftModel = new SwiftModel();

        // Set values using setters
        swiftModel.setSwiftCode("SWIFT999");
        swiftModel.setBankName("Bank XYZ");
        swiftModel.setAddress("456 Street");
        swiftModel.setTownName("Los Angeles");
        swiftModel.setIso2Code("CA");
        swiftModel.setHeadquarter(false);

        // Check if values are correctly set
        assertEquals("SWIFT999", swiftModel.getSwiftCode());
        assertEquals("Bank XYZ", swiftModel.getBankName());
        assertEquals("456 Street", swiftModel.getAddress());
        assertEquals("Los Angeles", swiftModel.getTownName());
        assertEquals("CA", swiftModel.getIso2Code());
        assertFalse(swiftModel.isHeadquarter());  // The boolean flag should be false
    }

    @Test
     void testEquality() {
        // Create two instances with the same values
        SwiftModel swift1 = new SwiftModel("SWIFT123", "Bank ABC", "123 Bank St.", "New York", "US", true);
        SwiftModel swift2 = new SwiftModel("SWIFT123", "Bank ABC", "123 Bank St.", "New York", "US", true);

        // Test if the two objects are considered equal
        assertEquals(swift1, swift2);
    }

    @Test
     void testToString() {
        // Create an instance
        SwiftModel swiftModel = new SwiftModel("SWIFT123", "Bank ABC", "123 Bank St.", "New York", "US", true);

        // Check if the toString() method gives the expected output
        String expectedToString = "SwiftModel(swiftCode=SWIFT123, bankName=Bank ABC, address=123 Bank St., " +
                "townName=New York, iso2Code=US, isHeadquarter=true)";
        assertEquals(expectedToString, swiftModel.toString());
    }
}
package com.example.swiftcodes.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for the SwiftResponseDto class.
 */
 class SwiftResponseDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
     void testDefaultConstructor() {
        // Create an instance using the default constructor
        SwiftResponseDto swiftResponseDto = new SwiftResponseDto();

        // Verify that the default values are null or default (false for boolean)
        assertNull(swiftResponseDto.getSwiftCode());
        assertNull(swiftResponseDto.getBankName());
        assertNull(swiftResponseDto.getAddress());
        assertNull(swiftResponseDto.getIso2Code());
        assertNull(swiftResponseDto.getCountryName());
        assertFalse(swiftResponseDto.isHeadquarter()); // Default value of boolean should be false
        assertNull(swiftResponseDto.getBranches());
    }

    @Test
     void testParameterizedConstructor() {
        // Create an instance using the parameterized constructor
        SwiftResponseDto swiftResponseDto = new SwiftResponseDto("SWIFT123", "Bank ABC", "123 Bank St.",
                "US", "United States", true,
                List.of(new BranchDto("SWIFT123", "Branch 1", "address 1", "city 1", false)));

        // Verify that the fields are correctly set
        assertEquals("SWIFT123", swiftResponseDto.getSwiftCode());
        assertEquals("Bank ABC", swiftResponseDto.getBankName());
        assertEquals("123 Bank St.", swiftResponseDto.getAddress());
        assertEquals("US", swiftResponseDto.getIso2Code());
        assertEquals("United States", swiftResponseDto.getCountryName());
        assertTrue(swiftResponseDto.isHeadquarter());
        assertNotNull(swiftResponseDto.getBranches());
        assertEquals(1, swiftResponseDto.getBranches().size());
    }

    @Test
     void testSettersAndGetters() {
        // Create an instance using the default constructor
        SwiftResponseDto swiftResponseDto = new SwiftResponseDto();

        // Set values using setters
        swiftResponseDto.setSwiftCode("SWIFT123");
        swiftResponseDto.setBankName("Bank XYZ");
        swiftResponseDto.setAddress("456 Street");
        swiftResponseDto.setIso2Code("CA");
        swiftResponseDto.setCountryName("Canada");
        swiftResponseDto.setHeadquarter(true);
        swiftResponseDto.setBranches(List.of(new BranchDto("SWIFT456", "Branch 2", "address 2", "city 2", false)));

        // Verify that values are correctly set
        assertEquals("SWIFT123", swiftResponseDto.getSwiftCode());
        assertEquals("Bank XYZ", swiftResponseDto.getBankName());
        assertEquals("456 Street", swiftResponseDto.getAddress());
        assertEquals("CA", swiftResponseDto.getIso2Code());
        assertEquals("Canada", swiftResponseDto.getCountryName());
        assertTrue(swiftResponseDto.isHeadquarter());
        assertNotNull(swiftResponseDto.getBranches());
        assertEquals(1, swiftResponseDto.getBranches().size());
    }

    @Test
     void testJsonSerialization() throws Exception {
        // Create an instance using the parameterized constructor
        SwiftResponseDto swiftResponseDto = new SwiftResponseDto("SWIFT123", "Bank ABC", "123 Bank St.",
                "US", "United States", true,
                List.of(new BranchDto("SWIFT123", "Branch 1", "address 1", "city 1", false)));

        // Serialize the object to JSON
        String json = objectMapper.writeValueAsString(swiftResponseDto);

        // Verify that the JSON string contains expected values
        assertTrue(json.contains("SWIFT123"));
        assertTrue(json.contains("Bank ABC"));
        assertTrue(json.contains("123 Bank St."));
        assertTrue(json.contains("United States"));
        assertTrue(json.contains("true"));
    }

    @Test
     void testJsonDeserialization() throws Exception {
        // Define a JSON string that corresponds to the SwiftResponseDto
        String json = "{ \"swiftCode\": \"SWIFT123\", \"bankName\": \"Bank ABC\", \"address\": \"123 Bank St.\", " +
                "\"iso2Code\": \"US\", \"countryName\": \"United States\", \"isHeadquarter\": true } ";

        // Deserialize the JSON string to a SwiftResponseDto object
        SwiftResponseDto swiftResponseDto = objectMapper.readValue(json, SwiftResponseDto.class);

        // Verify the deserialized values
        assertEquals("SWIFT123", swiftResponseDto.getSwiftCode());
        assertEquals("Bank ABC", swiftResponseDto.getBankName());
        assertEquals("123 Bank St.", swiftResponseDto.getAddress());
        assertEquals("US", swiftResponseDto.getIso2Code());
        assertEquals("United States", swiftResponseDto.getCountryName());
        assertTrue(swiftResponseDto.isHeadquarter());
    }

    @Test
     void testEquality() {
        // Create two instances with the same values
        SwiftResponseDto swift1 = new SwiftResponseDto("SWIFT123", "Bank ABC", "123 Bank St.", "US",
                "United States", true,
                List.of(new BranchDto("SWIFT123", "Branch 1", "address 1", "city 1", false)));

        SwiftResponseDto swift2 = new SwiftResponseDto("SWIFT123", "Bank ABC", "123 Bank St.", "US",
                "United States", true,
                List.of(new BranchDto("SWIFT123", "Branch 1", "address 1", "city 1", false)));

        // Test if the two objects are considered equal
        assertEquals(swift1, swift2);
    }

    @Test
     void testToString() {
        // Create an instance
        SwiftResponseDto swiftResponseDto = new SwiftResponseDto("SWIFT123", "Bank ABC", "123 Bank St.",
                "US", "United States", true,
                List.of(new BranchDto("SWIFT123", "Branch 1", "address 1", "city 1", false)));

        // Check if the toString() method gives the expected output
        String expectedToString = "SwiftResponseDto(swiftCode=SWIFT123, bankName=Bank ABC, address=123 Bank St., iso2Code=US, countryName=United States, isHeadquarter=true, branches=[BranchDto(swiftCode=SWIFT123, bankName=Branch 1, address=address 1, countryISO2=city 1, isHeadquarter=false)])";
        assertEquals(expectedToString, swiftResponseDto.toString());
    }
}
package com.example.swiftcodes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class representing the response containing country details and its associated SWIFT codes.
 * This class is used to transfer country information and its related SWIFT codes.
 */
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
public class CountrySwiftResponseDto {

    /**
     * The ISO2 code of the country (2-letter code).
     */
    private String countryISO2;

    /**
     * The full name of the country.
     */
    private String countryName;

    /**
     * A list of BranchDto objects representing the SWIFT codes associated with the country.
     * Each BranchDto contains details of a specific SWIFT branch.
     */
    private List<BranchDto> swiftCodes;

}
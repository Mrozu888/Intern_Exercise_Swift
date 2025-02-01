package com.example.swiftcodes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) for representing a request to add or update a SWIFT code.
 * It includes details such as the address, bank name, country, and SWIFT code.
 * This class is used to transfer data when creating or updating SWIFT codes in the system.
 */
@Data // Lombok annotation to automatically generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Lombok annotation to generate a no-arguments constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with arguments for all fields
public class SwiftCodeRequestDto {

    /**
     * The address of the bank.
     * This field stores the physical address where the bank is located.
     */
    private String address;

    /**
     * The name of the bank.
     * This field holds the name of the bank that owns the SWIFT code.
     */
    private String bankName;

    /**
     * The ISO2 country code (2-letter code) where the bank is located.
     * Example: "US" for United States, "GB" for Great Britain.
     */
    private String countryISO2;

    /**
     * The name of the country where the bank is located.
     * Example: "United States", "Germany".
     */
    private String countryName;

    /**
     * Flag indicating whether the bank is a headquarter.
     * This field is used to specify if the bank with the given SWIFT code is the head office.
     */
    @JsonProperty("isHeadquarter") // Custom name for this field in the JSON response
    private boolean isHeadquarter;

    /**
     * The SWIFT code for the bank.
     * This is the unique identifier for the bank used for international transactions.
     */
    private String swiftCode;

}
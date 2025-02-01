package com.example.swiftcodes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) for representing the details of a bank branch.
 * This includes the SWIFT code, bank name, address, country code, and whether the branch is a headquarters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchDto {

    /**
     * The SWIFT code associated with the bank branch.
     */
    private String swiftCode;

    /**
     * The name of the bank associated with the SWIFT code.
     */
    private String bankName;

    /**
     * The address of the bank branch.
     */
    private String address;

    /**
     * The ISO 2-letter code of the country where the bank branch is located.
     */
    private String countryISO2;

    /**
     * Flag indicating whether the branch is the headquarters of the bank.
     *
     * This field is mapped to the JSON property "isHeadquarter".
     */
    @JsonProperty("isHeadquarter")
    private boolean isHeadquarter;
}
package com.example.swiftcodes.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for representing the details of a SWIFT code response.
 * It includes information about the SWIFT code, bank, country, and branches.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Excludes null fields from the JSON response
public class SwiftResponseDto {

    private String swiftCode;       // The SWIFT code for the bank
    private String bankName;        // The name of the bank associated with the SWIFT code
    private String address;         // The address of the bank
    private String iso2Code;        // ISO 2 code for the country
    private String countryName;     // The name of the country associated with the SWIFT code

    @JsonProperty("isHeadquarter") // Custom name for the field in the JSON response
    private boolean isHeadquarter;  // Boolean flag indicating whether it's a headquarter

    private List<BranchDto> branches; // A list of branches if it's a headquarter



}
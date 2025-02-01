package com.example.swiftcodes.controller;

import com.example.swiftcodes.dto.*;
import com.example.swiftcodes.model.SwiftModel;
import com.example.swiftcodes.repository.SwiftRepository;
import com.example.swiftcodes.service.ExcelImportService;
import com.example.swiftcodes.service.SwiftCodeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controller for managing SWIFT code operations such as retrieving, adding, deleting, and uploading data.
 * Provides RESTful API endpoints for interacting with SWIFT code information.
 */
@Api(value = "SWIFT Code API")
@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftController {

    private static final Logger logger = LoggerFactory.getLogger(SwiftController.class);

    private final SwiftRepository swiftRepository;
    private final SwiftCodeService swiftCodeService;
    private final ExcelImportService excelImportService;

    /**
     * Constructor-based injection for services and repository.
     * @param swiftRepository Repository for interacting with SWIFT codes in the database.
     * @param swiftCodeService Service for handling business logic related to SWIFT codes.
     * @param excelImportService Service for handling Excel data import operations.
     */
    @Autowired
    public SwiftController(SwiftRepository swiftRepository,
                           SwiftCodeService swiftCodeService,
                           ExcelImportService excelImportService) {
        this.swiftRepository = swiftRepository;
        this.swiftCodeService = swiftCodeService;
        this.excelImportService = excelImportService;
    }

    /**
     * Fetch details of a specific SWIFT code, including the associated bank and country information.
     * @param swiftCode The SWIFT code to retrieve details for.
     * @return ResponseEntity containing SWIFT code details or an error message if not found.
     */
    @ApiOperation(value = "Get SWIFT code details", notes = "Fetches details of a specific SWIFT code.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved SWIFT code details."),
            @ApiResponse(code = 404, message = "SWIFT code not found.")
    })
    @GetMapping("/{swiftCode}")
    public ResponseEntity<Object> getSwiftDetails(@PathVariable String swiftCode) {
        try {
            // Retrieve SWIFT code details from the repository
            SwiftResponseDto result = swiftRepository.findBySwiftCodeWithCountry(swiftCode);

            // If SWIFT code is not found, return 404 with a MessageResponse
            if (result == null) {
                MessageResponse messageResponse = new MessageResponse("SWIFT code not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
            }

            // If SWIFT code is a headquarter, fetch the associated branches
            if (result.isHeadquarter()) {
                List<BranchDto> branches = swiftRepository.findBranchesBySwiftPrefix(swiftCode);
                result.setBranches(branches);
            } else {
                result.setBranches(null);
            }

            // Return the SWIFT code details
            return ResponseEntity.ok(result);

        } catch (Exception e) {
            logger.error("Error fetching SWIFT code details: {}", e.getMessage(), e);
            // Return error response in case of failure
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error fetching SWIFT code details."));
        }
    }

    /**
     * Fetch all SWIFT codes for a given country by its ISO2 code.
     * @param countryISO2code The ISO2 country code to retrieve SWIFT codes for.
     * @return ResponseEntity containing a list of SWIFT codes for the country or an error message if not found.
     */
    @ApiOperation(value = "Get all SWIFT codes for a country", notes = "Fetches all SWIFT codes for a given country based on its ISO2 code.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully retrieved SWIFT codes for the country."),
            @ApiResponse(code = 404, message = "Country not found.")
    })
    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<Object> getSwiftCodesByCountry(@PathVariable String countryISO2code) {
        try {
            // Retrieve country name by ISO2 code
            String countryName = swiftRepository.findCountryNameByISO2(countryISO2code);

            // If country is not found, return 404 with a MessageResponse
            if (countryName == null) {
                MessageResponse messageResponse = new MessageResponse("Country not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
            }

            // Retrieve the SWIFT codes for the country
            List<BranchDto> swiftCodes = swiftRepository.findSwiftCodesByCountryISO2(countryISO2code);
            CountrySwiftResponseDto response = new CountrySwiftResponseDto(countryISO2code, countryName, swiftCodes);

            // Return the response with status 200
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching SWIFT codes for country {}: {}", countryISO2code, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error fetching SWIFT codes for country."));
        }
    }

    /**
     * Add a new SWIFT code to the database.
     * @param body JSON request body containing the SWIFT code data.
     * @return ResponseEntity with a success or error message.
     */
    @ApiOperation(value = "Add a new SWIFT code", notes = "Adds a new SWIFT code to the database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SWIFT code added successfully."),
            @ApiResponse(code = 400, message = "Invalid request data.")
    })
    @PostMapping
    public ResponseEntity<MessageResponse> addSwiftCode(@RequestBody String body) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the incoming request body into a DTO
            SwiftCodeRequestDto swiftCodeRequestDto = objectMapper.readValue(body, SwiftCodeRequestDto.class);

            // Map the DTO to the model and save it to the repository
            SwiftModel swiftModel = new SwiftModel();
            swiftModel.setAddress(swiftCodeRequestDto.getAddress());
            swiftModel.setBankName(swiftCodeRequestDto.getBankName());
            swiftModel.setIso2Code(swiftCodeRequestDto.getCountryISO2());
            swiftModel.setHeadquarter(swiftCodeRequestDto.isHeadquarter());
            swiftModel.setSwiftCode(swiftCodeRequestDto.getSwiftCode());

            swiftRepository.save(swiftModel);

            // Return success response
            return ResponseEntity.ok().body(new MessageResponse("SWIFT code added successfully."));
        } catch (Exception e) {
            logger.error("Error adding SWIFT code: {}", e.getMessage(), e);
            // Return error response for invalid request data
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Invalid request data."));
        }
    }

    /**
     * Delete an existing SWIFT code from the database.
     * @param swiftCode The SWIFT code to delete.
     * @return ResponseEntity with success or error message.
     */
    @ApiOperation(value = "Delete a SWIFT code", notes = "Deletes a SWIFT code from the database.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "SWIFT code deleted successfully."),
            @ApiResponse(code = 404, message = "SWIFT code not found.")
    })
    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<MessageResponse> deleteSwiftCode(@PathVariable String swiftCode) {
        try {
            // Try to delete the SWIFT code
            boolean isDeleted = swiftCodeService.deleteSwiftCode(swiftCode);

            // Return success or failure message
            if (isDeleted) {
                return ResponseEntity.ok().body(new MessageResponse("SWIFT code deleted successfully."));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("SWIFT code not found."));
            }
        } catch (Exception e) {
            logger.error("Error deleting SWIFT code {}: {}", swiftCode, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error deleting SWIFT code."));
        }
    }

    /**
     * Upload an Excel file containing SWIFT code data.
     * @param file The Excel file to upload.
     * @return ResponseEntity with success or error message.
     */
    @ApiOperation(value = "Upload an Excel file", notes = "Uploads an Excel file with SWIFT code data.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Data uploaded successfully."),
            @ApiResponse(code = 400, message = "Error uploading file.")
    })
    @PostMapping("/import")
    public ResponseEntity<MessageResponse> uploadExcel(@RequestParam("file") MultipartFile file) {
        try {
            // Process the uploaded Excel file
            excelImportService.importExcelData(file);

            // Return success response
            return ResponseEntity.ok().body(new MessageResponse("Data uploaded successfully."));
        } catch (Exception e) {
            logger.error("Error uploading Excel file: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error uploading file: " + e.getMessage()));
        }
    }
}
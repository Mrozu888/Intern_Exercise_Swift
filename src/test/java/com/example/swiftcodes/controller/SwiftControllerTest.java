package com.example.swiftcodes.controller;

import com.example.swiftcodes.dto.*;
import com.example.swiftcodes.model.SwiftModel;
import com.example.swiftcodes.repository.SwiftRepository;
import com.example.swiftcodes.service.ExcelImportService;
import com.example.swiftcodes.service.SwiftCodeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(MockitoExtension.class)
class SwiftControllerTest {

    @Mock
    private SwiftRepository swiftRepository;

    @Mock
    private SwiftCodeService swiftCodeService;

    @Mock
    private ExcelImportService excelImportService;

    @InjectMocks
    private SwiftController swiftController;

    @Test
    void testGetSwiftDetails_Found() {
        String swiftCode = "ABCDEF12";
        SwiftResponseDto dto = new SwiftResponseDto(swiftCode, "Test Bank", "address", "US","United States", true, Collections.emptyList());
        when(swiftRepository.findBySwiftCodeWithCountry(swiftCode)).thenReturn(dto);

        ResponseEntity<Object> response = swiftController.getSwiftDetails(swiftCode);

        assertEquals(OK, response.getStatusCode());
        assertInstanceOf(SwiftResponseDto.class, response.getBody());
    }

    @Test
    void testGetSwiftDetails_NotFound() {
        String swiftCode = "XYZ12345";
        when(swiftRepository.findBySwiftCodeWithCountry(swiftCode)).thenReturn(null);

        ResponseEntity<Object> response = swiftController.getSwiftDetails(swiftCode);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertInstanceOf(MessageResponse.class, response.getBody());
    }

    @Test
    void testGetSwiftCodesByCountry_Found() {
        String countryISO2 = "US";
        String countryName = "United States";
        when(swiftRepository.findCountryNameByISO2(countryISO2)).thenReturn(countryName);
        when(swiftRepository.findSwiftCodesByCountryISO2(countryISO2)).thenReturn(List.of(new BranchDto("ABCDEF12", "Bank", "Address",countryISO2,false)));

        ResponseEntity<Object> response = swiftController.getSwiftCodesByCountry(countryISO2);

        assertEquals(OK, response.getStatusCode());
        assertInstanceOf(CountrySwiftResponseDto.class, response.getBody());
    }

    @Test
    void testGetSwiftCodesByCountry_NotFound() {
        String countryISO2 = "XX";
        when(swiftRepository.findCountryNameByISO2(countryISO2)).thenReturn(null);

        ResponseEntity<Object> response = swiftController.getSwiftCodesByCountry(countryISO2);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertInstanceOf(MessageResponse.class, response.getBody());
    }

    @Test
    void testAddSwiftCode_Success() {
        String json = """
                {
                    "address": "loooool",
                    "bankName": "SANTANDER BANK SPOLKA AKCYJNA",
                    "countryISO2": "PL",
                    "countryName": "POLAND",
                    "isHeadquarter": true,
                    "swiftCode": "AAAAAAABXXX"
                }
                """;
        ResponseEntity<MessageResponse> response = swiftController.addSwiftCode(json);

        assertEquals(OK, response.getStatusCode());
        assertEquals("SWIFT code added successfully.", response.getBody().getMessage());
        verify(swiftRepository, times(1)).save(any(SwiftModel.class));
    }

    @Test
    void testAddSwiftCode_InvalidData() {
        String json = "invalid_json";
        ResponseEntity<MessageResponse> response = swiftController.addSwiftCode(json);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request data.", response.getBody().getMessage());
    }

    @Test
    void testDeleteSwiftCode_Success() {
        String swiftCode = "ABCDEF12";
        when(swiftCodeService.deleteSwiftCode(swiftCode)).thenReturn(true);

        ResponseEntity<MessageResponse> response = swiftController.deleteSwiftCode(swiftCode);

        assertEquals(OK, response.getStatusCode());
        assertEquals("SWIFT code deleted successfully.", response.getBody().getMessage());
    }

    @Test
    void testDeleteSwiftCode_NotFound() {
        String swiftCode = "XYZ12345";
        when(swiftCodeService.deleteSwiftCode(swiftCode)).thenReturn(false);

        ResponseEntity<MessageResponse> response = swiftController.deleteSwiftCode(swiftCode);

        assertEquals(NOT_FOUND, response.getStatusCode());
        assertEquals("SWIFT code not found.", response.getBody().getMessage());
    }

    @Test
    void testUploadExcel_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);
        ResponseEntity<MessageResponse> response = swiftController.uploadExcel(file);

        assertEquals(OK, response.getStatusCode());
        assertEquals("Data uploaded successfully.", response.getBody().getMessage());
        verify(excelImportService, times(1)).importExcelData(file);
    }

    @Test
    void testUploadExcel_Failure() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);
        doThrow(new RuntimeException("File error")).when(excelImportService).importExcelData(file);

        ResponseEntity<MessageResponse> response = swiftController.uploadExcel(file);

        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Error uploading file"));
    }
}

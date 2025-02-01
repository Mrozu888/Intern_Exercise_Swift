package com.example.swiftcodes.service;

import com.example.swiftcodes.repository.SwiftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

 class SwiftCodeServiceTest {

    @Mock
    private SwiftRepository swiftRepository;

    @InjectMocks
    private SwiftCodeService swiftCodeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testDeleteSwiftCode_Success() {
        // Given
        String swiftCode = "SWIFT123";
        when(swiftRepository.existsById(swiftCode)).thenReturn(true); // Mock existsById to return true

        // When
        boolean result = swiftCodeService.deleteSwiftCode(swiftCode);

        // Then
        verify(swiftRepository, times(1)).deleteById(swiftCode); // Verify deleteById is called once
        assertTrue(result); // Assert that the result is true
    }

    @Test
     void testDeleteSwiftCode_NotFound() {
        // Given
        String swiftCode = "SWIFT999";
        when(swiftRepository.existsById(swiftCode)).thenReturn(false); // Mock existsById to return false

        // When
        boolean result = swiftCodeService.deleteSwiftCode(swiftCode);

        // Then
        verify(swiftRepository, times(0)).deleteById(swiftCode); // Verify deleteById is not called
        assertFalse(result); // Assert that the result is false
    }
}
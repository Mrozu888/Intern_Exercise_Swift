package com.example.swiftcodes.service;

import com.example.swiftcodes.repository.SwiftRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for handling operations related to SWIFT codes.
 * Provides functionality to delete a SWIFT code from the database.
 */
@Service
public class SwiftCodeService {

    private final SwiftRepository swiftRepository;

    /**
     * Constructor for SwiftCodeService.
     *
     * @param swiftRepository The repository for interacting with the SWIFT code data.
     */
    public SwiftCodeService(SwiftRepository swiftRepository) {
        this.swiftRepository = swiftRepository;
    }

    /**
     * Deletes a SWIFT code from the database if it exists.
     *
     * <p>This method checks if a SWIFT code exists in the database. If it does, the code is deleted.
     * If the SWIFT code is not found, it returns {@code false} indicating that the deletion was unsuccessful.</p>
     *
     * @param swiftCode The SWIFT code to be deleted.
     * @return {@code true} if the SWIFT code was successfully deleted, otherwise {@code false}.
     */
    public boolean deleteSwiftCode(String swiftCode) {
        // Check if the record with the given SWIFT code exists
        if (swiftRepository.existsById(swiftCode)) {
            // Delete the record if it exists
            swiftRepository.deleteById(swiftCode);
            return true; // Return true if the SWIFT code was deleted
        }
        // Return false if the SWIFT code does not exist
        return false;
    }
}
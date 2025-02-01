package com.example.swiftcodes.repository;

import com.example.swiftcodes.dto.BranchDto;
import com.example.swiftcodes.dto.SwiftResponseDto;
import com.example.swiftcodes.model.SwiftModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SwiftRepository extends CrudRepository<SwiftModel, String> {

    @Query("SELECT new com.example.swiftcodes.dto.SwiftResponseDto(s.swiftCode, s.bankName, s.address, " +
            "c.iso2Code, c.name, s.isHeadquarter, null) " +
            "FROM SwiftModel s JOIN CountryModel c ON s.iso2Code = c.iso2Code " +
            "WHERE s.swiftCode = :swiftCode")
    SwiftResponseDto findBySwiftCodeWithCountry(String swiftCode);

    // Pobieramy listę oddziałów jako BranchDto (bez countryName)
    @Query("SELECT new com.example.swiftcodes.dto.BranchDto(s.swiftCode, s.bankName, s.address, " +
            "c.iso2Code, s.isHeadquarter) " +
            "FROM SwiftModel s JOIN CountryModel c ON s.iso2Code = c.iso2Code " +
            "WHERE SUBSTRING(s.swiftCode, 1, 8) = SUBSTRING(:swiftCode, 1, 8) " +
            "AND s.swiftCode <> :swiftCode")
    List<BranchDto> findBranchesBySwiftPrefix(String swiftCode);


    // Zapytanie do bazy o nazwę kraju na podstawie iso2Code
    @Query("SELECT c.name FROM CountryModel c WHERE c.iso2Code = :countryISO2")
    String findCountryNameByISO2(String countryISO2);

    // Pobieramy wszystkie SWIFT code dla danego kraju
    @Query("SELECT new com.example.swiftcodes.dto.BranchDto(s.swiftCode, s.bankName, s.address, " +
            "s.iso2Code, s.isHeadquarter) " +
            "FROM SwiftModel s WHERE s.iso2Code = :countryISO2")
    List<BranchDto> findSwiftCodesByCountryISO2(String countryISO2);
}
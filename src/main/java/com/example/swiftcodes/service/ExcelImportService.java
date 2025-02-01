package com.example.swiftcodes.service;

import com.example.swiftcodes.model.CountryModel;
import com.example.swiftcodes.model.SwiftModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for importing SWIFT code and country data from an Excel file.
 * The data is parsed and then saved into the database using JdbcTemplate.
 */
@Service
public class ExcelImportService {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Constructor for injecting the JdbcTemplate dependency.
     * This ensures that the JdbcTemplate is provided at object creation time, promoting best practices for testability and immutability.
     *
     * @param jdbcTemplate the JdbcTemplate used for database operations
     */
    public ExcelImportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This method reads data from the provided Excel file and processes it.
     * The data is parsed into lists of CountryModel and SwiftModel objects before being saved to the database.
     *
     * @param file the MultipartFile that contains the Excel data to be imported
     * @throws IOException if an error occurs while reading the file
     */
    public void importExcelData(MultipartFile file) throws IOException {
        // Lists to store parsed country and swift data before saving to the database
        List<CountryModel> countryList = new ArrayList<>();
        List<SwiftModel> swiftList = new ArrayList<>();

        // Reading the Excel file using Apache POI
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream); // Create a Workbook object from the Excel file
            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet from the Excel file

            // Iterate over all rows (starting from 1 to skip the header row)
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row row = sheet.getRow(i);

                if (row != null) {
                    // Extract data from each cell in the row
                    String countryIso2Code = row.getCell(0).getStringCellValue();
                    String swiftCode = row.getCell(1).getStringCellValue();
                    String bankName = row.getCell(3).getStringCellValue();
                    String address = row.getCell(4).getStringCellValue();
                    String townName = row.getCell(5).getStringCellValue();
                    String countryName = row.getCell(6).getStringCellValue();
                    String timeZone = row.getCell(7).getStringCellValue();
                    boolean isHeadquarter = swiftCode.endsWith("XXX"); // Determine if the SWIFT code indicates a headquarter

                    // Create CountryModel and SwiftModel objects based on the extracted data
                    CountryModel country = new CountryModel(countryIso2Code, countryName, timeZone);
                    countryList.add(country);

                    SwiftModel swift = new SwiftModel(swiftCode, bankName, address, townName, countryIso2Code, isHeadquarter);
                    swiftList.add(swift);
                }
            }
        }

        // Save the parsed country and SWIFT data into the database
        saveCountries(countryList);
        saveSwifts(swiftList);
    }

    /**
     * Saves a list of CountryModel objects into the database.
     * If the country already exists (based on the ISO2 code), no action is taken.
     *
     * @param countryList the list of CountryModel objects to be saved
     */
    private void saveCountries(List<CountryModel> countryList) {
        for (CountryModel country : countryList) {
            // Insert country data into the database (if it doesn't already exist)
            jdbcTemplate.update("INSERT INTO country_model (iso2code, name, time_zone) VALUES (?, ?, ?) " +
                            "ON CONFLICT (iso2code) DO NOTHING",
                    country.getIso2Code(), country.getName(), country.getTimeZone());
        }
    }

    /**
     * Saves a list of SwiftModel objects into the database.
     * If the SWIFT code already exists, no action is taken.
     *
     * @param swiftList the list of SwiftModel objects to be saved
     */
    private void saveSwifts(List<SwiftModel> swiftList) {
        for (SwiftModel swift : swiftList) {
            // Insert SWIFT data into the database (if it doesn't already exist)
            jdbcTemplate.update("INSERT INTO swift_model (swift_code, bank_name, address, town_name, iso2code, is_headquarter) " +
                            "VALUES (?, ?, ?, ?, ?, ?) ON CONFLICT (swift_code) DO NOTHING",
                    swift.getSwiftCode(), swift.getBankName(), swift.getAddress(), swift.getTownName(), swift.getIso2Code(), swift.isHeadquarter());
        }
    }
}
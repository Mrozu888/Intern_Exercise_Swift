package com.example.swiftcodes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Klasa reprezentująca model kraju.
 * Zawiera informacje o kodzie ISO2, nazwie kraju oraz strefie czasowej.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryModel {

    /**
     * Kod ISO2 kraju (unikalny identyfikator kraju).
     */
    @Id
    private String iso2Code;

    /**
     * Nazwa kraju.
     */
    private String name;

    /**
     * Strefa czasowa kraju.
     */
    private String timeZone;

}
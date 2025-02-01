package com.example.swiftcodes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model reprezentujący dane SWIFT.
 * Zawiera informacje o kodzie SWIFT, nazwie banku, adresie, mieście, kodzie kraju i informacji o centrali banku.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwiftModel {

    /**
     * Kod SWIFT (unikalny identyfikator banku).
     */
    @Id
    private String swiftCode;

    /**
     * Nazwa banku.
     */
    private String bankName;

    /**
     * Adres banku.
     */
    private String address;

    /**
     * Nazwa miasta, w którym znajduje się bank.
     */
    private String townName;

    /**
     * Kod ISO2 kraju, w którym znajduje się bank.
     */
    private String iso2Code;

    /**
     * Flaga oznaczająca, czy bank jest centralą.
     */
    private boolean isHeadquarter;

}
package com.example.swiftcodes.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO for sending a response message.
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {

    private String message;

}
package com.g4bor.payment.integration.acquire_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
/**
 * Used for returning custom response such as an error message or other custom information
 */
public class MessageResponse {

    private String message;
    private List<String> details;
}

package com.g4bor.payment.acquirer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
/**
 * Used for returning custom response such as an error message or other custom information
 */
public class InfoResponse {

    private String message;
    private List<String> details;
}

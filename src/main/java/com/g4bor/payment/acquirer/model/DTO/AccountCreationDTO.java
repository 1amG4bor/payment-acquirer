package com.g4bor.payment.acquirer.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreationDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String currencyCode;
}

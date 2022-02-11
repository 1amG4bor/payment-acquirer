package com.g4bor.payment.entity.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC, setterPrefix = "with")
public class AccountRegistrationDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String currency;
}

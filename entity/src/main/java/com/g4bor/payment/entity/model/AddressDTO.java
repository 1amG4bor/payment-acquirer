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
public class AddressDTO {
    private String Country;
    private String Region;
    private String zipCode;
    private String City;
    private String Street;
    private String additionalInfo;
}

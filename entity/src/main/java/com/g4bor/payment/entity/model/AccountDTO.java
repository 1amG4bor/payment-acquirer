package com.g4bor.payment.entity.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(access = AccessLevel.PUBLIC, setterPrefix = "with")
public class AccountDTO {
    private UUID accountId;
    private String username;
    private String firstName;
    private String lastName;
    private AddressDTO address;
    private String idNumber;

    private Set<Long> walletIds = new HashSet<>();
}

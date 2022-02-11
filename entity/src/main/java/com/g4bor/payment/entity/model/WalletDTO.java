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
public class WalletDTO {
    private Long id;
    private Currency currency;
    private double balance;

    public WalletDTO(Currency currency, double balance) {
        this.currency = currency;
        this.balance = balance;
    }
}

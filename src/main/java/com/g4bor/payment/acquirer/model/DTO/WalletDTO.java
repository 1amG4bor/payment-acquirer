package com.g4bor.payment.acquirer.model.DTO;

import com.g4bor.payment.acquirer.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {
    private Currency currency;
    private double balance;
}

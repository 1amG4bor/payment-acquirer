package com.g4bor.payment.acquirer.converter;

import com.g4bor.payment.acquirer.model.Account;
import com.g4bor.payment.acquirer.model.Currency;
import com.g4bor.payment.acquirer.model.DTO.AccountCreationDTO;
import com.g4bor.payment.acquirer.model.Wallet;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountConverter {

    public Account creationDtoToAccount(AccountCreationDTO dto) {
        Wallet defaultWallet = new Wallet(Currency.resolve(dto.getCurrencyCode()), 0.0);
        return Account.builder()
                .username(dto.getUsername())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .wallets(Set.of(defaultWallet))
                .build();
    }
}

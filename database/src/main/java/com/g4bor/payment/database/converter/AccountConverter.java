package com.g4bor.payment.database.converter;

import com.g4bor.payment.database.model.Account;
import com.g4bor.payment.database.model.Address;
import com.g4bor.payment.database.model.Wallet;
import com.g4bor.payment.entity.model.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountConverter {

    AddressConverter addressConverter;

    public AccountConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    public Account registrationDtoToEntity(AccountRegistrationDTO dto) {
        Wallet defaultWallet = new Wallet(Currency.resolve(dto.getCurrency()), 0.0);
        return Account.builder()
                .withUsername(dto.getUsername())
                .withFirstName(dto.getFirstname())
                .withLastName(dto.getLastname())
                .withWallets(Set.of(defaultWallet))
                .build();
    }

    public AccountDTO entityToDTO(Account entity) {
        AddressDTO addressDto = addressConverter.entityToDTO(entity.getAddress());
        Set<Long> walletIds = entity.getWallets().stream()
                .map(Wallet::getId)
                .collect(Collectors.toSet());

        return AccountDTO.builder()
                .withAccountId(entity.getAccountId())
                .withFirstName(entity.getFirstName())
                .withLastName(entity.getLastName())
                .withAddress(addressDto)
                .withIdNumber(entity.getIdNumber())
                .withWalletIds(walletIds)
                .build();
    }

    public Account dtoToEntity(AccountDTO dto) {
        Address address = addressConverter.dtoToEntity(dto.getAddress());

        return Account.builder()
                .withAccountId(dto.getAccountId())
                .withUsername(dto.getUsername())
                .withFirstName(dto.getFirstName())
                .withLastName(dto.getLastName())
                .withAddress(address)
                .withIdNumber(dto.getIdNumber())
                .build();
    }
}

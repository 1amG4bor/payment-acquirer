package com.g4bor.payment.database.converter;

import com.g4bor.payment.database.model.Wallet;
import com.g4bor.payment.entity.model.WalletDTO;

public class WalletConverter {

    public WalletDTO entityToDTO(Wallet entity) {
        return new WalletDTO(entity.getId(), entity.getCurrency(), entity.getBalance());
    }

    public Wallet dtoToEntity(WalletDTO dto) {
        Wallet entity = new Wallet();
        entity.setId(dto.getId());
        entity.setCurrency(dto.getCurrency());
        entity.setBalance(dto.getBalance());
        return entity;
    }


}

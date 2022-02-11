package com.g4bor.payment.integration.acquire_service.service;

import com.g4bor.payment.database.manager.WalletManager;
import com.g4bor.payment.database.model.Wallet;
import com.g4bor.payment.entity.model.WalletDTO;
import com.g4bor.payment.database.converter.WalletConverter;
import com.g4bor.payment.integration.acquire_service.exception.RecordNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {

    private final WalletManager walletManager;
    private final WalletConverter walletConverter;

    public WalletService(WalletManager walletManager, WalletConverter walletConverter) {
        this.walletManager = walletManager;
        this.walletConverter = walletConverter;
    }

    // region > Read
    public List<WalletDTO> getWallets(List<Long> ids) {
        List<Wallet> wallets = walletManager.getWallets(ids);
        if (wallets.isEmpty()) {
            throw new RecordNotFoundException("None of the provided ids can be found!");
        }

        return wallets.stream()
                .map(walletConverter::entityToDTO)
                .collect(Collectors.toList());
    }

    public WalletDTO getWalletById(Long id) {
        Wallet wallet = walletManager.getWalletById(id);
        return walletConverter.entityToDTO(wallet);
    }

    public Double getBalanceById(Long id) {
        return walletManager.getBalanceById(id);
    }
    // endregion

    // region > Update
    public Double updateBalance(Long id, Double amount) {
        Double newBalance = walletManager.updateBalance(id, amount);
        return newBalance;
    }
    //endregion

    // region > Delete
    public void deleteWalletById(Long id) {
        walletManager.deleteWalletById(id);
    }
    // endregion
}

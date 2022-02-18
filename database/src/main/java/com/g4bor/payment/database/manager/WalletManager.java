package com.g4bor.payment.database.manager;

import com.g4bor.payment.database.exception.ErrorMsg;
import com.g4bor.payment.database.exception.EntityNotFoundException;
import com.g4bor.payment.database.exception.InsufficientFundsException;
import com.g4bor.payment.database.model.Wallet;
import com.g4bor.payment.database.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class WalletManager {

    private final WalletRepository walletRepository;

    public WalletManager(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletById(Long id) {
        Wallet wallet = walletRepository.findWalletById(id);
        if (null == wallet) {
            throw new EntityNotFoundException(ErrorMsg.WALLET_NOT_FOUND.formatMessage(id.toString()));
        }
        return wallet;
    }
    public List<Wallet> getWallets(List<Long> ids) {
        return walletRepository.findAllById(ids).stream()
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public void deleteWalletById(Long id) {
        walletRepository.deleteById(id);
    }
    public Double getBalanceById(Long id) {
        return walletRepository.findBalanceById(id);
    }
    public Double updateBalance(Long id, Double amount) {
        Wallet wallet = walletRepository.findWalletById(id);
        if (null == wallet) {
            throw new EntityNotFoundException(ErrorMsg.WALLET_NOT_FOUND.formatMessage(id.toString()));
        }

        Double balance = wallet.getBalance();
        if ( balance + amount < 0.0) {
            throw new InsufficientFundsException();
        }

        wallet.setBalance(balance + amount);
        walletRepository.save(wallet);
        return wallet.getBalance();
    }
}

package com.g4bor.payment.acquirer.service;

import com.g4bor.payment.acquirer.exception.CustomError;
import com.g4bor.payment.acquirer.exception.InsufficientFundsException;
import com.g4bor.payment.acquirer.exception.RecordNotFoundException;
import com.g4bor.payment.acquirer.model.Wallet;
import com.g4bor.payment.acquirer.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWalletById(Long id) {
        Wallet wallet = walletRepository.findWalletById(id);
        if (null == wallet) {
            throw new RecordNotFoundException(CustomError.WALLET_NOT_FOUND.formatMessage(id.toString()));
        }
        return wallet;
    }

    public List<Wallet> getWallets(List<Long> ids) {
        return walletRepository.findAllById(ids);
    }

    public void deleteWalletById(Long id) {
        walletRepository.deleteById(id);
    }

    public Double getBalanceById(Long id) {
        return walletRepository.findBalanceById(id);
    }

    public Double updateBalance(Long id, Double amount) {
        Wallet wallet = walletRepository.findWalletById(id);
        Double balance = wallet.getBalance();
        if ( balance + amount < 0.0) {
            throw new InsufficientFundsException();
        }
        // TODO: execute the transaction
        wallet.setBalance(balance + amount);
        walletRepository.save(wallet);
        return wallet.getBalance();
    }
}

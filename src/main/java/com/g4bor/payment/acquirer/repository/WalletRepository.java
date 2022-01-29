package com.g4bor.payment.acquirer.repository;

import com.g4bor.payment.acquirer.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findWalletById(Long id);
    double findBalanceById(Long id);
}

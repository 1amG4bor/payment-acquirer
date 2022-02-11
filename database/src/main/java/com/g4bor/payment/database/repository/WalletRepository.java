package com.g4bor.payment.database.repository;

import com.g4bor.payment.database.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findWalletById(Long id);
    double findBalanceById(Long id);
}

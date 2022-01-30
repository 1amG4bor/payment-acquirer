package com.g4bor.payment.acquirer.controller;

import com.g4bor.payment.acquirer.exception.InfoResponse;
import com.g4bor.payment.acquirer.model.Wallet;
import com.g4bor.payment.acquirer.service.WalletService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/wallets",
        consumes = "application/json",
        produces = "application/json")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{id}")
    public Wallet getWalletById(@PathVariable Long id) {
        return walletService.getWalletById(id);
    }

    @GetMapping()
    public List<Wallet> getWallets(@RequestBody List<Long> ids) {
        return walletService.getWallets(ids);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWallet(@PathVariable Long id) {
        walletService.deleteWalletById(id);

        InfoResponse response = new InfoResponse(
                "Wallet is successfully deleted.",
                List.of("Deleted record id: " + id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/balance")
    public Double getBalance(@PathVariable Long id) {
        return walletService.getBalanceById(id);
    }

    @PostMapping("/{id}/balance")
    public Double updateBalanceByAmount(@PathVariable Long id, @RequestBody Double amount) {
        return walletService.updateBalance(id, amount);
    }
}

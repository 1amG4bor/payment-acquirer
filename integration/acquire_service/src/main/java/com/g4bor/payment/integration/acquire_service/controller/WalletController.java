package com.g4bor.payment.integration.acquire_service.controller;

import com.g4bor.payment.entity.model.WalletDTO;
import com.g4bor.payment.integration.acquire_service.model.MessageResponse;
import com.g4bor.payment.integration.acquire_service.service.WalletService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Wallet", description = "API to manage changes in Wallet resources")
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
    public WalletDTO getWalletById(@PathVariable Long id) {
        return walletService.getWalletById(id);
    }

    @GetMapping()
    public List<WalletDTO> getWallets(@RequestBody List<Long> ids) {
        return walletService.getWallets(ids);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWallet(@PathVariable Long id) {
        walletService.deleteWalletById(id);

        MessageResponse response = new MessageResponse(
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

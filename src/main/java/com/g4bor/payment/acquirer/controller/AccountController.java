package com.g4bor.payment.acquirer.controller;

import com.g4bor.payment.acquirer.model.Account;
import com.g4bor.payment.acquirer.model.DTO.AccountCreationDTO;
import com.g4bor.payment.acquirer.model.DTO.WalletDTO;
import com.g4bor.payment.acquirer.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/new")
    public Account createNewAccount(@RequestBody AccountCreationDTO accountDto) {
        return accountService.createAccount(accountDto);
    }

    @GetMapping("/id/{id}")
    public Account getAccountById(@PathVariable String id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/name/{username}")
    public Account getAccountByUsername(@PathVariable String username) {
        return accountService.getAccountByUsername(username);
    }

    @GetMapping("/{id}/wallets")
    public Set<WalletDTO> getWallets(@PathVariable String id) {
        return accountService.getWalletsForAccount(id);
    }

    @PostMapping("/{id}/wallets/new/{currency}")
    public Account addWallet(@PathVariable String id, @PathVariable(name = "currency") String currencyISOCode) {
        return accountService.addWallet(id, currencyISOCode);
    }

    @GetMapping("/{id}/balance/{currency}")
    public Double getBalance(@PathVariable String id, @PathVariable(name = "currency") String currencyISOCode) {
        return accountService.getBalanceByAccountIdAndCurrency(id, currencyISOCode);
    }
}

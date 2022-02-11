package com.g4bor.payment.integration.acquire_service.controller;

import com.g4bor.payment.entity.model.AccountDTO;
import com.g4bor.payment.entity.model.AccountRegistrationDTO;
import com.g4bor.payment.entity.model.WalletDTO;
import com.g4bor.payment.integration.acquire_service.model.MessageResponse;
import com.g4bor.payment.integration.acquire_service.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "Account", description = "API to manage Account resources and the corresponding Wallets")
@RestController
@RequestMapping(value = "/api/users",
        consumes = "application/json",
        produces = "application/json")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Operation(summary = "Create new Account", description = "Create an account with a wallet in the chosen currency.")
    @PostMapping()
    public AccountDTO createNewAccount(@RequestBody AccountRegistrationDTO registrationDTO) {
        return accountService.createAccount(registrationDTO);
    }

    @Operation(summary = "Get all Accounts", description = "Get all Accounts")
    @GetMapping("/")
    public List<AccountDTO> getAllAccount() {
        return accountService.getAccounts();
    }

    @Operation(summary = "Get Account by Id", description = "Retrieve an account by the given UUID.")
    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable String id) {
        return accountService.getAccountById(id);
    }

    @Operation(summary = "Update Account", description = "Retrieve the account by the id of the provided account, " +
            "and update with the provided details.")
    @PutMapping("/{id}")
    public AccountDTO updateAccount(@RequestBody AccountDTO account) {
        return accountService.updateAccount(account);
    }

    @PatchMapping("/{id}")
    public AccountDTO updateAccount(@PathVariable String id, @RequestBody String username) {
        return accountService.changeUsername(id, username);
    }

    @Operation(summary = "Delete an Account", description = "Delete an account by the given UUID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable String id) {
        accountService.deleteAccountById(id);

        MessageResponse response = new MessageResponse(
                "Account is successfully deleted.",
                List.of("Deleted record id: " + id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get all wallets for an Account", description = "Return all the registered wallet " +
            "for an account identified by the given UUID.")
    @GetMapping("/{id}/wallets")
    public Set<WalletDTO> getWallets(@PathVariable String id) {
        return accountService.getWalletsForAccount(id);
    }

    @Operation(summary = "Add new wallet", description = "Add a new wallet for an account in the chosen currency.")
    @PostMapping("/{id}/wallets/new/{currency}")
    public AccountDTO addWallet(@PathVariable String id, @PathVariable(name = "currency") String currencyISOCode) {
        return accountService.addWallet(id, currencyISOCode);
    }

    @Operation(summary = "Get balance", description = "Return the balance of an account by the given " +
            "account id (UUID) and currency (ISO code).")
    @GetMapping("/{id}/balance/{currency}")
    public Double getBalance(@PathVariable String id, @PathVariable(name = "currency") String currencyISOCode) {
        return accountService.getBalanceByIdAndCurrency(id, currencyISOCode);
    }
}

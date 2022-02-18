package com.g4bor.payment.integration.acquire_service.controller;

import com.g4bor.payment.entity.model.AccountDTO;
import com.g4bor.payment.entity.model.AccountRegistrationDTO;
import com.g4bor.payment.entity.model.AddressDTO;
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

    // region CREATE
    @Operation(summary = "Create new Account", description = "Create an account with a default wallet in the chosen currency.")
    @PostMapping()
    public AccountDTO createNewAccount(@RequestBody AccountRegistrationDTO registrationDTO) {
        return accountService.createAccount(registrationDTO);
    }

    @Operation(summary = "Add new wallet", description = "Add a new wallet for an account in the chosen currency.")
    @PostMapping("/{id}/wallets/{currency}")
    public AccountDTO addWallet(@PathVariable String id, @PathVariable(name = "currency") String currencyISOCode) {
        return accountService.addWallet(id, currencyISOCode);
    }
    // endregion

    // region READ
    @Operation(summary = "Get all Accounts", description = "Fetch all the accounts")
    @GetMapping("/")
    public List<AccountDTO> getAllAccount() {
        return accountService.getAccounts();
    }

    @Operation(summary = "Get Account by Id", description = "Retrieve an account by accountId.")
    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable String id) {
        return accountService.getAccountById(id);
    }

    @Operation(summary = "Get all wallets for an Account", description = "Return all the registered wallet " +
            "for an account identified by the accountId.")
    @GetMapping("/{id}/wallets")
    public Set<WalletDTO> getWallets(@PathVariable String id) {
        return accountService.getWalletsForAccount(id);
    }

    @Operation(summary = "Get balance for an Account", description = "Return the balance of an account by the given " +
            "account id (UUID) and currency (ISO code).")
    @GetMapping("/{id}/balance/{currency}")
    public Double getBalance(@PathVariable String id, @PathVariable(name = "currency") String currencyISOCode) {
        return accountService.getBalanceByIdAndCurrency(id, currencyISOCode);
    }
    // endregion

    // region UPDATE
    @Operation(summary = "Update Account", description = "Update all the fields of an account identified by the accountId")
    @PutMapping("/{id}")
    public AccountDTO updateAccount(@RequestBody AccountDTO account) {
        return accountService.updateAccount(account);
    }

    @Operation(summary = "Change the username", description = "Change the username of an account identified by the accountId")
    @PatchMapping("/{id}/username")
    public AccountDTO updateUsername(@PathVariable String id, @RequestBody String username) {
        return accountService.changeUsername(id, username);
    }

    @Operation(summary = "Change the address", description = "Change the username of an account identified by the accountId")
    @PatchMapping("/{id}/address")
    public AccountDTO updateAddress(@PathVariable String id, @RequestBody AddressDTO address) {
        return accountService.changeAddress(id, address);
    }

    @Operation(summary = "Change the idNumber", description = "Change the idNumber of an account identified by the accountId")
    @PatchMapping("/{id}/id-number")
    public AccountDTO updateIdNumber(@PathVariable String id, @RequestBody String idNumber) {
        return accountService.changeIdNumber(id, idNumber);
    }
    // endregion

    // region DELETE
    @Operation(summary = "Delete an Account", description = "Delete an account by the accountId.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable String id) {
        accountService.deleteAccountById(id);

        MessageResponse response = new MessageResponse(
                "Account is successfully deleted.",
                List.of("Deleted record id: " + id));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Delete a Wallet", description = "Delete an existing Wallet for a given account identified by the accountId.")
    @DeleteMapping("/{accountId}/wallets/{walletId}")
    public ResponseEntity<Object> deleteWallet(@PathVariable String accountId, @PathVariable Long walletId) {
        accountService.deleteWallet(accountId, walletId);

        MessageResponse response = new MessageResponse(
                "Wallet is successfully deleted.", List.of(
                        "Identifier of the corresponding account: " + accountId,
                        "Identifier of the deleted wallet : " + walletId
        ));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    // endregion
}

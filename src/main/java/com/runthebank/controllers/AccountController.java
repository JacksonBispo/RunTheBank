package com.runthebank.controllers;

import com.runthebank.domain.Account;
import com.runthebank.domain.Customer;
import com.runthebank.domain.Enums.Status;
import com.runthebank.dtos.AccountDTO;
import com.runthebank.usecases.SaveAccount;
import com.runthebank.usecases.SaveCustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {


    private final SaveAccount saveAccount;
    @PostMapping("/accounts")
    public ResponseEntity<Account> save(@RequestBody AccountDTO accountDTO, UriComponentsBuilder uriBuilder) {

        var customerSaved = saveAccount.execute(accountDTO);
        var uri = uriBuilder.path("/customer/save/{id}").buildAndExpand(customerSaved.getId()).toUri();
        return ResponseEntity.created(uri).body(customerSaved);
    }
}

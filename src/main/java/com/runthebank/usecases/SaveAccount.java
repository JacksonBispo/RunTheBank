package com.runthebank.usecases;

import com.runthebank.domain.Account;
import com.runthebank.domain.Customer;
import com.runthebank.domain.Enums.Status;
import com.runthebank.dtos.AccountDTO;
import com.runthebank.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveAccount {

    private final AccountRepository accountRepository;

    public Account execute(AccountDTO accountDTO){
        Customer customer = new Customer();
        customer.setCustomerId(accountDTO.customerID());

        Account account = new Account();

        account.setAgency(accountDTO.agency());
        account.setStatus(Status.ACTIVE);
        account.setCustomer(customer);
        account.setBalance(accountDTO.balance());

       return accountRepository.save(account);
    }
}

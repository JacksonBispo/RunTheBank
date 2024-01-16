package com.runthebank.usecases;

import com.runthebank.domain.Account;
import com.runthebank.domain.Customer;
import com.runthebank.domain.Enums.Status;
import com.runthebank.dtos.AccountDTO;
import com.runthebank.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class SaveAccountTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private SaveAccount saveAccoount;

    @Test
    void shouldSaveACustomerWithSuccess(){
        var customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());

        var accountDTO = new AccountDTO(
                "1234",
                customer.getCustomerId(),
                new BigDecimal("20.0000")
        );

        var account = new Account();
        account.setAgency(accountDTO.agency());
        account.setCustomer(customer);
        account.setBalance(accountDTO.balance());
        account.setStatus(Status.ACTIVE);
        Mockito.when(accountRepository.save(any(Account.class))).thenReturn(account);

        var accountSaved = saveAccoount.execute(accountDTO);

        verify(accountRepository, times(1)).save(any());

        assertNotNull(accountSaved);
        assertEquals(account, accountSaved);

    }

    @Test
    void shouldNotSaveCustomer(){

         var customer  = new Customer();

         customer.setCustomerId(UUID.randomUUID());
        var accountDTO = new AccountDTO(
                "1234",
                customer.getCustomerId(),
                new BigDecimal("20.0000")
        );

        var account = new Account();

        account.setAgency(accountDTO.agency());
        account.setCustomer(customer);
        account.setBalance(accountDTO.balance());
        account.setStatus(Status.ACTIVE);


        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(null);

        var accountSaved = saveAccoount.execute(accountDTO);

        assertNull(accountSaved);
    }
}

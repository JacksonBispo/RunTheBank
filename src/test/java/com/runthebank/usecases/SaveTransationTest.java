package com.runthebank.usecases;

import com.runthebank.domain.Account;
import com.runthebank.domain.Customer;
import com.runthebank.domain.Enums.Status;
import com.runthebank.domain.Transaction;
import com.runthebank.dtos.TransactionDTO;
import com.runthebank.exceptions.AccountNotFoundException;
import com.runthebank.exceptions.InsufficientBalanceException;
import com.runthebank.repositories.AccountRepository;
import com.runthebank.repositories.CustomerRepository;
import com.runthebank.repositories.TransactionRepository;
import com.runthebank.usecases.SaveTransaction;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SaveTransationTest {

  @InjectMocks
  private SaveTransaction saveTransaction;

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private TransactionRepository transactionRepository;

  @Mock
  private ValidationTransaction validationTransaction;

  @Mock
  private Notification notification;

  @Test
  void shouldSaveTransactionWithSuccess(){
    TransactionDTO transactionDTO = new TransactionDTO(
            UUID.randomUUID(),
            new BigDecimal(10),
            "1232223423",
            "1234",
            "1234"
    );



    Customer senderCustomer = new Customer();
    senderCustomer.setName("Jackson");
    senderCustomer.setDocument(transactionDTO.senderDocument());
    senderCustomer.setPassword("password");


    Customer receiverCustomer = new Customer();
    receiverCustomer.setName("Vanessa");
    receiverCustomer.setDocument(transactionDTO.receiverDocument());
    receiverCustomer.setPassword("password");


    var senderAccount = new Account();
    senderAccount.setAgency(transactionDTO.agency());
    senderAccount.setStatus(Status.ACTIVE);
    senderAccount.setCustomer(senderCustomer);
    senderAccount.setBalance(new BigDecimal(100));
    senderAccount.setCustomer(senderCustomer);
    senderCustomer.setAccounts(List.of(senderAccount));

    Account receiverAccount = new Account();
    receiverAccount.setAgency(transactionDTO.receiverDocument());
    receiverAccount.setStatus(Status.ACTIVE);
    receiverAccount.setCustomer(senderCustomer);
    receiverAccount.setBalance(new BigDecimal(100));
    receiverCustomer.setAccounts(List.of(receiverAccount));

    when(accountRepository.findByCustomerDocument(senderCustomer.getDocument())).thenReturn(Optional.of(senderAccount));
    when(accountRepository.findByCustomerDocument(receiverCustomer.getDocument())).thenReturn(Optional.of(receiverAccount));

    Transaction transaction = new Transaction();
    transaction.setAmount(transactionDTO.value());
    transaction.setReceiver(receiverCustomer);
    transaction.setSender(senderCustomer);


    when(validationTransaction.execute(any(), any())).thenReturn(true);

    saveTransaction.execute(transactionDTO);

    verify(accountRepository, times(2)).findByCustomerDocument(any());
    verify(validationTransaction).execute(any(), any());
    verify(transactionRepository).save(any());
    verify(accountRepository, times(1)).saveAll(any());
    verify(notification, times(2)).sendNotification(any(), any());

  }

  @Test
  void shouldAccountNotFoundExceptionTransaction(){
    TransactionDTO transactionDTO = new TransactionDTO(

            UUID.randomUUID(),
            new BigDecimal(10),
            "1234",
            "123432242323",
            "1234"
    );


      when(accountRepository.findByCustomerDocument(eq("1232223423"))).thenReturn(Optional.empty());
      when(accountRepository.findByCustomerDocument(eq("123432242323"))).thenReturn(Optional.empty());
    assertThrows(AccountNotFoundException.class, ()-> saveTransaction.execute(transactionDTO));

  }


  @Test
  void shouldShowInsuficientBalanceExceptionTransaction(){
    TransactionDTO transactionDTO = new TransactionDTO(
            UUID.randomUUID(),
            new BigDecimal(10),
            "1232223423",
            "123432242323",
            "1234"
    );

    Customer senderCustomer = new Customer();
    senderCustomer.setName("Jackson");
    senderCustomer.setDocument("1232223423");
    senderCustomer.setPassword("password");


    Customer receiverCustomer = new Customer();
    receiverCustomer.setName("Vanessa");
    receiverCustomer.setDocument("123432242323");
    receiverCustomer.setPassword("password");


    var senderAccount = new Account();
    senderAccount.setAgency("1234");
    senderAccount.setStatus(Status.ACTIVE);
    senderAccount.setCustomer(senderCustomer);
    senderAccount.setBalance(new BigDecimal(100));
    senderCustomer.setAccounts(List.of(senderAccount));
    senderAccount.setCustomer(senderCustomer);

    Account receiverAccount = new Account();
    receiverAccount.setAgency("1234");
    receiverAccount.setStatus(Status.ACTIVE);
    receiverAccount.setCustomer(senderCustomer);
    receiverAccount.setBalance(new BigDecimal(100));
    receiverCustomer.setAccounts(List.of(receiverAccount));

    when(accountRepository.findByCustomerDocument(eq("1232223423"))).thenReturn(Optional.of(senderAccount));
    when(accountRepository.findByCustomerDocument(eq("123432242323"))).thenReturn(Optional.of(receiverAccount));
    assertThrows(InsufficientBalanceException.class, ()-> saveTransaction.execute(transactionDTO));

  }
}

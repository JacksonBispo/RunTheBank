package com.runthebank.usecases;

import com.runthebank.domain.Customer;
import com.runthebank.domain.Transaction;
import com.runthebank.dtos.TransactionDTO;
import com.runthebank.exceptions.AccountNotFoundException;
import com.runthebank.exceptions.InsufficientBalanceException;
import com.runthebank.repositories.AccountRepository;
import com.runthebank.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CancelTransaction {

    private final AccountRepository accountRepository;

    private final Notification notificationService;

    private final TransactionRepository transactionRepository;
    private final ValidationTransaction validationTransaction;

    public Transaction execute(TransactionDTO transactionDTO){

        Optional<Transaction> transactionfind = transactionRepository.findById(transactionDTO.id());
        var sender = accountRepository.
                findByCustomerDocument(transactionDTO.senderDocument()
                );
        var receiver = accountRepository.
                findByCustomerDocument(transactionDTO.receiverDocument()
                );

        if (receiver.isPresent() && sender.isPresent()){


            sender.get().setBalance(sender.get().getBalance().add(transactionfind.get().getAmount()));
            receiver.get().setBalance(receiver.get().getBalance().subtract(transactionfind.get().getAmount()));
                transactionRepository.save(transactionfind.get());
                accountRepository.saveAll(Arrays.asList(sender.get(), receiver.get()));
                notificationService.sendNotification(sender.get().getCustomer().getEmail(), "Transferencia canelada");
                notificationService.sendNotification(receiver.get().getCustomer().getEmail(), "Transferencia revertida");
        }else{
            throw new AccountNotFoundException("Dados invalidos");
        }
        return transactionfind.get();
    }

    private Transaction createTransaction(
            Customer sender,
            Customer receiver,
            BigDecimal value){
        var transaction = new Transaction();
        transaction.setAmount(value);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
    return transaction;
    }
}

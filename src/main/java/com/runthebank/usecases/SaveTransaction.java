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

@Service
@RequiredArgsConstructor
public class SaveTransaction {

    private final AccountRepository accountRepository;

    private final Notification notificationService;

    private final TransactionRepository transactionRepository;
    private final ValidationTransaction validationTransaction;

    public Transaction execute(TransactionDTO transactionDTO){

        Transaction transactionSave = new Transaction();
        var sender = accountRepository.
                findByCustomerDocument(transactionDTO.senderDocument()
                );
        var receiver = accountRepository.
                findByCustomerDocument(transactionDTO.receiverDocument()
                );

        if (receiver.isPresent() && sender.isPresent()){
            Transaction transaction =
                    createTransaction(sender.get().getCustomer(),
                            receiver.get().getCustomer(),
                            transactionDTO.value());
            if (validationTransaction.execute(sender.get(), transaction.getAmount())) {
                sender.get().setBalance(sender.get().getBalance().subtract(transaction.getAmount()));
                receiver.get().setBalance(receiver.get().getBalance().add(transaction.getAmount()));
                transactionSave = transactionRepository.save(transaction);
                accountRepository.saveAll(Arrays.asList(sender.get(), receiver.get()));
                notificationService.sendNotification(sender.get().getCustomer().getEmail(), "Transferencia enviada");
                notificationService.sendNotification(receiver.get().getCustomer().getEmail(), "Transferencia recebida");
            }else {
                throw new InsufficientBalanceException("saldo insuficiente!");
            }
        }else{
            throw new AccountNotFoundException("Dados invalidos");
        }
        return transactionSave;
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

package com.runthebank.controllers;

import com.runthebank.dtos.TransactionDTO;
import com.runthebank.usecases.CancelTransaction;
import com.runthebank.usecases.SaveTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {


    private final SaveTransaction saveTransaction;

    private final CancelTransaction cancelTransaction;

    @PostMapping
    public ResponseEntity<TransactionDTO> saveTransaction(@RequestBody TransactionDTO transactionDTO,
                                                          UriComponentsBuilder uriBuilder){
        var transaction = saveTransaction.execute(transactionDTO);
        var uri = uriBuilder.path("/customer/save/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(uri).body(new TransactionDTO(
                transaction.getId(),
                transaction.getAmount(),
                transactionDTO.senderDocument(),
                transactionDTO.receiverDocument(),
                transactionDTO.agency()));

    }

    @DeleteMapping
    public void cancelTransaction(@RequestBody TransactionDTO transactionDTO){
        cancelTransaction.execute(transactionDTO);
    }
}

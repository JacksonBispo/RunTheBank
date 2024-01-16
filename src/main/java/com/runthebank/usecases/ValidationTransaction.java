package com.runthebank.usecases;

import com.runthebank.domain.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ValidationTransaction {

    public Boolean execute(Account sender, BigDecimal transactionValue){

        return  sender.getBalance().compareTo(transactionValue) > 0;
    }


}

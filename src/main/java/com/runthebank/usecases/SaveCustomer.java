package com.runthebank.usecases;

import com.runthebank.domain.Account;
import com.runthebank.domain.Customer;
import com.runthebank.repositories.AccountRepository;
import com.runthebank.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveCustomer {

    private final CustomerRepository customerRepository;

    public Customer execute(Customer customer){
       return customerRepository.save(customer);
    }
}

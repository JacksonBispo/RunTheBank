package com.runthebank.controllers;

import com.runthebank.domain.Customer;
import com.runthebank.dtos.CustomerDTO;
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
@RequestMapping("/customer")
public class CustomerController {


    private final SaveCustomer saveCustomer;
    @PostMapping
    public ResponseEntity<Customer> saveCustomer(@RequestBody CustomerDTO customerDTO,
                                                    UriComponentsBuilder uriBuilder) {
            Customer customer = new Customer();

            customer.setName(customerDTO.name());
            customer.setPassword(customerDTO.password());
            customer.setDocument(customerDTO.document());
            customer.setAddress(customerDTO.address());
            customer.setEmail(customerDTO.email());

            var customerSaved = saveCustomer.execute(customer);
            var uri = uriBuilder.path("/customer/save/{id}").buildAndExpand(customerSaved.getCustomerId()).toUri();
            return ResponseEntity.created(uri).body(customerSaved);
    }
}

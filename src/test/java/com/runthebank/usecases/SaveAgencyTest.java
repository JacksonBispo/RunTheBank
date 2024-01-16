package com.runthebank.usecases;

import com.runthebank.domain.Customer;
import com.runthebank.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
public class SaveAgencyTest {

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void shouldSaveACustomerWithSuccess(){
        var customer = new Customer();

        customer.setCustomerId(null);
        customer.setName("Jack Daniels");
        customer.setAddress("Rua dos pororos");
        customer.setPassword("StrongPassword");
        customer.setAccounts(new ArrayList<>());

        when(customerRepository.save(customer)).thenReturn(customer);
    }
}

package com.hexaware.hotpot.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.security.repository.CustomerSecurityRepository;
@Service
public class CustomerService {
	@Autowired
    private CustomerSecurityRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String saveCustomer(Customer customer) {
        // Encode the password before saving
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        repository.save(customer);
        return "Customer registered successfully";
    }
}



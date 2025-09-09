package com.hexaware.hotpot.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.hotpot.entities.Customer;

public interface CustomerSecurityRepository extends JpaRepository<Customer,Integer> {
	Optional<Customer> findByEmail(String email);

}

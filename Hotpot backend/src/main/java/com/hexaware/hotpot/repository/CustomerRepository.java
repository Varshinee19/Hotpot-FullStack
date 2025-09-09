package com.hexaware.hotpot.repository;
/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.hexaware.hotpot.entities.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer>{
	
	public Customer findByEmail(String email);

}

package com.hexaware.hotpot.repository;
/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.hotpot.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Integer>{
	
	public Payment findByOrderOrderId(int orderId);
	

}

package com.hexaware.hotpot.repository;
import java.util.Optional;

/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hotpot.entities.Cart;
@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
	
	Optional<Cart> findByCustomerCustomerId(int customerId);

}

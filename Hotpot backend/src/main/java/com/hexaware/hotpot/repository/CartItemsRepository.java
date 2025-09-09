package com.hexaware.hotpot.repository;
/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.hotpot.entities.CartItems;


@Repository
public interface CartItemsRepository extends JpaRepository<CartItems,Integer> {
	
	public List<CartItems> findByCartCartId(int cartid);
	@Modifying
	@Transactional
	@Query("Update CartItems c set c.quantity=?1 where c.cartItemId=?2")
	public int updateQuantity(int quantity,int cartItemId);

}

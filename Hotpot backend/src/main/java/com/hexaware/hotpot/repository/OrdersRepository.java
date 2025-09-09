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

import com.hexaware.hotpot.entities.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer>{
	
	public List<Orders> findByCustomerCustomerId(int custid);
	public List<Orders> findByRestaurantRestaurantId(int resid);
	@Modifying
	@Transactional
	@Query("UPDATE Orders o SET o.status = ?1 WHERE o.orderId = ?2")
	public int updateOrderStatus(String status,int orderId);
	
	


}

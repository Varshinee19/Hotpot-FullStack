package com.hexaware.hotpot.repository;
/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.hexaware.hotpot.entities.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems,Integer>{
	public List<OrderItems> findByOrderOrderId(int orderId);
	public List<OrderItems> findByOrderRestaurantRestaurantId(int restaurantId);
	public List<OrderItems> findByOrderCustomerCustomerId(int customerId);
	

}

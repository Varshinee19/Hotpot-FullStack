package com.hexaware.hotpot.repository;
import java.util.List;

/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hotpot.entities.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Integer>{
	
	public List<Restaurant> findByRestaurantNameContainingIgnoreCase(String restaurantName);
}



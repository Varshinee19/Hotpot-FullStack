package com.hexaware.hotpot.repository;
/*
 * 
 * auth: Varshinee
 * Repository Class.
 */
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hotpot.entities.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Integer>{
	public List<Menu> findByRestaurantRestaurantId(int restaurantId);
	public List<Menu> findByMenuCategoryCategoryId(int categoryId);
	public List<Menu> findByItemName(String itemName);


}

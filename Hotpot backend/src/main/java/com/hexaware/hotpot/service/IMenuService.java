package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import java.util.List;

import com.hexaware.hotpot.dto.MenuDto;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.exception.CategoryDoesNotExistException;
import com.hexaware.hotpot.exception.MenuNotFoundException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;

public interface IMenuService {
	
	public Menu addMenuItem(MenuDto menu) throws RestaurantNotFoundException, CategoryDoesNotExistException;
	public Menu updateMenu(Integer menuId,MenuDto menu) throws MenuNotFoundException;
	public List<Menu> getMenuByRestaurant(int restaurantId) throws RestaurantNotFoundException;
	public List<Menu> getMenuByCategory(int categoryId) throws CategoryDoesNotExistException;
	public List<Menu> getAllMenu();
	public Menu getById(int menuId) throws MenuNotFoundException;
	public String deleteMenuItem(int menuId) throws MenuNotFoundException;
	public List<Menu> getByItemName(String itemName);

	

}

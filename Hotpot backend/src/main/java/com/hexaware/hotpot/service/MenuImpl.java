package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * modified:13-08-25
 * Service Implementation Class.
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.dto.MenuDto;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.entities.MenuCategories;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.exception.CategoryDoesNotExistException;
import com.hexaware.hotpot.exception.MenuNotFoundException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
import com.hexaware.hotpot.repository.MenuCategoriesRepository;
import com.hexaware.hotpot.repository.MenuRepository;
import com.hexaware.hotpot.repository.RestaurantRepository;

@Service
public class MenuImpl implements IMenuService {

	@Autowired
	MenuRepository repo;

	@Autowired
	RestaurantRepository resRepo;

	@Autowired
	MenuCategoriesRepository catRepo;

	@Override
	public Menu addMenuItem(MenuDto menu) throws RestaurantNotFoundException, CategoryDoesNotExistException {
		Menu menus = new Menu();
		int restaurantId = menu.getRestaurantId();
		Restaurant restaurant = resRepo.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
		int categoryId=menu.getCategoryId();
		MenuCategories category = catRepo.findById(menu.getCategoryId()).orElseThrow(() -> new CategoryDoesNotExistException());
		menus.setItemName(menu.getItemName());
		menus.setType(menu.getType());
		menus.setDescription(menu.getDescription());
		menus.setInfo(menu.getInfo());
		menus.setImage(menu.getImage());
		menus.setPrice(menu.getPrice());
	    menus.setRestaurant(restaurant);
	    menus.setMenuCategory(category);
		return repo.save(menus);
		
	}

	@Override
	public Menu updateMenu(Integer menuId, MenuDto menu) throws MenuNotFoundException {
		Menu menus = repo.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
		menus.setItemName(menu.getItemName());
		menus.setType(menu.getType());
		menus.setDescription(menu.getDescription());
		menus.setInfo(menu.getInfo());
		menus.setImage(menu.getImage());
		menus.setPrice(menu.getPrice());
	    int restaurantId = menu.getRestaurantId();
	    Restaurant restaurant = resRepo.findById(restaurantId).orElseThrow(() -> new RuntimeException("Restaurant not found"));
	    menus.setRestaurant(restaurant);
	    int categoryId = menu.getCategoryId();
	    MenuCategories category = catRepo.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
	    menus.setMenuCategory(category);
		return repo.save(menus);

	}

	@Override
	public List<Menu> getMenuByRestaurant(int restaurantId) throws RestaurantNotFoundException {
		resRepo.findById(restaurantId).orElseThrow(() -> new RestaurantNotFoundException("Restaurant Not Found"));
		return repo.findByRestaurantRestaurantId(restaurantId);
	}

	@Override
	public List<Menu> getMenuByCategory(int categoryId) throws CategoryDoesNotExistException {
		catRepo.findById(categoryId).orElseThrow(() -> new CategoryDoesNotExistException());
		return repo.findByMenuCategoryCategoryId(categoryId);
	}

	@Override
	public List<Menu> getAllMenu() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public String deleteMenuItem(int menuId) throws MenuNotFoundException {
		// TODO Auto-generated method stub
		repo.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
		repo.deleteById(menuId);
		return "Menu item deleted";
	}

	@Override
	public Menu getById(int menuId) throws MenuNotFoundException {
		// TODO Auto-generated method stub
		return repo.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
	}

	@Override
	public List<Menu> getByItemName(String itemName) {
		// TODO Auto-generated method stub
		return repo.findByItemName(itemName);
	}


	

}

package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import java.util.List;

import com.hexaware.hotpot.dto.RestaurantDto;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.exception.RestaurantAlreadyExistsException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;

public interface IRestaurantService {
	
	public Restaurant addRestaurant(RestaurantDto dto)throws RestaurantAlreadyExistsException;
	public Restaurant updateRestaurant(Integer restaurantId,RestaurantDto dto)throws RestaurantNotFoundException;
	public List<Restaurant> getRestaurantByName(String restaurantName)throws RestaurantNotFoundException;
	public Restaurant getById(int restaurantId)throws RestaurantNotFoundException;
	public String deleteRestaurant(int resaurantId)throws RestaurantNotFoundException;
	List<Restaurant> getAllRestaurant();
	

}

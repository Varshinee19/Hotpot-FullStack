package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * modified:13-08-25
 * Service Implementation Class.
 */
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.dto.RestaurantDto;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.exception.RestaurantAlreadyExistsException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
import com.hexaware.hotpot.repository.RestaurantRepository;
@Service
public class RestaurantImpl implements IRestaurantService {
	@Autowired
	RestaurantRepository repo;

	@Override
	public Restaurant addRestaurant(RestaurantDto dto) throws RestaurantAlreadyExistsException{
		Restaurant restaurant=readData(dto);
		if (repo.findById(restaurant.getRestaurantId()).isPresent()) {
			throw new RestaurantAlreadyExistsException();
		}
		return repo.save(restaurant);
		
	}

	@Override
	public Restaurant updateRestaurant(Integer restaurantId,RestaurantDto dto) throws RestaurantNotFoundException{
		Optional<Restaurant>restaurant=repo.findById(restaurantId);
		if(!(restaurant.isPresent())){
			throw new RestaurantNotFoundException("Restaurant with ID not found"+restaurantId);
		}
		Restaurant res=restaurant.get();
		res.setRestaurantName(dto.getRestaurantName());
		res.setRestaurantAddress(dto.getRestaurantAddress());
		res.setPhoneNo(dto.getPhoneNo());
		return repo.save(res);
		
	}

	@Override
	public List<Restaurant> getRestaurantByName(String restaurantName) throws RestaurantNotFoundException {
		
		List<Restaurant> restaurant= repo.findByRestaurantNameContainingIgnoreCase(restaurantName);
		if(restaurant.isEmpty()) {
			throw new  RestaurantNotFoundException("Restaurant Not Foundd with name"+restaurantName);
		}
		return restaurant;
	}

	@Override
	public String deleteRestaurant(int restaurantId) throws RestaurantNotFoundException{
		repo.findById(restaurantId) .orElseThrow(() -> new RestaurantNotFoundException("Restaurant could not be found with"+restaurantId));
		repo.deleteById(restaurantId);
		return "restaurant deleted successfully";
		
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}
	public Restaurant readData(RestaurantDto dto) {
		Restaurant restaurant=new Restaurant();
		restaurant.setRestaurantName(dto.getRestaurantName());
		restaurant.setRestaurantAddress(dto.getRestaurantAddress());
	    restaurant.setPhoneNo(dto.getPhoneNo());
		return restaurant;
		
	}

	@Override
	public Restaurant getById(int restaurantId) throws RestaurantNotFoundException {
		// TODO Auto-generated method stub
		return repo.findById(restaurantId).orElseThrow(()-> new RestaurantNotFoundException("Resstaurant not found with ID: "+restaurantId ));
	}



}

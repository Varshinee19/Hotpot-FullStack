package com.hexaware.hotpot.service;


import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.RestaurantDto;
import com.hexaware.hotpot.entities.Restaurant;
import com.hexaware.hotpot.repository.RestaurantRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class RestaurantServiceImplTest {
    @Autowired
    private IRestaurantService restaurantService;

    @Autowired
    private RestaurantRepository repo;

    @Test
    void testAddRestaurant() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Resto");
        restaurantDto.setRestaurantAddress("123 Main St");
        restaurantDto.setPhoneNo("1234567890");

        Restaurant restaurant = restaurantService.addRestaurant(restaurantDto);

        Assertions.assertNotNull(restaurant.getRestaurantId());
        Assertions.assertEquals("Test Resto", restaurant.getRestaurantName());
    }

    @Test
    void testUpdateRestaurant() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Resto");
        restaurantDto.setRestaurantAddress("123 Main St");
        restaurantDto.setPhoneNo("1234567890");

        Restaurant restaurant = restaurantService.addRestaurant(restaurantDto);

        RestaurantDto updateDto = new RestaurantDto();
        updateDto.setRestaurantName("Updated Resto");
        updateDto.setRestaurantAddress("456 New St");
        updateDto.setPhoneNo("9876543210");

        Restaurant updated = restaurantService.updateRestaurant(restaurant.getRestaurantId(), updateDto);

        Assertions.assertEquals("Updated Resto", updated.getRestaurantName());
        Assertions.assertEquals("456 New St", updated.getRestaurantAddress());
        Assertions.assertEquals("9876543210", updated.getPhoneNo());
    }

    @Test
    void testGetById() throws Exception {
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Resto");
        restaurantDto.setRestaurantAddress("123 Main St");
        restaurantDto.setPhoneNo("1234567890");

        Restaurant restaurant = restaurantService.addRestaurant(restaurantDto);

        Restaurant found = restaurantService.getById(restaurant.getRestaurantId());

        Assertions.assertEquals(restaurant.getRestaurantId(), found.getRestaurantId());
        Assertions.assertEquals("Test Resto", found.getRestaurantName());
    }

  

    @Test
    void testDeleteRestaurant() throws Exception {
    	repo.deleteAll();
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Resto");
        restaurantDto.setRestaurantAddress("123 Main St");
        restaurantDto.setPhoneNo("1234567890");

        Restaurant restaurant = restaurantService.addRestaurant(restaurantDto);

        String result = restaurantService.deleteRestaurant(restaurant.getRestaurantId());
        Assertions.assertEquals("restaurant deleted successfully", result);

        List<Restaurant> all = restaurantService.getAllRestaurant();
        Assertions.assertEquals(0, all.size());
    }

    @Test
    void testGetAllRestaurant() throws Exception {
    	repo.deleteAll();
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setRestaurantName("Test Resto");
        restaurantDto.setRestaurantAddress("123 Main St");
        restaurantDto.setPhoneNo("1234567890");

        restaurantService.addRestaurant(restaurantDto);

        List<Restaurant> all = restaurantService.getAllRestaurant();
        Assertions.assertEquals(1, all.size());
        Assertions.assertEquals("Test Resto", all.get(0).getRestaurantName());
    }
}



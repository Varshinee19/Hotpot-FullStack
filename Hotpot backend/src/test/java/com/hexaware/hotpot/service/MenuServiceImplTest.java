package com.hexaware.hotpot.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class MenuServiceImplTest {
    @Autowired
    private IMenuService service;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private RestaurantRepository resRepo;

    @Autowired
    private MenuCategoriesRepository catRepo;

    private Restaurant restaurant;
    private MenuCategories category;

    @BeforeEach
    void setUp() {
        menuRepo.deleteAll();
        resRepo.deleteAll();
        catRepo.deleteAll();

        restaurant = new Restaurant();
        restaurant.setRestaurantName("Test Restaurant");
        resRepo.save(restaurant);

        category = new MenuCategories();
        category.setCategoryName("Lunch");
        catRepo.save(category);
    }

    @Test
    void testAddMenuItem() throws RestaurantNotFoundException, CategoryDoesNotExistException {
        MenuDto dto = new MenuDto();
        dto.setItemName("Burger");
        dto.setType("Veg");
        dto.setDescription("Tasty burger");
        dto.setInfo("No onion");
        dto.setImage("burger.png");
        dto.setPrice(150);
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setCategoryId(category.getCategoryId());

        Menu saved = service.addMenuItem(dto);

        Assertions.assertNotNull(saved);
        Assertions.assertEquals("Burger", saved.getItemName());
    }

    @Test
    void testUpdateMenu() throws MenuNotFoundException, RestaurantNotFoundException, CategoryDoesNotExistException {
        MenuDto dto = new MenuDto();
        dto.setItemName("Burger");
        dto.setType("Veg");
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setCategoryId(category.getCategoryId());

        Menu menu = service.addMenuItem(dto);

        MenuDto updateDto = new MenuDto();
        updateDto.setItemName("Cheese Burger");
        updateDto.setType("Veg");
        updateDto.setRestaurantId(restaurant.getRestaurantId());
        updateDto.setCategoryId(category.getCategoryId());

        Menu updated = service.updateMenu(menu.getMenuId(), updateDto);

        Assertions.assertEquals("Cheese Burger", updated.getItemName());
    }

    @Test
    void testGetMenuByRestaurant() throws RestaurantNotFoundException, CategoryDoesNotExistException {
        MenuDto dto = new MenuDto();
        dto.setItemName("Burger");
        dto.setType("Veg");
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setCategoryId(category.getCategoryId());
        service.addMenuItem(dto);

        List<Menu> menus = service.getMenuByRestaurant(restaurant.getRestaurantId());
        Assertions.assertEquals(1, menus.size());
    }

    @Test
    void testGetMenuByCategory() throws CategoryDoesNotExistException, RestaurantNotFoundException {
        MenuDto dto = new MenuDto();
        dto.setItemName("Burger");
        dto.setType("Veg");
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setCategoryId(category.getCategoryId());
        service.addMenuItem(dto);

        List<Menu> menus = service.getMenuByCategory(category.getCategoryId());
        Assertions.assertEquals(1, menus.size());
    }

    @Test
    void testDeleteMenuItem() throws MenuNotFoundException, RestaurantNotFoundException, CategoryDoesNotExistException {
        MenuDto dto = new MenuDto();
        dto.setItemName("Burger");
        dto.setType("Veg");
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setCategoryId(category.getCategoryId());

        Menu menu = service.addMenuItem(dto);

        String result = service.deleteMenuItem(menu.getMenuId());
        Assertions.assertEquals("Menu item deleted", result);
    }

    @Test
    void testGetById() throws MenuNotFoundException, RestaurantNotFoundException, CategoryDoesNotExistException {
        MenuDto dto = new MenuDto();
        dto.setItemName("Burger");
        dto.setType("Veg");
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setCategoryId(category.getCategoryId());

        Menu menu = service.addMenuItem(dto);

        Menu found = service.getById(menu.getMenuId());
        Assertions.assertEquals(menu.getMenuId(), found.getMenuId());
    }
}



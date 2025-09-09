package com.hexaware.hotpot.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotpot.dto.CartItemDto;
import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.exception.CartDoesNotExistException;
import com.hexaware.hotpot.exception.CartItemNotFoundException;
import com.hexaware.hotpot.repository.CartItemsRepository;
import com.hexaware.hotpot.repository.CartRepository;
import com.hexaware.hotpot.repository.MenuRepository;

import jakarta.transaction.Transactional;
@SpringBootTest
@Transactional
class CartItemServiceImplTest {
	
    @Autowired
    ICartItemService cartItemService;

    @Autowired
    private CartRepository cartRepo;

    @Autowired
    private MenuRepository menuRepo;

    @Autowired
    private CartItemsRepository cartItemsRepo;

    private Cart cart;
    private Menu menu;
    private CartItemDto cartItemDto;

    @BeforeEach
    void setUp() {
        
        cartItemsRepo.deleteAll();
        cartRepo.deleteAll();
        menuRepo.deleteAll();

      
        cart = new Cart();
        cartRepo.save(cart);

        
        menu = new Menu();
        menu.setItemName("Pizza");
        menu.setPrice(200.0);
        menuRepo.save(menu);

        
        cartItemDto = new CartItemDto();
        cartItemDto.setCartId(cart.getCartId());
        cartItemDto.setMenuId(menu.getMenuId());
        cartItemDto.setQuantity(2);
    }

  
    @Test
    void testAddItem() throws CartDoesNotExistException {
        CartItems saved = cartItemService.addItem(cartItemDto);
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(2, saved.getQuantity());
        Assertions.assertEquals(cart.getCartId(), saved.getCart().getCartId());
    }

   
    @Test
    void testGetItemsInCart() throws CartDoesNotExistException {
        cartItemService.addItem(cartItemDto);
        List<CartItems> items = cartItemService.getItemsInCart(cart.getCartId());
        Assertions.assertEquals(1, items.size());
    }

    
    @Test
    void testUpdateItemQuantity() throws CartDoesNotExistException {
        CartItems saved = cartItemService.addItem(cartItemDto);
        String result = cartItemService.updateItemQuantity(5, saved.getCartItemId());
        Assertions.assertEquals("Updated successfully", result);
    }

 
    @Test
    void testRemoveItem() throws CartDoesNotExistException, CartItemNotFoundException {
        CartItems saved = cartItemService.addItem(cartItemDto);
        String result = cartItemService.removeItem(saved.getCartItemId());
        Assertions.assertEquals("Cart Item removed", result);
    }

    @Test
    void testGetById() throws CartDoesNotExistException, CartItemNotFoundException {
        CartItems saved = cartItemService.addItem(cartItemDto);
        CartItems fetched = cartItemService.getById(saved.getCartItemId());
        Assertions.assertEquals(saved.getCartItemId(), fetched.getCartItemId());
    }
}
	



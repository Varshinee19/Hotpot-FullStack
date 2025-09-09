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

@Service
public class CartItemImpl implements ICartItemService {
    @Autowired
	CartItemsRepository repo;
    @Autowired
    CartRepository cartRepo;
    @Autowired
    MenuRepository menuRepo;
	@Override
	public CartItems addItem(CartItemDto cartItem) throws CartDoesNotExistException {
		Cart cart = cartRepo.findById(cartItem.getCartId()).orElseThrow(()->new  CartDoesNotExistException());
		Menu menu=menuRepo.findById(cartItem.getMenuId()).orElseThrow(()->new RuntimeException("Menu Not Found"));
		CartItems item=new CartItems();
		item.setQuantity(cartItem.getQuantity());	
		item.setCart(cart);
		item.setMenu(menu);

		return repo.save(item);
		
	}

	@Override
	public List<CartItems> getItemsInCart(int cartId) throws CartDoesNotExistException {
		// TODO Auto-generated method stub
		cartRepo.findById(cartId).orElseThrow(()->new CartDoesNotExistException());
		return repo.findByCartCartId(cartId);
	}

	@Transactional
	public String updateItemQuantity(int quantity,int cartItemId) {
		int updated=repo.updateQuantity(quantity, cartItemId);
		return updated > 0 ? "Updated successfully" : "Cart Item not found";
		
		
	}

	@Override
	public String removeItem(int cartItemId)throws CartItemNotFoundException  {
		repo.findById(cartItemId).orElseThrow(()->new CartItemNotFoundException());
		repo.deleteById(cartItemId);
		return "Cart Item removed";
		
	}

	@Override
	public CartItems getById(int cartItemId)throws CartItemNotFoundException  {
		
		return repo.findById(cartItemId).orElseThrow(()->new CartItemNotFoundException());
	}


}

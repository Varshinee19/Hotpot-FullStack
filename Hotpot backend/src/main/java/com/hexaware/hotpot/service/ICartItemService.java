package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * modified:13-08-25
 * Service Interface Class.
 */
import java.util.List;

import com.hexaware.hotpot.dto.CartItemDto;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.exception.CartDoesNotExistException;
import com.hexaware.hotpot.exception.CartItemNotFoundException;

public interface ICartItemService {
	public CartItems addItem(CartItemDto cartItem) throws CartDoesNotExistException;
	public List<CartItems> getItemsInCart(int cartId) throws CartDoesNotExistException;
	public String updateItemQuantity(int quantity,int cartItemId);
	public String removeItem(int cartItemId) throws CartItemNotFoundException;
	public CartItems getById(int cartId) throws CartItemNotFoundException;

}

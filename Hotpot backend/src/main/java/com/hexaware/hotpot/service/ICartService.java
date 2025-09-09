package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import java.util.List;
import java.util.Optional;

import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.exception.CartAlreadyExistsException;
import com.hexaware.hotpot.exception.CartDoesNotExistException;
import com.hexaware.hotpot.exception.CustomerNotFoundException;

public interface ICartService {
	public Cart createCart(int customerId) throws CartAlreadyExistsException,CustomerNotFoundException;
	public Cart getCartById(int cartId) throws CartDoesNotExistException;
	public Optional<Cart> getCartByCustomer(int customerId);
	public String clearCart(int cartId) throws CartDoesNotExistException;
	public List<Cart> getAll();

}

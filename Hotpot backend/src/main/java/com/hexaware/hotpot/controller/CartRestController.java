
package com.hexaware.hotpot.controller;
/*
 * 
 * auth: Varshinee
 * controller class .
 */

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.exception.CartAlreadyExistsException;
import com.hexaware.hotpot.exception.CartDoesNotExistException;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.service.ICartService;

import jakarta.validation.Valid;
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/cart")
public class CartRestController {
	@Autowired
	ICartService service;
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/add/{customerId}")
	public Cart createCart(@Valid @PathVariable Integer customerId) throws CartAlreadyExistsException, CustomerNotFoundException {
		return service.createCart(customerId);
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("get/{cartId}")
	public Cart getCartById(@PathVariable Integer cartId) throws CartDoesNotExistException{
		return service.getCartById(cartId);
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/get")
	public List<Cart>getAll(){
		return service.getAll();
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/getbycustomer/{customerId}")	
	public Optional<Cart> getByCustomer(@PathVariable Integer customerId){
		return service.getCartByCustomer(customerId);
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@DeleteMapping("delete/{cartId}")
	public String deleteCart(@PathVariable Integer cartId) throws CartDoesNotExistException {
		service.clearCart(cartId);
		return "Cart deleted";
	}
	
	

}

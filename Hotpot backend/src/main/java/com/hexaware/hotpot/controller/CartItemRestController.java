/*
 * 
 * auth: Varshinee
 * controller class .
 */
package com.hexaware.hotpot.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hotpot.dto.CartItemDto;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.exception.CartDoesNotExistException;
import com.hexaware.hotpot.exception.CartItemNotFoundException;
import com.hexaware.hotpot.service.ICartItemService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/cartitem")
public class CartItemRestController {
	private static final Logger log = LoggerFactory.getLogger(CartItemRestController.class);
	@Autowired
	ICartItemService service;
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/add")
	public CartItems addItem(@Valid @RequestBody CartItemDto dto) throws CartDoesNotExistException {
		log.info("Item has been added to cart");
		return service.addItem(dto);
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/get/{cartId}")
	public List<CartItems> getAll(@PathVariable Integer cartId) throws CartDoesNotExistException{
		return service.getItemsInCart(cartId);
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@PutMapping("/update/{quantity}/{cartItemId}")
	public String update(@Valid @PathVariable Integer quantity,@PathVariable Integer cartItemId) {
		service.updateItemQuantity(quantity,cartItemId);
		return "Updated successfully";
		
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/getbyid/{cartItemId}")
	public CartItems getById(@PathVariable Integer cartItemId) throws CartItemNotFoundException {
		return service.getById(cartItemId);
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@DeleteMapping("/delete/{cartItemId}")
	public String delete(@PathVariable Integer cartItemId) throws CartItemNotFoundException {
		log.info("item has been removed");
		service.removeItem(cartItemId);
		return "Deleted successfully";
	}

}

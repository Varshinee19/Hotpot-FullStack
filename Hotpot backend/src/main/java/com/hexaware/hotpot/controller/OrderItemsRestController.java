package com.hexaware.hotpot.controller;
/*
 * 
 * auth: Varshinee
 * controller class .
 */
import java.util.List;

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

import com.hexaware.hotpot.dto.OrderItemsDto;
import com.hexaware.hotpot.entities.OrderItems;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderItemNotAvailableException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
import com.hexaware.hotpot.service.IOrderItemService;

import jakarta.validation.Valid;
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/orderitems")
public class OrderItemsRestController {
	@Autowired
	IOrderItemService service;
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/add/{orderId}")
	public OrderItems addOrderItem(@Valid @PathVariable Integer orderId,@RequestBody OrderItemsDto dto) throws OrderNotExistException {
		return service.addItem(orderId, dto);
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
	@GetMapping("/getbyorder/{orderId}")
	public List<OrderItems> getByOrder(@PathVariable Integer orderId) throws OrderNotExistException{
		return service.getItemsByOrder(orderId);
	}
	@PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN')or hasRole('RESTAURANT')")
	@PutMapping("/update/{orderItemId}")
	public OrderItems updateOrder(@Valid @PathVariable Integer orderItemId,@RequestBody OrderItemsDto dto) throws OrderItemNotAvailableException {
		return service.updateItem(orderItemId,dto);
	}
	 @PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/getbyid/{orderItemId}")
	public OrderItems getById(@PathVariable Integer orderItemId) throws OrderItemNotAvailableException{
		return service.getById(orderItemId);
	}
	 @PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN')")
	@DeleteMapping("/delete/{orderItemId}")
	public String deleteItem(@PathVariable Integer orderItemId) throws OrderItemNotAvailableException {
		service.removeItem(orderItemId);
		return "Item removed successfully";
	}
	 @PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN') or hasRole('RESTAURANT')")
	 @GetMapping("getbyrest/{restaurantId}")
    public List<OrderItems> getByRest(@PathVariable Integer restaurantId) throws RestaurantNotFoundException{
    	return service.getByRest(restaurantId);
    }
	 @PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN')")
	 @GetMapping("getbycust/{customerId}")
    public List<OrderItems> getByCust(@PathVariable Integer customerId) throws CustomerNotFoundException{
    	return service.getByCust(customerId);
    }
	

}

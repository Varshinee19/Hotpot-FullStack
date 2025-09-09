package com.hexaware.hotpot.controller;
/*
 * 
 * auth: Varshinee
 * controller class .
 */
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

import com.hexaware.hotpot.dto.OrderResponseDto;
import com.hexaware.hotpot.dto.OrdersDto;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
import com.hexaware.hotpot.service.IOrderService;

import jakarta.validation.Valid;

@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("/api/order")
public class OrderRestController {
	private static final Logger log = LoggerFactory.getLogger(OrderRestController.class);
	@Autowired
	IOrderService service;
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("/add/{customerId}")
	public Orders addOrder(@Valid @PathVariable Integer customerId, @RequestBody OrdersDto dto) {
		log.info("order has been placed");
		return service.placeOrder(customerId,dto);
	}
	@PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN')")
	@PutMapping("/update/{orderId}")
	public Orders updateOrder(@Valid @PathVariable Integer orderId,@RequestBody OrdersDto dto) throws OrderNotExistException {
		return service.updateOrder(orderId,dto);
	}
	@PreAuthorize("hasRole('CUSTOMER')or hasRole('ADMIN')")
	@GetMapping("/getbycustomer/{customerId}")
	public List<Orders> getOrdersByCust(@PathVariable Integer customerId) throws CustomerNotFoundException{
		return service.getOrdersByCustomer(customerId);
	}
	@PreAuthorize("hasRole('ADMIN')or hasRole('RESTAURANT')")
	@GetMapping("/getbyrestaurant/{restaurantId}")
	public List<Orders> getOrdersByRest(@PathVariable Integer restaurantId) throws RestaurantNotFoundException{
		return service.getOrdersByRestaurant(restaurantId);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getby")
	public List<Orders> getOrders(){
		return service.getAll();
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@GetMapping("/getbyid/{orderId}")
	public Orders getById(@PathVariable Integer orderId) throws OrderNotExistException{
		return service.getById(orderId);
	}
	@PreAuthorize("hasRole('ADMIN')or hasRole('RESTAURANT')")
	@PutMapping("updatestatus/{status}/{orderId}")
	public String updateStatus(@Valid @PathVariable String status,@PathVariable Integer orderId) throws OrderNotExistException {
		log.info("order status has been changed");
		return service.updateOrderStatus(status, orderId);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getall")
	public List<OrderResponseDto> getOrdersWithItems(){
		return service.getOrderWithItems();
	}
	@PreAuthorize("hasRole('CUSTOMER')")
	@DeleteMapping("/delete/{orderId}")
	public String deleteOrder(@PathVariable Integer orderId) throws OrderNotExistException {
		return service.cancelOrder(orderId);
		
	}
	
	

}

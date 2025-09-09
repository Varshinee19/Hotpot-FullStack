/*
 * OrderServiceImpl.java
 * modified:13-08-25
 * Auth: Varshinee K
 *
 *
 * Implements business logic related to orders.

 */
package com.hexaware.hotpot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.dto.OrderItemsDto;
import com.hexaware.hotpot.dto.OrderResponseDto;
import com.hexaware.hotpot.dto.OrdersDto;
import com.hexaware.hotpot.entities.Cart;
import com.hexaware.hotpot.entities.CartItems;
import com.hexaware.hotpot.entities.Customer;
import com.hexaware.hotpot.entities.OrderItems;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
import com.hexaware.hotpot.repository.CartItemsRepository;
import com.hexaware.hotpot.repository.CartRepository;
import com.hexaware.hotpot.repository.CustomerRepository;
import com.hexaware.hotpot.repository.OrderItemsRepository;
import com.hexaware.hotpot.repository.OrdersRepository;
import com.hexaware.hotpot.repository.RestaurantRepository;

@Service
public class OrderImpl implements IOrderService {
	@Autowired
	OrdersRepository repo;
	@Autowired
	CustomerRepository custRepo;
	@Autowired
	CartItemsRepository cartRepo;
	@Autowired
	OrderItemsRepository itemRepo;
	@Autowired
	CartRepository cRepo;
	@Autowired
	RestaurantRepository rRepo;
	@Autowired
	INotificationService notify;

	@Override
	public Orders placeOrder(int customerId, OrdersDto dto) {
		Customer customer = custRepo.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
		Cart cart = customer.getCart();
		List<CartItems> cartItems = cartRepo.findByCartCartId(cart.getCartId());
		double totalPrice = cartItems.stream().mapToDouble(ci -> ci.getMenu().getPrice() * ci.getQuantity()).sum();
		Orders order = new Orders();
		order.setCustomer(customer);
		order.setDeliveryAddress(dto.getDeliveryAddress());
		order.setTotalPrice(totalPrice);
		order.setStatus(dto.getStatus());
		order.setRestaurant(cartItems.get(0).getMenu().getRestaurant());
		order = repo.save(order);
		List<OrderItems> savedOrderItems = new ArrayList<>();
		for (CartItems ci : cartItems) {
			OrderItems orderItem = new OrderItems(); 
			orderItem.setOrder(order); 
			orderItem.setMenu(ci.getMenu());
			orderItem.setItemName(ci.getMenu().getItemName());
			orderItem.setQuantity(ci.getQuantity()); 
			orderItem.setPrice(ci.getMenu().getPrice()); 
			itemRepo.save(orderItem);
			savedOrderItems.add(orderItem);
		}
		notify.sendNotification(customerId,"Your order has been placed successfully");
		cartItems.forEach(item -> cartRepo.delete(item));
		return order;
	}

	@Override
	public Orders updateOrder(Integer orderId, OrdersDto dto) throws OrderNotExistException{
		// TODO Auto-generated method stub
		Orders ord = repo.findById(orderId).orElseThrow(()->new OrderNotExistException());
		ord.setTotalPrice(dto.getTotalPrice());
		ord.setDeliveryAddress(dto.getDeliveryAddress());
		ord.setStatus(dto.getStatus());
		return repo.save(ord);
	}

	@Override
	public List<Orders> getOrdersByCustomer(int customerId) throws CustomerNotFoundException {
		// TODO Auto-generated method stub
		custRepo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer not found with id "+customerId));
		return repo.findByCustomerCustomerId(customerId);
	}

	@Override
	public List<Orders> getOrdersByRestaurant(int restaurantId) throws RestaurantNotFoundException {
		rRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("Restaurant Not Found"));
		return repo.findByRestaurantRestaurantId(restaurantId);
	}

	@Override
	public String updateOrderStatus(String status, int orderId)throws OrderNotExistException {
	    Orders order = repo.findById(orderId).orElseThrow(() -> new OrderNotExistException());
	    order.setStatus(status);
	    repo.save(order);

	    notify.sendNotification(order.getCustomer().getCustomerId(),
	            "Your order" + orderId + " status has been updated to: " + status);

	    return "Updated successfully";
	}

	@Override
	public String cancelOrder(int orderId) throws OrderNotExistException{
		repo.findById(orderId).orElseThrow(()->new OrderNotExistException());
		repo.deleteById(orderId);
		return "Order cancelled";
	}

	@Override
	public List<Orders> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public Orders getById(int orderId) throws OrderNotExistException {
		
		// TODO Auto-generated method stub
		return repo.findById(orderId).orElseThrow(()->new OrderNotExistException());
	}

	@Override
	public List<OrderResponseDto> getOrderWithItems() {
		// TODO Auto-generated method stub
		return repo.findAll().stream().map(order -> {
	        OrderResponseDto dto = new OrderResponseDto();
	        dto.setOrderId(order.getOrderId());
	        dto.setStatus(order.getStatus());
	        dto.setDeliveryAddress(order.getDeliveryaddress());
	        dto.setTotalPrice(order.getTotalPrice());

	        // customer info
	        if (order.getCustomer() != null) {
	            dto.setCustomerId(order.getCustomer().getCustomerId());
	            dto.setCustomerName(order.getCustomer().getName());
	            dto.setCustomerEmail(order.getCustomer().getEmail());
	            dto.setCustomerPhone(order.getCustomer().getPhone());
	        }

	        // restaurant info
	        if (order.getRestaurant() != null) {
	            dto.setRestaurantName(order.getRestaurant().getRestaurantName());
	            dto.setRestaurantId(order.getRestaurant().getRestaurantId());
	        }

	        List<OrderItemsDto> items = order.getOrderItems().stream().map(item -> {
	            OrderItemsDto iDto = new OrderItemsDto();
	            iDto.setItemName(item.getItemName());
	            iDto.setQuantity(item.getQuantity());
	            iDto.setPrice(item.getPrice());
	            iDto.setOrderId(order.getOrderId());       
	            iDto.setMenuId(item.getMenu().getMenuId());
	            return iDto;
	        }).toList();

	        dto.setItems(items);

	        return dto;
	    }).toList();
	}

	

}

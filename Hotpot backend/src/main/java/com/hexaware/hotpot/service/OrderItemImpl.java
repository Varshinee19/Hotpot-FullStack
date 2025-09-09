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

import com.hexaware.hotpot.dto.OrderItemsDto;
import com.hexaware.hotpot.entities.Menu;
import com.hexaware.hotpot.entities.OrderItems;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderItemNotAvailableException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;
import com.hexaware.hotpot.repository.CustomerRepository;
import com.hexaware.hotpot.repository.MenuRepository;
import com.hexaware.hotpot.repository.OrderItemsRepository;
import com.hexaware.hotpot.repository.OrdersRepository;
import com.hexaware.hotpot.repository.RestaurantRepository;

@Service
public class OrderItemImpl implements IOrderItemService {
	@Autowired
	OrderItemsRepository repo;
	@Autowired
	OrdersRepository orderRepo;
	@Autowired
	MenuRepository menuRepo;
	@Autowired
	RestaurantRepository rRepo;
	@Autowired
	CustomerRepository cRepo;

	public OrderItems addItem(Integer orderId, OrderItemsDto orderItem) throws OrderNotExistException {
		// TODO Auto-generated method stub
		Orders order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotExistException());
		Menu menu = menuRepo.findById(orderItem.getMenuId()).orElse(null);
		OrderItems item = new OrderItems();
		item.setItemName(orderItem.getItemName());
		item.setQuantity(orderItem.getQuantity());
		item.setPrice(orderItem.getPrice());
		item.setOrder(order);
		item.setMenu(menu);
		order.addItem(item);
		double total = order.getOrderItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();
		order.setTotalPrice(total);
		orderRepo.save(order);
		return repo.save(item);
	}

	@Override
	public List<OrderItems> getItemsByOrder(int orderId) throws OrderNotExistException {
		orderRepo.findById(orderId).orElseThrow(() -> new OrderNotExistException());

		return repo.findByOrderOrderId(orderId);
	}

	@Override
	public OrderItems updateItem(Integer orderItemId, OrderItemsDto dto) throws OrderItemNotAvailableException {
		OrderItems orderItem = repo.findById(orderItemId).orElseThrow(() -> new OrderItemNotAvailableException());

		// Update OrderItem fields
		orderItem.setItemName(dto.getItemName());
		orderItem.setQuantity(dto.getQuantity());
		orderItem.setPrice(dto.getPrice());

		// Save the updated OrderItem
		OrderItems updatedItem = repo.save(orderItem);

		// Update parent Order total price
		Orders parentOrder = updatedItem.getOrder();
		if (parentOrder != null) {
			double total = repo.findByOrderOrderId(parentOrder.getOrderId()).stream()
					.mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
			parentOrder.setTotalPrice(total);
			orderRepo.save(parentOrder);
		}

		return updatedItem;

	}
	@Override
	public OrderItems getById(int orderItemId) throws OrderItemNotAvailableException {

		return repo.findById(orderItemId).orElseThrow(() -> new OrderItemNotAvailableException());
	}

	@Override
	public String removeItem(int orderItemId) throws OrderItemNotAvailableException {
		OrderItems item = repo.findById(orderItemId).orElseThrow(() -> new OrderItemNotAvailableException());

		Orders parentOrder = item.getOrder();
		if (parentOrder != null) {
			parentOrder.getOrderItems().remove(item); // remove from list
			double total = parentOrder.getOrderItems().stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
			parentOrder.setTotalPrice(total);
			orderRepo.save(parentOrder);
		}
		repo.deleteById(orderItemId);
		return "Deleted successfully";
	}

	public List<OrderItems> getByRest(int restaurantId) throws RestaurantNotFoundException {
		rRepo.findById(restaurantId).orElseThrow(()->new RestaurantNotFoundException("No restaurant found"));
		return repo.findByOrderRestaurantRestaurantId(restaurantId);
	}

	@Override
	public List<OrderItems> getByCust(int customerId) throws CustomerNotFoundException {
		
		cRepo.findById(customerId).orElseThrow(()->new CustomerNotFoundException("Customer not Found"));
		return repo.findByOrderCustomerCustomerId(customerId);
	}

}

package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import java.util.List;

import com.hexaware.hotpot.dto.OrderItemsDto;
import com.hexaware.hotpot.entities.OrderItems;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderItemNotAvailableException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;

public interface IOrderItemService {
	public OrderItems addItem(Integer orderId,OrderItemsDto orderItem)throws OrderNotExistException;
	public List<OrderItems> getItemsByOrder(int orderId) throws OrderNotExistException;
	public OrderItems updateItem(Integer orderItemId,OrderItemsDto orderItem) throws OrderItemNotAvailableException;
	public OrderItems getById(int orderItemId)throws OrderItemNotAvailableException;
	public String removeItem(int orderItemId)throws OrderItemNotAvailableException;
	public List<OrderItems> getByRest(int restaurantId) throws RestaurantNotFoundException;
	public List<OrderItems> getByCust(int customerId)throws CustomerNotFoundException;

}

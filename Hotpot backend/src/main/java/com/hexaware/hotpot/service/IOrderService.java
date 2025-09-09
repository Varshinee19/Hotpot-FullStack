package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import java.util.List;

import com.hexaware.hotpot.dto.OrderResponseDto;
import com.hexaware.hotpot.dto.OrdersDto;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.exception.CustomerNotFoundException;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.RestaurantNotFoundException;

public interface IOrderService {
	public Orders placeOrder(int customerId,OrdersDto dto);
	public Orders updateOrder(Integer OrderId,OrdersDto dto) throws OrderNotExistException;
	public List<Orders> getOrdersByCustomer(int customerId) throws CustomerNotFoundException;
	public List<Orders> getOrdersByRestaurant(int restaurantId)throws RestaurantNotFoundException;
	public List<Orders> getAll();
	public List<OrderResponseDto> getOrderWithItems();
	public Orders getById(int orderId)throws OrderNotExistException;
	public String updateOrderStatus(String Status,int orderId) throws OrderNotExistException;
	public String cancelOrder(int orderId)throws OrderNotExistException;

}

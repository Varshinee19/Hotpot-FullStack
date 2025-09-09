/*
 * Orders.java
 * Author: Varshinee K
 * Represents orders in the system and links them to users and order items.
 */
package com.hexaware.hotpot.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Orders {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderId;
	private double totalPrice;
	private String deliveryAddress;
	private String status;
	
	@ManyToOne
	@JoinColumn(name="customerId")
	@JsonIgnore
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name="restaurantId")
	@JsonIgnore
	private Restaurant restaurant;
	@OneToMany(mappedBy="order",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<OrderItems> orderItems;
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Payment payment;
	public Orders() {
		
	}
	public Orders(int orderId, double totalPrice, String deliveryAddress, String status) {
		super();
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.deliveryAddress = deliveryAddress;
		this.status = status;
	}
	public Orders(int orderId, double totalPrice, String deliveryAddress, String status, Customer customer,
			Restaurant restaurant, List<OrderItems> orderItems) {
		super();
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.deliveryAddress = deliveryAddress;
		this.status = status;
		this.customer = customer;
		this.restaurant = restaurant;
		this.orderItems = orderItems;
	}
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderid(int orderId) {
		this.orderId = orderId;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getDeliveryaddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public List<OrderItems> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItems> orderItems) {
		this.orderItems = orderItems;
	}
    public int getCustomerId() {
        return customer != null ? customer.getCustomerId() : 0;
    }

    public int getRestaurantId() {
        return restaurant != null ? restaurant.getRestaurantId() : 0;
    }
    public void addItem(OrderItems item) {
        orderItems.add(item);      // add to the list
        item.setOrder(this);  // set the back-reference
    }
	
	
	

}

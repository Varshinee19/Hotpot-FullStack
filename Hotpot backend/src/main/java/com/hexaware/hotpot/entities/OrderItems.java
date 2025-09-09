/*
 * OrderItems.java
 * Author: Varshinee K
 * Represents individual items within an order and links them to orders and menu items.
 */
package com.hexaware.hotpot.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItems {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderItemId;
	private String itemName;
	private int quantity;
	private double price;
	
	@ManyToOne
	@JoinColumn(name="orderId")
    @JsonIgnore
	private Orders order;
	
    @ManyToOne
	@JoinColumn(name="menuId")
    @JsonIgnore
	private Menu menu;
    
    public OrderItems() {
    	
    }
	public OrderItems(int orderItemId,String itemName ,int quantity, double price) {
		super();
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.price = price;
		this.itemName=itemName;
	}

	public OrderItems(int orderItemId, int quantity, double price, Orders order, Menu menu) {
		super();
		this.orderItemId = orderItemId;
		this.quantity = quantity;
		this.price = price;
		this.order = order;
		this.menu = menu;
	}
	public int getOrderItemId() {
		return orderItemId;
	}
	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Orders getOrder() {
		return order;
	}
	public void setOrder(Orders order) {
		this.order = order;
	}
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
    public int getOrderId() {
        return order != null ? order.getOrderId() : 0;
    }

    public int getMenuId() {
        return menu != null ? menu.getMenuId() : 0;
    }

	

	

}

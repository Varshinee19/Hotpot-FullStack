package com.hexaware.hotpot.entities;

/*
 * Menu.java
 * auth: Varshinee
 * Represents Restaurant creation and mappings.
 */
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;


@Entity
public class Restaurant {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int restaurantId;
	private String restaurantName;
	private String restaurantAddress;
	private String phoneNo;
	@OneToMany(mappedBy="restaurant", cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Menu> menuItems;
	@OneToMany(mappedBy="restaurant",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<Orders> orders;
	
	public Restaurant() {
		
	}
	public Restaurant(int restaurantId, String restaurantName, String restaurantAddress, String phoneNo) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.restaurantAddress = restaurantAddress;
		this.phoneNo = phoneNo;
	}

	public Restaurant(int restaurantId, String restaurantName, String restaurantAddress, String phoneNo,
			List<Menu> menuItems, List<Orders> orders) {
		super();
		this.restaurantId = restaurantId;
		this.restaurantName = restaurantName;
		this.restaurantAddress = restaurantAddress;
		this.phoneNo = phoneNo;
		this.menuItems = menuItems;
		this.orders = orders;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getRestaurantAddress() {
		return restaurantAddress;
	}
	public void setRestaurantAddress(String restaurantAddress) {
		this.restaurantAddress = restaurantAddress;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public List<Menu> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<Menu> menuItems) {
		this.menuItems = menuItems;
	}
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

	

	
	
	
	
	

}

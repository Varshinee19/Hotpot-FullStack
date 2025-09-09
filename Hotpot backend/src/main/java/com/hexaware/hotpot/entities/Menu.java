/*
 * Menu.java
 * auth: Varshinee
 * Represents Menu creation and mappings.
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


@Entity
public class Menu {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int menuId;
	private String itemName;
	private String description;
	private double price;
	private String type;
	private String image;
	private String info;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="categoryId")

	private MenuCategories menuCategory;
	@ManyToOne
	@JoinColumn(name="restaurantId")
    @JsonIgnore
	private Restaurant restaurant;
	@OneToMany(mappedBy="menu",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<OrderItems> orderItems;
	@OneToMany(mappedBy="menu",cascade=CascadeType.ALL)
    @JsonIgnore
	private List<CartItems> cartItems;
	
	public Menu() {
		
	}
	
	public Menu(int menuId, String itemName, String description, double price, String type, String image, String info) {
		super();
		this.menuId = menuId;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.type = type;
		this.image = image;
		this.info = info;
	}

	public Menu(int menuId, String itemName, String description, double price, String type, String image, String info,
			MenuCategories menuCategory, Restaurant restaurant, List<OrderItems> orderItems,
			List<CartItems> cartItems) {
		super();
		this.menuId = menuId;
		this.itemName = itemName;
		this.description = description;
		this.price = price;
		this.type = type;
		this.image = image;
		this.info = info;
		this.menuCategory = menuCategory;
		this.restaurant = restaurant;
		this.orderItems = orderItems;
		this.cartItems = cartItems;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public MenuCategories getMenuCategory() {
		return menuCategory;
	}

	public void setMenuCategory(MenuCategories menuCategory) {
		this.menuCategory = menuCategory;
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

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}
    public int getRestaurantId() {
        return restaurant != null ? restaurant.getRestaurantId() : 0;
    }
    
    public int getCategoryId() {
        return menuCategory != null ? menuCategory.getCategoryId() : 0;
    }
	


	

	

}

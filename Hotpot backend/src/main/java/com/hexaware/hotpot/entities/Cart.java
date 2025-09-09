/*
 * Cart.java
 * auth: Varshinee K
 * modified date:01-08-25
 * Represents cart creation and mappings.
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int cartId;
	@OneToOne
	@JoinColumn(name="custid")
	@JsonIgnore
	private Customer customer;
	@OneToMany(mappedBy="cart",cascade=CascadeType.ALL)
	@JsonIgnore
	private List<CartItems> cartItems;
	
	public Cart() {
		
	}
	public Cart(int cartId, Customer customer, List<CartItems> cartItems) {
		super();
		this.cartId = cartId;
		this.customer = customer;
		this.cartItems = cartItems;
	}
	public int getCartId() {
		return cartId;
	}
	public void setCartId(int cartid) {
		this.cartId = cartid;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<CartItems> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}
    public int getCustomerId() {
        return customer != null ? customer.getCustomerId() : 0;
    }
	
	
	

}

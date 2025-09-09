/*
 * Customer.java
 * auth: Varshinee K
 * modified date:01-08-25
 * Represents the users of the app.
 */

package com.hexaware.hotpot.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hexaware.hotpot.entitiesenum.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerId;
	private String name;
	private String gender;
	private String email;
	private String phone;
	private String address;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	@OneToOne(mappedBy="customer",cascade=CascadeType.ALL)
	@JsonIgnore
	private Cart cart;
	@OneToMany(mappedBy="customer",cascade=CascadeType.ALL,orphanRemoval = true)
	@JsonIgnore
	private List<Orders> orders;
	
	
	public Customer() {
		
	}
	
	public Customer(int customerId,String gender, String name, String email, String phone, String address,Role role,String password) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.gender=gender;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role=role;
		this.password=password;
	}

	public Customer(int customerId, String name,String gender ,String email, String phone, String address,Role role,Cart cart,
			List<Orders> orders) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.gender=gender;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role=role;
		this.cart = cart;
		this.orders = orders;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

	@Override
	public String toString() {
		return "Customer [custid=" + customerId + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", role=" + role + "]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	



	


	
	
	

}

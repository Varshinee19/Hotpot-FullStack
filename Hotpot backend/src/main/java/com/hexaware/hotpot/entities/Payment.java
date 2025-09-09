/*
 * Payment.java
 * Author: Varshinee K
 * Represents payment details for an order and links each payment to its order.
 */

package com.hexaware.hotpot.entities;

import jakarta.persistence.*;

@Entity
public class Payment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int paymentId;
	private double amount;
	private String mode;
	private String status;
	@OneToOne
	@JoinColumn(name="orderid")
	private Orders order;
	public Payment() {
		
	}
	public Payment(int paymentId, double amount, String mode, String status) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.mode = mode;
		this.status = status;
	}

	public Payment(int paymentId, double amount, String mode, String status, Orders order) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.mode = mode;
		this.status = status;
		this.order = order;
	}

	public int getPaymentid() {
		return paymentId;
	}

	public void setPaymentid(int paymentid) {
		this.paymentId = paymentid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}
	

	
	

}

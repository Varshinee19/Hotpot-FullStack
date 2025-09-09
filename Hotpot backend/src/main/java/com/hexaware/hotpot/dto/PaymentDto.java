/*
 * 
 * auth: Varshinee
 * DTO class for validation.
 */
package com.hexaware.hotpot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data
public class PaymentDto {
	@NotNull
	private int paymentId;
	@NotNull(message="Amount can't be null")
	@Min(1)
	private double amount;
	@NotEmpty(message="Payment mode must be specified")
	@Pattern(regexp="^(UPI|NetBanking|COD|Card)$")
	private String mode;
	@NotBlank(message="Payment status cannot be blank")
	@Pattern(regexp="^(pending|success|failed)$")
	private String status;
	private int orderId;
	
	 public PaymentDto() {}

	    // Getters and Setters
	    public int getPaymentId() {
	        return paymentId;
	    }

	    public void setPaymentId(int paymentId) {
	        this.paymentId = paymentId;
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

	    public int getOrderId() {
	        return orderId;
	    }

	    public void setOrderId(int orderId) {
	        this.orderId = orderId;
	    }

}

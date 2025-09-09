package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * Service Interface Class.
 */
import com.hexaware.hotpot.dto.PaymentDto;
import com.hexaware.hotpot.entities.Payment;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.PaymentNotFoundException;

public interface IPaymentService {
	public Payment processPayment(int orderId,PaymentDto dto) throws OrderNotExistException;
	public Payment getPaymentById(int paymentId)throws PaymentNotFoundException;
	public Payment getPaymentByOrder(int orderId)throws OrderNotExistException;

}

package com.hexaware.hotpot.controller;
/*
 * 
 * auth: Varshinee
 * controller class .
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.hotpot.dto.PaymentDto;
import com.hexaware.hotpot.entities.Payment;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.PaymentNotFoundException;
import com.hexaware.hotpot.service.IPaymentService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin(origins="http://localhost:5173")
@RestController
@RequestMapping("api/payment")
public class PaymentRestController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerRestController.class);
	@Autowired
	IPaymentService service;
	@PreAuthorize("hasRole('CUSTOMER')")
	@PostMapping("process/{orderId}")
	public Payment processPayment(@Valid @PathVariable Integer orderId,@RequestBody PaymentDto dto) throws OrderNotExistException {
		logger.info("payment has processed");
		return service.processPayment(orderId, dto);
		
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')or hasRole('RESTAURANT')")
	@GetMapping("getbyid/{paymentId}")
	public Payment getById(@PathVariable Integer paymentId) throws PaymentNotFoundException {
		return service.getPaymentById(paymentId);
	}
	@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN') or hasRole('RESTAURANT')")
	@GetMapping("getbyorder/{orderId}")
	public Payment getByOrder(@PathVariable Integer orderId) throws OrderNotExistException {
		return service.getPaymentByOrder(orderId);
	}

}

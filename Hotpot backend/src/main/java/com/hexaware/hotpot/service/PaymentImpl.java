package com.hexaware.hotpot.service;
/*
 * 
 * auth: Varshinee
 * modified:14-08-25
 * Service Implementation Class.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.dto.PaymentDto;
import com.hexaware.hotpot.entities.Orders;
import com.hexaware.hotpot.entities.Payment;
import com.hexaware.hotpot.exception.OrderNotExistException;
import com.hexaware.hotpot.exception.PaymentNotFoundException;
import com.hexaware.hotpot.repository.OrdersRepository;
import com.hexaware.hotpot.repository.PaymentRepository;

@Service
public class PaymentImpl implements IPaymentService {
	@Autowired
	PaymentRepository repo;
	@Autowired
	OrdersRepository orderRepo;
	@Autowired
	INotificationService notify;

	@Override
	public Payment processPayment(int orderId, PaymentDto dto) throws OrderNotExistException {
		Orders order = orderRepo.findById(orderId).orElseThrow(() -> new OrderNotExistException());
		Payment pay = new Payment();
		pay.setOrder(order);
		pay.setAmount(dto.getAmount());
		pay.setMode(dto.getMode());

		if (dto.getAmount() < order.getTotalPrice()) {
			pay.setStatus("failed");
			Payment savedPay = repo.save(pay);
			notify.sendNotification(order.getCustomerId(), "Payment failed");
			return savedPay;
		}
		pay.setStatus("success");
		Payment savedPay = repo.save(pay);
		notify.sendNotification(order.getCustomerId(), "Payment success");
		return savedPay;

	}

	@Override
	public Payment getPaymentById(int paymentId) throws PaymentNotFoundException {

		return repo.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException());
	}

	@Override
	public Payment getPaymentByOrder(int orderId) throws OrderNotExistException {
		orderRepo.findById(orderId).orElseThrow(() -> new OrderNotExistException());
		return repo.findByOrderOrderId(orderId);

	}

}

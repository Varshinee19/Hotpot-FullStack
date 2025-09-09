package com.hexaware.hotpot.service;

import org.springframework.stereotype.Service;

@Service
public interface INotificationService {
	void sendNotification(int customerId,String message);

}

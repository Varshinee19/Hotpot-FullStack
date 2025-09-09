package com.hexaware.hotpot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.hotpot.entities.Notification;
import com.hexaware.hotpot.repository.NotificationRepository;
@Service
public class NotificationImpl implements INotificationService {
	@Autowired
	NotificationRepository repo;

	@Override
	public void sendNotification(int customerId, String message) {
		Notification notification=new Notification(customerId,message);
		repo.save(notification);
		System.out.println("Notification :" +message);
		
	}

}

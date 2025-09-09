package com.hexaware.hotpot.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Notification {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private int notificationId;
	private int customerId;
	private String message;
	private LocalDateTime time;
	public Notification(int customerId, String message) {
		super();
		this.customerId = customerId;
		this.message = message;
	}

}

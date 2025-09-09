package com.hexaware.hotpot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.hotpot.entities.Notification;
@Repository
public interface NotificationRepository extends JpaRepository<Notification,Integer>{
	

}

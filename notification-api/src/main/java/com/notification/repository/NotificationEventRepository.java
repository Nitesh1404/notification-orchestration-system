package com.notification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.notification.entity.NotificationEvent;

@Repository
public interface NotificationEventRepository extends JpaRepository<NotificationEvent, Long>{

	public boolean existsByIdempotencyKey(String idempotencyKey); 
	
	public Optional<NotificationEvent> findfindByEventId(String eventId);
	
}

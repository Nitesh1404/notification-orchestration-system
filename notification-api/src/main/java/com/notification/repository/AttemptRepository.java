package com.notification.repository;

import com.notification.entity.NotificationAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttemptRepository extends JpaRepository<NotificationAttempt , Long> {
    public NotificationAttempt findTopByChannelIdOrderByAttemptNumberDesc(Long channelId);
}

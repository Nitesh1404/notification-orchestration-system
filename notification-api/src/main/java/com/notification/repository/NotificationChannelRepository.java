package com.notification.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.notification.entity.NotificationChannelReq;


@Repository
public interface NotificationChannelRepository extends JpaRepository<NotificationChannelReq, Long> {

    @Query(value = """
            SELECT *
            FROM notification_channel
            WHERE status = 'PENDING'
            AND (next_retry_at IS NULL OR next_retry_at <= now())
            ORDER BY created_at
            LIMIT 100
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
    public List<NotificationChannelReq> fetchPendingChannels();

    @Query("""
                SELECT c.status
                FROM NotificationChannelReq c
                WHERE c.event.id = :eventId
            """)
    List<String> findStatusesByEventId(Long eventId);

    Optional<NotificationChannelReq> findByProviderRefId(String providerRefId);


}

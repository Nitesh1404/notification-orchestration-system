package com.notification.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notification_channel")
@Data
public class NotificationChannelReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "channel_type", nullable = false)
    private String channelType;

    @Column(nullable = false)
    private String recipient;

    @Column(name = "template_code")
    private String templateCode;

    private String provider;

    @Column(name = "provider_ref_id")
    private String providerRefId;

    @Column(nullable = false)
    private String status;

    @Column(name = "retry_count")
    private Integer retryCount;

    @Column(name = "error_message")
    private String errorMessage;

    @Column(name = "next_retry_at")
    private LocalDateTime nextRetryAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "event_id",                 // column in this table
        referencedColumnName = "event_id"  // column in parent table
    )
    private NotificationEvent event;
}

package com.notification.entity;

import java.time.LocalDateTime;

import com.notification.constants.NotificationChannel;

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
@Table(name = "notification_attempt")
@Data
public class NotificationAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

    @Column(columnDefinition = "jsonb")
    private String requestPayload;

    @Column(columnDefinition = "jsonb")
    private String responsePayload;

    @Column(name = "attempt_number", nullable = false)
    private Integer attemptNumber;

    @Column(nullable = false)
    private String status;

    @Column(name = "error_message")
    private String errorMessage;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id")
    private NotificationChannelReq channel;
}
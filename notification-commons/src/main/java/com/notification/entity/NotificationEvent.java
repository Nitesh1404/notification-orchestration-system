package com.notification.entity;

import java.time.LocalDateTime;
import java.util.List;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "notification_event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", unique = true, nullable = false)
    private String eventId;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "overall_status", nullable = false)
    private String status;

    @Column(name = "reference_id")
    private String referenceId;

    @Column(name = "idempotency_key", unique = true)
    private String idempotencyKey;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Business-key mapping
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<NotificationChannelReq> channels;
}

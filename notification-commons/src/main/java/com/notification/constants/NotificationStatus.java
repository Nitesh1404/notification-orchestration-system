package com.notification.constants;

public enum NotificationStatus {
    CREATED("New", "The notification has been created."),
    VALIDATED("Checked", "The notification has been validated."),
    QUEUED("In Queue", "Waiting in the message queue."),
    PROCESSING("Busy", "Currently being processed."),
    SENT("Dispatched", "Dispatched to the provider."),
    FAILED("Error", "Delivery failed."),
    RETRYING("Retrying", "Attempting to resend."),
    CANCELLED("Stopped", "The notification was cancelled."),
    PENDING("Awaiting", "Awaiting further action."),
    RECEIVED("Received", "Successfully received."),
    SUCCESS("Completed", "The process completed successfully."),
    DELIVERED("Delivered", "Message delivered successfully.");

    private final String status;
    private final String description;

    // Constructor to set both fields
    NotificationStatus(String status, String description) {
        this.status = status;
        this.description = description;
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}

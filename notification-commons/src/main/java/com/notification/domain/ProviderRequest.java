package com.notification.domain;

import lombok.Data;

@Data
public class ProviderRequest {
    private String to;
    private String body;
    private String webhookUrl;
}

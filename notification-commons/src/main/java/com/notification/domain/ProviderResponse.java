package com.notification.domain;

import lombok.Data;

@Data
public class ProviderResponse {
    private String msgId;
    private String status;
    private String errorMessage;
}

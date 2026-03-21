package com.notification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="provider_config")
@Data
public class ProviderConfig {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "provider_name")
	private String providerName;
	
	@Column(name = "channel_type")
	private String ChannelType;
	
	@Column(name = "base_url")
	private String baseUrl;
	
	@Column(name = "priority")
	private int priority;
	
	@Column(name = "is_active")
	private boolean isActive;
	
}

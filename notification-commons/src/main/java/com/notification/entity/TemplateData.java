package com.notification.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "notification_template")
@Data
public class TemplateData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "event_type")
	private String eventType;
	
	@Column(name = "channel_type")
	private String channelType;
	
	@Column(name = "template_code")
	private String templateCode;
	
	@Column(name = "template_body")
	private String templateBody;
	
	@Column(name = "is_active")
	private boolean isActive;
	
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "updated_at")
	private Date updatedAt;
	
}

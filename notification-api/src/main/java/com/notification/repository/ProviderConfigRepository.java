package com.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.notification.entity.ProviderConfig;

public interface ProviderConfigRepository extends JpaRepository<ProviderConfig, Long> {
	
	@Query("""
		       SELECT p
		       FROM ProviderConfig p
		       WHERE p.channelType = :channelType
		       AND p.isActive = true
		       ORDER BY p.priority ASC
		       """)
	List<ProviderConfig> fetchProviders(@Param("channelType") String channelType);

}

package com.notification.repository;

import com.notification.entity.TemplateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TemplateDataRepository extends JpaRepository<TemplateData, Long> {

    Optional<TemplateData> findByTemplateCode(String templateCode);

}

package com.example.wodcrmapi.repository;

import com.example.wodcrmapi.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    List<ActivityLog> findByCompanyIdOrderByCreatedAtDesc(Long companyId);
    List<ActivityLog> findByUserIdOrderByCreatedAtDesc(Long userId);
}

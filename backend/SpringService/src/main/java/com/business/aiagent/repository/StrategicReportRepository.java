package com.business.aiagent.repository;

import com.business.aiagent.entity.StrategicReport;
import com.business.aiagent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategicReportRepository extends JpaRepository<StrategicReport, Long> {
    
    Page<StrategicReport> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    List<StrategicReport> findByUser(User user);
}

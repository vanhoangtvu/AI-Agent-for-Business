package com.business.aiagent.repository;

import com.business.aiagent.entity.ActivityLog;
import com.business.aiagent.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> {
    
    Page<ActivityLog> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    List<ActivityLog> findByUser(User user);
    
    Page<ActivityLog> findAllByOrderByCreatedAtDesc(Pageable pageable);
}

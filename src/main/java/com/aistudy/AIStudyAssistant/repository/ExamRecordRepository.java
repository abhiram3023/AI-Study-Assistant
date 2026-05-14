package com.aistudy.AIStudyAssistant.repository;

import com.aistudy.AIStudyAssistant.model.ExamRecord;
import com.aistudy.AIStudyAssistant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Long> {
    List<ExamRecord> findByUserOrderByIdDesc(User user);
}
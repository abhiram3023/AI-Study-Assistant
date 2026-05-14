package com.aistudy.AIStudyAssistant.repository;

import com.aistudy.AIStudyAssistant.model.Subject;
import com.aistudy.AIStudyAssistant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByUser(User user);

    Subject findByUserAndSubjectName(User user, String subjectName);
}
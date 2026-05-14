package com.aistudy.AIStudyAssistant.repository;
import com.aistudy.AIStudyAssistant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}

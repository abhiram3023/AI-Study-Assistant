package com.aistudy.AIStudyAssistant.model;

import jakarta.persistence.*;
import java.util.Locale;

@Entity
@Table(name = "exam_records")
public class ExamRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String examName;

    @Column(nullable = false)
    private Integer totalMarks;

    @Column(nullable = false)
    private Integer scoredMarks;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public ExamRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Integer getScoredMarks() {
        return scoredMarks;
    }

    public void setScoredMarks(Integer scoredMarks) {
        this.scoredMarks = scoredMarks;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    public double getPercentage() {
        if (totalMarks == null || totalMarks == 0 || scoredMarks == null) {
            return 0.0;
        }
        return (scoredMarks * 100.0) / totalMarks;
    }

    @Transient
    public String getPercentageText() {
        return String.format(Locale.US, "%.1f", getPercentage());
    }
}
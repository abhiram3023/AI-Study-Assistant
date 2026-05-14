package com.aistudy.AIStudyAssistant.controller;

import com.aistudy.AIStudyAssistant.model.ExamRecord;
import com.aistudy.AIStudyAssistant.model.Subject;
import com.aistudy.AIStudyAssistant.model.User;
import com.aistudy.AIStudyAssistant.repository.ExamRecordRepository;
import com.aistudy.AIStudyAssistant.repository.SubjectRepository;
import com.aistudy.AIStudyAssistant.service.RecommendationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ExamRecordRepository examRecordRepository;

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        List<Subject> subjects = subjectRepository.findByUser(user);
        List<ExamRecord> examRecords = examRecordRepository.findByUserOrderByIdDesc(user);

        model.addAttribute("user", user);
        model.addAttribute("subjects", subjects);
        model.addAttribute("examRecords", examRecords);

        model.addAttribute("totalSubjects", subjects.size());
        model.addAttribute("totalExams", examRecords.size());
        model.addAttribute("overallPercentage", recommendationService.calculateOverallPercentage(examRecords));
        model.addAttribute("bestSubject", recommendationService.getBestSubject(examRecords));
        model.addAttribute("weakSubject", recommendationService.getWeakestSubject(examRecords));

        model.addAttribute("subjectLabels", recommendationService.getSubjectLabels(examRecords));
        model.addAttribute("subjectData", recommendationService.getSubjectPercentages(examRecords));
        model.addAttribute("examLabels", recommendationService.getExamLabels(examRecords));
        model.addAttribute("examData", recommendationService.getExamPercentages(examRecords));

        model.addAttribute("recommendations", recommendationService.generateRecommendations(examRecords));

        return "dashboard";
    }

    @PostMapping("/add-subject")
    public String addSubject(@RequestParam("subjectName") String subjectName,
                             HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        String cleanedName = subjectName.trim();
        Subject existing = subjectRepository.findByUserAndSubjectName(user, cleanedName);

        if (existing == null) {
            Subject subject = new Subject();
            subject.setSubjectName(cleanedName);
            subject.setUser(user);
            subjectRepository.save(subject);
        }

        return "redirect:/dashboard";
    }

    @PostMapping("/add-exam")
    public String addExam(
            @RequestParam("examName") String examName,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("totalMarks") Integer totalMarks,
            @RequestParam("scoredMarks") Integer scoredMarks,
            HttpSession session) {

        User user = (User) session.getAttribute("loggedInUser");

        if (user == null) {
            return "redirect:/login";
        }

        Subject subject = subjectRepository.findById(subjectId).orElse(null);

        if (subject == null || subject.getUser() == null || !subject.getUser().getId().equals(user.getId())) {
            return "redirect:/dashboard";
        }

        ExamRecord record = new ExamRecord();
        record.setExamName(examName.trim());
        record.setSubject(subject);
        record.setTotalMarks(totalMarks);
        record.setScoredMarks(scoredMarks);
        record.setUser(user);

        examRecordRepository.save(record);

        return "redirect:/dashboard";
    }
}
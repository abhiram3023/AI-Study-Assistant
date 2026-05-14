package com.aistudy.AIStudyAssistant.service;

import com.aistudy.AIStudyAssistant.model.ExamRecord;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    public double calculateOverallPercentage(List<ExamRecord> records) {
        if (records == null || records.isEmpty()) {
            return 0.0;
        }

        int totalScored = records.stream()
                .mapToInt(r -> r.getScoredMarks() == null ? 0 : r.getScoredMarks())
                .sum();

        int totalMarks = records.stream()
                .mapToInt(r -> r.getTotalMarks() == null ? 0 : r.getTotalMarks())
                .sum();

        if (totalMarks == 0) {
            return 0.0;
        }

        return round((totalScored * 100.0) / totalMarks);
    }

    public List<String> getSubjectLabels(List<ExamRecord> records) {
        return new ArrayList<>(groupBySubject(records).keySet());
    }

    public List<Double> getSubjectPercentages(List<ExamRecord> records) {
        return groupBySubject(records).values().stream()
                .map(this::averagePercentage)
                .collect(Collectors.toList());
    }

    public List<String> getExamLabels(List<ExamRecord> records) {
        if (records == null) {
            return List.of();
        }
        return records.stream()
                .map(ExamRecord::getExamName)
                .collect(Collectors.toList());
    }

    public List<Double> getExamPercentages(List<ExamRecord> records) {
        if (records == null) {
            return List.of();
        }
        return records.stream()
                .map(ExamRecord::getPercentage)
                .map(this::round)
                .collect(Collectors.toList());
    }

    public String getBestSubject(List<ExamRecord> records) {
        Map<String, Double> averages = subjectAverages(records);
        if (averages.isEmpty()) {
            return "No data yet";
        }

        Map.Entry<String, Double> best = averages.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (best == null) {
            return "No data yet";
        }

        return best.getKey() + " (" + best.getValue() + "%)";
    }

    public String getWeakestSubject(List<ExamRecord> records) {
        Map<String, Double> averages = subjectAverages(records);
        if (averages.isEmpty()) {
            return "No data yet";
        }

        Map.Entry<String, Double> weak = averages.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        if (weak == null) {
            return "No data yet";
        }

        return weak.getKey() + " (" + weak.getValue() + "%)";
    }

    public List<String> generateRecommendations(List<ExamRecord> records) {
        List<String> recommendations = new ArrayList<>();

        if (records == null || records.isEmpty()) {
            recommendations.add("Add your first exam record to unlock personalized recommendations.");
            recommendations.add("Enter exam name, subject, total marks, and scored marks for better analysis.");
            return recommendations;
        }

        Map<String, Double> averages = subjectAverages(records);

        Map.Entry<String, Double> weakest = averages.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .orElse(null);

        Map.Entry<String, Double> strongest = averages.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (weakest != null) {
            if (weakest.getValue() < 40) {
                recommendations.add(
                        weakest.getKey() + " needs urgent attention. Revise fundamentals, do short daily practice, and reattempt weak topics."
                );
            } else if (weakest.getValue() < 60) {
                recommendations.add(
                        weakest.getKey() + " is below target. Focus on revision, concept clarity, and past-paper practice."
                );
            } else {
                recommendations.add(
                        weakest.getKey() + " is stable, but keep revising it regularly."
                );
            }
        }

        if (strongest != null && weakest != null && !strongest.getKey().equals(weakest.getKey())) {
            recommendations.add(
                    strongest.getKey() + " is your strongest subject. Keep it sharp with weekly revision."
            );
        }

        double overall = calculateOverallPercentage(records);

        if (overall < 50) {
            recommendations.add(
                    "Overall performance is low. Study in smaller sessions, track mistakes after every exam, and revise weak chapters first."
            );
        } else if (overall < 75) {
            recommendations.add(
                    "Overall performance is decent. Increase question practice and review incorrect answers after each exam."
            );
        } else {
            recommendations.add(
                    "Excellent overall performance. Maintain consistency and keep revising regularly."
            );
        }

        ExamRecord lowestExam = records.stream()
                .min(Comparator.comparingDouble(ExamRecord::getPercentage))
                .orElse(null);

        if (lowestExam != null && lowestExam.getSubject() != null) {
            recommendations.add(
                    "Lowest exam score: " + lowestExam.getExamName() + " in " +
                            lowestExam.getSubject().getSubjectName() + ". Review the mistakes from that test."
            );
        }

        return recommendations;
    }

    private Map<String, List<ExamRecord>> groupBySubject(List<ExamRecord> records) {
        if (records == null || records.isEmpty()) {
            return new LinkedHashMap<>();
        }

        return records.stream()
                .filter(r -> r.getSubject() != null)
                .collect(Collectors.groupingBy(
                        r -> r.getSubject().getSubjectName(),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
    }

    private Map<String, Double> subjectAverages(List<ExamRecord> records) {
        Map<String, List<ExamRecord>> grouped = groupBySubject(records);
        Map<String, Double> averages = new LinkedHashMap<>();

        for (Map.Entry<String, List<ExamRecord>> entry : grouped.entrySet()) {
            averages.put(entry.getKey(), averagePercentage(entry.getValue()));
        }

        return averages;
    }

    private double averagePercentage(List<ExamRecord> records) {
        if (records == null || records.isEmpty()) {
            return 0.0;
        }

        double avg = records.stream()
                .mapToDouble(ExamRecord::getPercentage)
                .average()
                .orElse(0.0);

        return round(avg);
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }
}
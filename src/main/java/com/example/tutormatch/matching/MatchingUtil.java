package com.example.tutormatch.matching;

import com.example.tutormatch.entity.Requirement;
import com.example.tutormatch.entity.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class MatchingUtil {

    // weights
    private static final double SUBJECT_WEIGHT = 50;
    private static final double LEVEL_WEIGHT = 20;
    private static final double MODE_WEIGHT = 10;
    private static final double LOCATION_WEIGHT = 10;
    private static final double BUDGET_WEIGHT = 10;

    public static List<Map<String, Object>> matchRequirementToTeachers(Requirement req, List<Teacher> teachers) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Teacher t : teachers) {
            double score = 0;
            Map<String, Double> breakdown = new LinkedHashMap<>();

            // Subject matching (simple token intersection)
            Set<String> tSubjects = splitToSet(t.getSubjects());
            Set<String> rSubjects = splitToSet(req.getSubject());
            double subjectScore = 0;
            if (!tSubjects.isEmpty() && !rSubjects.isEmpty()) {
                Set<String> inter = new HashSet<>(tSubjects);
                inter.retainAll(rSubjects);
                subjectScore = inter.isEmpty() ? 0 : 1;
            }
            breakdown.put("subject", subjectScore * SUBJECT_WEIGHT);
            score += subjectScore * SUBJECT_WEIGHT;

            // Level
            double levelScore = 0;
            if (t.getLevels() != null && req.getLevel() != null) {
                Set<String> tLevels = splitToSet(t.getLevels());
                levelScore = tLevels.contains(req.getLevel()) ? 1 : 0;
            }
            breakdown.put("level", levelScore * LEVEL_WEIGHT);
            score += levelScore * LEVEL_WEIGHT;

            // Mode
            double modeScore = 0;
            if (t.getMode() != null) {
                String tm = t.getMode().name();
                String rm = req.getMode().name();
                if (tm.equals("BOTH") || tm.equals(rm)) modeScore = 1;
            }
            breakdown.put("mode", modeScore * MODE_WEIGHT);
            score += modeScore * MODE_WEIGHT;

            // Location if offline
            double locScore = 0;
            if (req.getMode().name().equals("OFFLINE")) {
                if (t.getLocation() != null && req.getLocation() != null) {
                    locScore = t.getLocation().equalsIgnoreCase(req.getLocation()) ? 1 : 0;
                }
            } else {
                locScore = 1; // for online, treat as matched
            }
            breakdown.put("location", locScore * LOCATION_WEIGHT);
            score += locScore * LOCATION_WEIGHT;

            // Budget ±25%
            double budgetScore = 0;
            if (req.getBudget() != null && t.getFeePerHour() != null) {
                double min = req.getBudget() * 0.75;
                double max = req.getBudget() * 1.25;
                if (t.getFeePerHour() >= min && t.getFeePerHour() <= max) budgetScore = 1;
            }
            breakdown.put("budget", budgetScore * BUDGET_WEIGHT);
            score += budgetScore * BUDGET_WEIGHT;

            double percent = Math.round((score / 100.0) * 100.0) / 100.0; // keep two decimals
            Map<String, Object> map = new HashMap<>();
            map.put("teacher", t);
            map.put("score", percent);
            map.put("breakdown", breakdown);
            out.add(map);
        }

        // sort desc by score
        return out.stream()
                .sorted((a,b) -> Double.compare((Double)b.get("score"), (Double)a.get("score")))
                .collect(Collectors.toList());
    }

    private static Set<String> splitToSet(String s) {
        if (s == null) return Collections.emptySet();
        return Arrays.stream(s.split(","))
                .map(String::trim)
                .filter(x -> !x.isEmpty())
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
    }
}

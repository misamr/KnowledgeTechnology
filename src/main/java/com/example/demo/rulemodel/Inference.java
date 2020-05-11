package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Problem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.LinkOption;
import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * inference class
 */
public class Inference {
    private static Logger logger = LoggerFactory.getLogger(Inference.class);

    /**
     * infers rules based on the tags of options selected
     *
     * @param patient patient's stats
     */
    public static void inferRules(Patient patient) {
        // inferring the rules
        Problem major = patient.getProblems().get(0);
        Problem secondary = patient.getProblems().size() > 1 ? patient.getProblems().get(1) : null;
        switch (major.getComplaint()) {
            case "Headache":
                if (secondary == null) {
                    String tag;
                    if (major.getSeverity().equals("Severe") && major.getFrequency().equals("Recurring (chronic)") ||
                            major.getFrequency().equals("Recurring (chronic)") && major.getSymptoms().size() > 1) {
                        tag = "Neurologist";
                    } else if (RuleModel.compareStrings(major.getSeverity(), "Severe") && major.getFrequency().equals("Recurring (chronic)")
                            && major.getSymptoms().size() > 2) {
                        tag = "Endocrinologist";
                    } else if (major.getFrequency().equals("Acute (sudden)") && RuleModel.compareStrings(major.getSeverity(), "Severe")) {
                        tag = "Neurologist";
                    } else {
                        tag = "No specialist";
                    }
                    patient.getRecommendations().put(tag,
                            patient.getRecommendations().getOrDefault(tag, 0) + 1);
                } else {
                    switch (secondary.getComplaint()) {
                        case "Muscle / joint pain":
                            ruleHeadacheMuscle(patient, major, secondary);
                            break;
                        case "Sore throat / cough / sinusitis":
                            String tag;
                            if (major.getSymptoms().size() >= 1 &&
                                    !major.getSymptoms().contains("None of the above")) {
                                tag = "Allergologist";
                                patient.getRecommendations().put(tag,
                                        patient.getRecommendations().getOrDefault(tag, 0) + 1);
                            } else {
                                tag = "Otolaryngologist";
                            }
                            patient.getRecommendations().put(tag,
                                    patient.getRecommendations().getOrDefault(tag, 0) + 1);
                    }
                }
                break;
            case "Muscle / joint pain":
                if (secondary != null && secondary.getComplaint().equals("Headache")) {
                    ruleHeadacheMuscle(patient, secondary, major);
                } else if (secondary != null && secondary.getComplaint().equals("Difficulty breathing")) {
                    ruleMuscleDifficultyBreathing(patient, major);
                } else if (major.getSymptoms() != null && major.getSymptoms().size() > 1 &&
                        !major.getSymptoms().contains("None of the above")) {
                    String tag = "Physiotherapist";
                    patient.getRecommendations().put(tag,
                            patient.getRecommendations().getOrDefault(tag, 0) + 1);
                }
                break;
            case "Chest pain":
                String tag;
                if (secondary != null && (secondary.getComplaint().equals("Difficulty breathing") ||
                        major.getSymptoms().size() > 1 && !major.getSymptoms().contains("None of the above"))) {
                    tag = "Cardiologist";
                } else {
                    tag = "No specialist";
                }
                patient.getRecommendations().put(tag,
                        patient.getRecommendations().getOrDefault(tag, 0) + 1);
                break;
            case "Sore throat / cough / sinusitis":
                if (secondary != null && secondary.getComplaint().equals("Dermatitis") &&
                        major.getSymptoms().size() > 2) {
                    tag = "Allergologist";
                } else {
                    tag = "Otolaryngologist";
                }
                patient.getRecommendations().put(tag,
                        patient.getRecommendations().getOrDefault(tag, 0) + 1);
                break;
            case "Abdominal problems":
                if (major.getSymptoms().size() > 1 && !major.getSymptoms().contains("None of the above")) {
                    tag = "Gastroenterologist";
                } else {
                    tag = "No specialist";
                }
                patient.getRecommendations().put(tag,
                        patient.getRecommendations().getOrDefault(tag, 0) + 1);
                break;

            case "Difficulty breathing":
                if (secondary != null && secondary.getComplaint().equals("Muscle / joint pain")) {
                    ruleMuscleDifficultyBreathing(patient, secondary);
                }
                if (secondary != null && secondary.getComplaint().equals("Chest pain")) {
                    tag = "Cardiologist";
                } else if (major.getSymptoms() != null && major.getSymptoms().size() > 2) {
                    tag = "Allergologist";
                } else {
                    tag = "No specialist";
                }
                patient.getRecommendations().put(tag,
                        patient.getRecommendations().getOrDefault(tag, 0) + 1);
                break;
        }
        // in case no inference
        if (patient.getRecommendations().isEmpty()) {
            patient.getRecommendations().put("No specialist", 1);
        }
        // sort the specialist count to form the ranking
        HashMap<String, Integer> sortedRecommendations = sortByValue(patient.getRecommendations());
        List<String> recommendations = new ArrayList<>();
        int i = Math.min(sortedRecommendations.entrySet().size(), 3);
        for (Map.Entry<String, Integer> entry : sortedRecommendations.entrySet()) {
            if (i <= 0) {
                break;
            }
            recommendations.add(entry.getKey());
            i--;
        }
        logger.info("inference=" + sortedRecommendations.toString());
        patient.setSpecialists(recommendations);
    }

    private static void ruleMuscleDifficultyBreathing(Patient patient, Problem major) {
        String tag;
        if (patient.getProblems().size() > 2 && patient.getProblems().get(2).getComplaint().equals("Chest pain")) {
            tag = "Cardiologist";
        } else if (major.getSymptoms() != null && major.getSymptoms().size() > 1 &&
                !major.getSymptoms().contains("None of the above")) {
            tag = "Physiotherapist";
        } else {
            tag = "No specialist";
        }
        patient.getRecommendations().put(tag,
                patient.getRecommendations().getOrDefault(tag, 0) + 1);
    }

    private static void ruleHeadacheMuscle(Patient patient, Problem major, Problem secondary) {

        if (RuleModel.compareStrings(major.getSeverity(), "Severe") &&
                RuleModel.compareStrings(secondary.getSeverity(), "Severe")) {
            String tag = "Neurologist";
            patient.getRecommendations().put(tag,
                    patient.getRecommendations().getOrDefault(tag, 0) + 1);
        }
        if (RuleModel.compareStrings(major.getSeverity(), "!Low") &&
                RuleModel.compareStrings(secondary.getSeverity(), "!Low") &&
                ((major.getSymptoms() != null && major.getSymptoms().size() >= 1 &&
                        !major.getSymptoms().contains("None of the above")) ||
                        (major.getRiskFactors() != null && major.getRiskFactors().size() >= 1 &&
                                !major.getRiskFactors().get(0).equals("None of the above")))) {
            String tag = "Physiotherapist";
            patient.getRecommendations().put(tag,
                    patient.getRecommendations().getOrDefault(tag, 0) + 1);

        }
        if (RuleModel.compareStrings(major.getSeverity(), "!Low") &&
                RuleModel.compareStrings(secondary.getSeverity(), "!Low") &&
                (major.getSymptoms() != null && major.getSymptoms().contains("Fatigue") || patient.isFever())) {
            String tag = "Neurologist";
            patient.getRecommendations().put(tag,
                    patient.getRecommendations().getOrDefault(tag, 0) + 1);
        }
    }

    /**
     * helps to sort the specialists to visit in order
     *
     * @param hm hashmap of patient stats
     * @return sorted hashmap
     */
    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        HashMap<String, Integer> sorted = hm
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        sorted = sorted
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        return sorted;
    }
}

package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

/**
 * inference class
 */
public class Inference {
    private static Logger logger = LoggerFactory.getLogger(Inference.class);

    /**
     * it populates the recommendations in the patient instance
     *
     * @param patient patient's stats
     */
    public static void inferRules(Patient patient) {
        HashMap<String, Integer> sortedRecommendations = sortByValue(patient.getRecommendations());
        List<String> recommendations = new ArrayList<>();
        int i = Math.min(sortedRecommendations.entrySet().size(), 3);
        logger.info("i is ----- " + i);
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

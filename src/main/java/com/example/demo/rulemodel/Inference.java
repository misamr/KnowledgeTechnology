package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

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
        LinkedHashMap<String, Integer> sortedRecommendations = sortByValue(patient.getRecommendations());
        List<String> recommendations = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : sortedRecommendations.entrySet()) {
            recommendations.add(entry.getKey());
        }
        logger.info("inference=" + sortedRecommendations.toString());
        patient.setSpecialists(recommendations);
    }

    private static LinkedHashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        List<Map.Entry<String, Integer>> list =
                new LinkedList<>(hm.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        LinkedHashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }


}

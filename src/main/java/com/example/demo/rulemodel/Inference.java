package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * inference class
 */
public class Inference {
    private static Logger logger = LoggerFactory.getLogger(Inference.class);

    /**
     * it populates the recommendations in the fact instance
     *
     * @param fact
     */
    public static void inferRules(Patient fact, Map<String, String[]> specialistsMap) {
        List<String> inference = new ArrayList<>();
        if (fact.getAge() != null) {
            inference = Arrays.asList(specialistsMap.get(fact.getAge()));
        }
        if (fact.getSymptoms().size() > 0) {
            List<String> tempList = Arrays.asList(specialistsMap.get(fact.getSymptoms().get(0)));
            //inference = Arrays.asList(specialistsMap.get(fact.getSymptoms().get(0)));
            inference = merge(inference, tempList); //was previously intersect

            /*
             * iterate over all the chosen symptoms and merge all the recommendations
             */

            if (fact.getSymptoms().size() > 1) {
                for (int i = 1; i < fact.getSymptoms().size(); i++) {
                    tempList = Arrays.asList(specialistsMap.get(fact.getSymptoms().get(i)));
                    inference = merge(tempList, inference);
                }
            }
        }
        logger.info("inference=" + inference.toString());
        //set the final recommendations
        fact.setRecommendations(inference);
    }

    /**
     * merge two lists together and remove duplicates
     *
     * @param l1
     * @param l2
     * @return
     */
    private static List<String> merge(List<String> l1, List<String> l2) {
        List<String> j = Stream.concat(l1.stream(), l2.stream())
                .collect(Collectors.toList());
        return j.stream().distinct().collect(Collectors.toList());

    }

    /**
     * filter to only show things in common (in l2)
     *
     * @param l1
     * @param l2
     * @return
     */
    private static List<String> intersect(List<String> l1, List<String> l2) {
        return l1.stream().filter(x -> l2.contains(x)).collect(Collectors.toList());
    }


}

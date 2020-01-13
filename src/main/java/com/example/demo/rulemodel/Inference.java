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
     * infers rules based on the tags of options selected
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
            StringBuilder recommendation = new StringBuilder(entry.getKey());
            switch (entry.getKey()) {
                case "Cardiologist":
                    recommendation.append(" - Cardiologist specializes in diagnosing and " +
                            "treating diseases of the cardiovascular system, such as arrythmias, coronary " +
                            "heart disease, atherosclerosis or high blood cholesterol. Patients diagnosed " +
                            "with heart disease or patients with a high risk of developing heart disease " +
                            "based on their medical history (patients over 50 years of age, obese patients, " +
                            "smokers, patients with hypertension) should visit a cardiologist.\n");
                    break;
                case "Neurologist":
                    recommendation.append(" - Neurologist specializes in treating diseases " +
                            "of the central and peripheral nervous system. Patients experiencing muscle weakness, " +
                            "dizziness, seizures, severe headaches or patients who have suffered damage of the " +
                            "nervous system (such as stroke) should visit a neurologist for further diagnostics " +
                            "and treatment.\n");
                    break;
                case "Otolaryngologist":
                    recommendation.append(" - Otolaryngologist specializes in treating diseases" +
                            " of the ear, nose, throat, and related bodily structures. Most commonly, otolaryngologists " +
                            "treat chronic sinusitis (demonstrated by swollen and inflamed sinuses - nose and head), " +
                            "laryngeal diseases (common symptoms include sore throat), and ear pain. Treatment of " +
                            "allergies which affect the sense of smell may require cooperation of both otolaryngologist " +
                            "and allergologist.\n");
                    break;
                case "Physiotherapist":
                    recommendation.append(" - Physiotherapy helps with the management of " +
                            "long-term conditions related to the musculoskeletal and nervous system (such as arthritis), " +
                            "as well as sudden injuries (especially people who do sports are at risk). A wide range of " +
                            "patients may benefit from visiting a physiotherapist, and patients physiotherapy is typically " +
                            "indicated as a complementary treatment along with other medical procedures.\n");
                    break;
                case "Gastroenterologist":
                    recommendation.append(" - Gastroenterologist specializes in treating " +
                            "conditions of the gastrointestinal tract and liver. Diseases treated by gastroenterologists " +
                            "typically include peptic ulcer disease, nutritional problems or colon polyps. You should " +
                            "consider referring a patient to gastroenterologist if the patient suffers from diarrhea, " +
                            "vomiting, acid reflux, or has recently experienced rapid weight gain / loss.\n");
                    break;
                case "Allergologist":
                    recommendation.append(" - Allergologist is trained to prevent, diagnose and " +
                            "manage allergic disease (such as food allergies or asthma). In many cases, allergies may " +
                            "demonstrate very subtly (runny nose, sore throat, coughing / sneezing) and are often " +
                            "misdiagnosed.\n");
                    break;
                case "Endocrinologist":
                    recommendation.append(" - Endocrinologists are experts in glands that are " +
                            "responsible for producing and releasing hormones into the bloodstream (which coordinate " +
                            "processes ranging from metabolism to cell growth). One of the most common diseases treated " +
                            "by endocrinologists is hyperthyroidism (common symptoms include sudden weight loss or gain, " +
                            "and fatigue). Furthermore, endocrinologists are usually involved in the treatment of a " +
                            "range of diseases, including diabetes, osteoporosis or metabolic disorders.");
                    break;
            }
            recommendations.add(recommendation.toString());
            i--;
        }
        logger.info("inference=" + sortedRecommendations.toString());

        patient.setSpecialists(recommendations);
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

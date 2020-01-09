package com.example.demo.rulemodel;

import com.example.demo.domainmodel.Patient;
import com.example.demo.domainmodel.Question;
import com.example.demo.utils.QuestionsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * class for populating the domain model instance
 */
public class RuleModel {

    private static Logger logger = LoggerFactory.getLogger(RuleModel.class);
    private static List<Question> questions = QuestionsUtil.initializeQuestions();

    public static void populate(Patient patient, String question, String answer) {
        Question questionKB = getQuestionKB(patient, question);
        List<String> patientTags = Objects.requireNonNull(questionKB).getAnswers().get(answer);
        for (String tag : patientTags) {
            patient.getRecommendations().put(tag, patient.getRecommendations().getOrDefault(tag, 0) + 1);
        }

    }

    public static void populate(Patient patient, String question, List<String> answers) {
        Question questionKB = getQuestionKB(patient, question);
        for (String answer : answers) {
            List<String> patientTags = Objects.requireNonNull(questionKB).getAnswers().get(answer);
            for (String tag : patientTags) {
                patient.getRecommendations().put(tag, patient.getRecommendations().getOrDefault(tag, 0) + 1);
            }
        }
    }

    private static Question getQuestionKB(Patient patient, String question) {
        patient.init();
        Question questionKB = null;
        for (Question q : questions) {
            if (q.getText().equals(question)) {
                questionKB = q;
                break;
            }
        }
        if (questionKB == null) logger.info("Question did not match any=" + question);
        return questionKB;
    }
}
